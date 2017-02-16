package com.doctor.commons.filewatch;

import java.util.Set;

public interface FileChangeListener {
    void onChange(Set<ChangedFiles> changeSet);
}
