package com.doctor.commons.filewatch;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.doctor.commons.ObjectUtils;

class FolderSnapshot {

    private static final Set<String> DOT_FOLDERS = Collections
            .unmodifiableSet(new HashSet<String>(Arrays.asList(".", "..")));

    private final File               folder;

    private final Date               time;

    private Set<FileSnapshot>        files;

    /**
     * Create a new {@link FolderSnapshot} for the given folder.
     * 
     * @param folder the source folder
     */
    FolderSnapshot(File folder) {
        Objects.requireNonNull(folder, "Folder must not be null");
        ObjectUtils.requireTrue(folder.isDirectory(), "Folder must not be a file");
        this.folder = folder;
        this.time = new Date();
        Set<FileSnapshot> files = new LinkedHashSet<FileSnapshot>();
        collectFiles(folder, files);
        this.files = Collections.unmodifiableSet(files);
    }

    private void collectFiles(File source, Set<FileSnapshot> result) {
        File[] children = source.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory() && !DOT_FOLDERS.contains(child.getName())) {
                    collectFiles(child, result);
                } else if (child.isFile()) {
                    result.add(new FileSnapshot(child));
                }
            }
        }
    }

    public ChangedFiles getChangedFiles(FolderSnapshot snapshot,
                                        FileFilter triggerFilter) {
        Objects.requireNonNull(snapshot, "Snapshot must not be null");
        File folder = this.folder;
        ObjectUtils.requireTrue(snapshot.folder.equals(folder),
                "Snapshot source folder must be '" + folder + "'");
        Set<ChangedFile> changes = new LinkedHashSet<ChangedFile>();
        Map<File, FileSnapshot> previousFiles = getFilesMap();
        for (FileSnapshot currentFile : snapshot.files) {
            if (acceptChangedFile(triggerFilter, currentFile)) {
                FileSnapshot previousFile = previousFiles.remove(currentFile.getFile());
                if (previousFile == null) {
                    changes.add(new ChangedFile(folder, currentFile.getFile(), FileChangeType.ADD));
                } else if (!previousFile.equals(currentFile)) {
                    changes.add(
                            new ChangedFile(folder, currentFile.getFile(), FileChangeType.MODIFY));
                }
            }
        }
        for (FileSnapshot previousFile : previousFiles.values()) {
            if (acceptChangedFile(triggerFilter, previousFile)) {
                changes.add(new ChangedFile(folder, previousFile.getFile(), FileChangeType.DELETE));
            }
        }
        return new ChangedFiles(folder, changes);
    }

    private boolean acceptChangedFile(FileFilter triggerFilter, FileSnapshot file) {
        return (triggerFilter == null || !triggerFilter.accept(file.getFile()));
    }

    private Map<File, FileSnapshot> getFilesMap() {
        Map<File, FileSnapshot> files = new LinkedHashMap<File, FileSnapshot>();
        for (FileSnapshot file : this.files) {
            files.put(file.getFile(), file);
        }
        return files;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof FolderSnapshot) {
            return equals((FolderSnapshot) obj, null);
        }
        return super.equals(obj);
    }

    public boolean equals(FolderSnapshot other, FileFilter filter) {
        if (this.folder.equals(other.folder)) {
            Set<FileSnapshot> ourFiles = filter(this.files, filter);
            Set<FileSnapshot> otherFiles = filter(other.files, filter);
            return ourFiles.equals(otherFiles);
        }
        return false;
    }

    private Set<FileSnapshot> filter(Set<FileSnapshot> source, FileFilter filter) {
        if (filter == null) {
            return source;
        }
        Set<FileSnapshot> filtered = new LinkedHashSet<FileSnapshot>();
        for (FileSnapshot file : source) {
            if (filter.accept(file.getFile())) {
                filtered.add(file);
            }
        }
        return filtered;
    }

    @Override
    public int hashCode() {
        int hashCode = this.folder.hashCode();
        hashCode = 31 * hashCode + this.files.hashCode();
        return hashCode;
    }

    /**
     * Return the source folder of this snapshot.
     * 
     * @return the source folder
     */
    public File getFolder() {
        return this.folder;
    }

    @Override
    public String toString() {
        return this.folder + " snapshot at " + this.time;
    }

}
