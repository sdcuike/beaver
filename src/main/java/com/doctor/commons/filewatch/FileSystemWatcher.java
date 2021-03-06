package com.doctor.commons.filewatch;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.doctor.commons.ObjectUtils;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2017年2月16日 <br/>
 *         {@linkplain org.springframework.boot.devtools.filewatch.FileSystemWatcher}
 */

public class FileSystemWatcher {

    private static final long               DEFAULT_POLL_INTERVAL = 1000;

    private static final long               DEFAULT_QUIET_PERIOD  = 400;

    private final List<FileChangeListener>  listeners             = new ArrayList<FileChangeListener>();

    private final boolean                   daemon;

    private final long                      pollInterval;

    private final long                      quietPeriod;

    private final AtomicInteger             remainingScans        = new AtomicInteger(-1);

    private final Map<File, FolderSnapshot> folders               = new HashMap<File, FolderSnapshot>();

    private Thread                          watchThread;

    private FileFilter                      triggerFilter;

    private final Object                    monitor               = new Object();

    /**
     * Create a new {@link FileSystemWatcher} instance.
     */
    public FileSystemWatcher() {
        this(true, DEFAULT_POLL_INTERVAL, DEFAULT_QUIET_PERIOD);
    }

    /**
     * Create a new {@link FileSystemWatcher} instance.
     * 
     * @param daemon if a daemon thread used to monitor changes
     * @param pollInterval the amount of time to wait between checking for changes
     * @param quietPeriod the amount of time required after a change has been detected to ensure that updates have completed
     */
    public FileSystemWatcher(boolean daemon, long pollInterval, long quietPeriod) {
        ObjectUtils.requireTrue(pollInterval > 0, "PollInterval must be positive");
        ObjectUtils.requireTrue(quietPeriod > 0, "QuietPeriod must be positive");
        ObjectUtils.requireTrue(pollInterval > quietPeriod, "PollInterval must be greater than QuietPeriod");
        this.daemon = daemon;
        this.pollInterval = pollInterval;
        this.quietPeriod = quietPeriod;
    }

    /**
     * Add listener for file change events. Cannot be called after the watcher has been {@link #start() started}.
     * 
     * @param fileChangeListener the listener to add
     */
    public void addListener(FileChangeListener fileChangeListener) {
        Objects.requireNonNull(fileChangeListener, "FileChangeListener must not be null");
        synchronized (this.monitor) {
            checkNotStarted();
            this.listeners.add(fileChangeListener);
        }
    }

    /**
     * Add source folders to monitor. Cannot be called after the watcher has been {@link #start() started}.
     * 
     * @param folders the folders to monitor
     */
    public void addSourceFolders(Iterable<File> folders) {
        Objects.requireNonNull(folders, "Folders must not be null");
        synchronized (this.monitor) {
            for (File folder : folders) {
                addSourceFolder(folder);
            }
        }
    }

    /**
     * Add a source folder to monitor. Cannot be called after the watcher has been {@link #start() started}.
     * 
     * @param folder the folder to monitor
     */
    public void addSourceFolder(File folder) {
        Objects.requireNonNull(folder, "Folder must not be null");
        ObjectUtils.requireTrue(folder.isDirectory(),
                "Folder '" + folder + "' must exist and must" + " be a directory");
        synchronized (this.monitor) {
            checkNotStarted();
            this.folders.put(folder, null);
        }
    }

    /**
     * Set an optional {@link FileFilter} used to limit the files that trigger a change.
     * 
     * @param triggerFilter a trigger filter or null
     */
    public void setTriggerFilter(FileFilter triggerFilter) {
        synchronized (this.monitor) {
            this.triggerFilter = triggerFilter;
        }
    }

    private void checkNotStarted() {
        synchronized (this.monitor) {
            ObjectUtils.requireTrue(this.watchThread == null, "FileSystemWatcher already started");
        }
    }

    /**
     * Start monitoring the source folder for changes.
     */
    public void start() {
        synchronized (this.monitor) {
            saveInitialSnapshots();
            if (this.watchThread == null) {
                Map<File, FolderSnapshot> localFolders = new HashMap<File, FolderSnapshot>();
                localFolders.putAll(this.folders);
                this.watchThread = new Thread(new Watcher(this.remainingScans,
                        new ArrayList<FileChangeListener>(this.listeners),
                        this.triggerFilter, this.pollInterval, this.quietPeriod,
                        localFolders));
                this.watchThread.setName("File Watcher");
                this.watchThread.setDaemon(this.daemon);
                this.watchThread.start();
            }
        }
    }

    private void saveInitialSnapshots() {
        for (File folder : this.folders.keySet()) {
            this.folders.put(folder, new FolderSnapshot(folder));
        }
    }

    /**
     * Stop monitoring the source folders.
     */
    public void stop() {
        stopAfter(0);
    }

    /**
     * Stop monitoring the source folders.
     * 
     * @param remainingScans the number of remaining scans
     */
    void stopAfter(int remainingScans) {
        synchronized (this.monitor) {
            Thread thread = this.watchThread;
            if (thread != null) {
                this.remainingScans.set(remainingScans);
                if (remainingScans <= 0) {
                    thread.interrupt();
                }
                if (Thread.currentThread() != thread) {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                this.watchThread = null;
            }
        }
    }

    private static final class Watcher implements Runnable {

        private final AtomicInteger            remainingScans;

        private final List<FileChangeListener> listeners;

        private final FileFilter               triggerFilter;

        private final long                     pollInterval;

        private final long                     quietPeriod;

        private Map<File, FolderSnapshot>      folders;

        private Watcher(AtomicInteger remainingScans, List<FileChangeListener> listeners,
                        FileFilter triggerFilter, long pollInterval, long quietPeriod,
                        Map<File, FolderSnapshot> folders) {
            this.remainingScans = remainingScans;
            this.listeners = listeners;
            this.triggerFilter = triggerFilter;
            this.pollInterval = pollInterval;
            this.quietPeriod = quietPeriod;
            this.folders = folders;
        }

        @Override
        public void run() {
            int remainingScans = this.remainingScans.get();
            while (remainingScans > 0 || remainingScans == -1) {
                try {
                    if (remainingScans > 0) {
                        this.remainingScans.decrementAndGet();
                    }
                    scan();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                remainingScans = this.remainingScans.get();
            }
        };

        private void scan() throws InterruptedException {
            Thread.sleep(this.pollInterval - this.quietPeriod);
            Map<File, FolderSnapshot> previous;
            Map<File, FolderSnapshot> current = this.folders;
            do {
                previous = current;
                current = getCurrentSnapshots();
                Thread.sleep(this.quietPeriod);
            } while (isDifferent(previous, current));
            if (isDifferent(this.folders, current)) {
                updateSnapshots(current.values());
            }
        }

        private boolean isDifferent(Map<File, FolderSnapshot> previous,
                                    Map<File, FolderSnapshot> current) {
            if (!previous.keySet().equals(current.keySet())) {
                return true;
            }
            for (Map.Entry<File, FolderSnapshot> entry : previous.entrySet()) {
                FolderSnapshot previousFolder = entry.getValue();
                FolderSnapshot currentFolder = current.get(entry.getKey());
                if (!previousFolder.equals(currentFolder, this.triggerFilter)) {
                    return true;
                }
            }
            return false;
        }

        private Map<File, FolderSnapshot> getCurrentSnapshots() {
            Map<File, FolderSnapshot> snapshots = new LinkedHashMap<File, FolderSnapshot>();
            for (File folder : this.folders.keySet()) {
                snapshots.put(folder, new FolderSnapshot(folder));
            }
            return snapshots;
        }

        private void updateSnapshots(Collection<FolderSnapshot> snapshots) {
            Map<File, FolderSnapshot> updated = new LinkedHashMap<File, FolderSnapshot>();
            Set<ChangedFiles> changeSet = new LinkedHashSet<ChangedFiles>();
            for (FolderSnapshot snapshot : snapshots) {
                FolderSnapshot previous = this.folders.get(snapshot.getFolder());
                updated.put(snapshot.getFolder(), snapshot);
                ChangedFiles changedFiles = previous.getChangedFiles(snapshot,
                        this.triggerFilter);
                if (!changedFiles.getFiles().isEmpty()) {
                    changeSet.add(changedFiles);
                }
            }
            if (!changeSet.isEmpty()) {
                fireListeners(Collections.unmodifiableSet(changeSet));
            }
            this.folders = updated;
        }

        private void fireListeners(Set<ChangedFiles> changeSet) {
            for (FileChangeListener listener : this.listeners) {
                listener.onChange(changeSet);
            }
        }

    }

}
