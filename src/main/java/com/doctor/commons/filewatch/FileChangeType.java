package com.doctor.commons.filewatch;

public enum FileChangeType {

    /**
     * A new file has been added.
     */
    ADD,

    /**
     * An existing file has been modified.
     */
    MODIFY,

    /**
     * An existing file has been deleted.
     */
    DELETE

}
