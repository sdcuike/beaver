package com.doctor.commons.filewatch;

import java.io.File;
import java.util.Objects;

import com.doctor.commons.ObjectUtils;

class FileSnapshot {

    private final File    file;

    private final boolean exists;

    private final long    length;

    private final long    lastModified;

    FileSnapshot(File file) {
        Objects.requireNonNull(file, "File must not be null");
        ObjectUtils.requireTrue(file.isFile() || !file.exists(), "File must not be a folder");
        this.file = file;
        this.exists = file.exists();
        this.length = file.length();
        this.lastModified = file.lastModified();
    }

    public File getFile() {
        return this.file;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof FileSnapshot) {
            FileSnapshot other = (FileSnapshot) obj;
            boolean equals = this.file.equals(other.file);
            equals &= this.exists == other.exists;
            equals &= this.length == other.length;
            equals &= this.lastModified == other.lastModified;
            return equals;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hashCode = this.file.hashCode();
        hashCode = 31 * hashCode + (this.exists ? 1231 : 1237);
        hashCode = 31 * hashCode + (int) (this.length ^ (this.length >>> 32));
        hashCode = 31 * hashCode + (int) (this.lastModified ^ (this.lastModified >>> 32));
        return hashCode;
    }

    @Override
    public String toString() {
        return this.file.toString();
    }

}
