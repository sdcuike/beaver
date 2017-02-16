package com.doctor.commons.filewatch;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FileSystemWatcherTest {

    public static void main(String[] args) throws InterruptedException {

        FileSystemWatcher watcher = new FileSystemWatcher();
        watcher.addListener(new FileChangeListener() {

            @Override
            public void onChange(Set<ChangedFiles> changeSet) {
                System.out.println(changeSet);

            }
        });

        watcher.addSourceFolder(new File("E:/app-doc-2016-8-31"));
        watcher.start();
        TimeUnit.HOURS.sleep(1L);

    }

}
