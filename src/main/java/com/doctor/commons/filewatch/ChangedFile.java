package com.doctor.commons.filewatch;

import java.io.File;
import java.util.Objects;

import com.doctor.commons.PathUtils;

public final class ChangedFile {

    private final File           sourceFolder;

    private final File           file;

    private final FileChangeType type;

    /**
     * Create a new {@link ChangedFile} instance.
     * 
     * @param sourceFolder the source folder
     * @param file the file
     * @param type the type of change
     */
    public ChangedFile(File sourceFolder, File file, FileChangeType type) {
        Objects.requireNonNull(sourceFolder, "SourceFolder must not be null");
        Objects.requireNonNull(file, "File must not be null");
        Objects.requireNonNull(type, "Type must not be null");
        this.sourceFolder = sourceFolder;
        this.file = file;
        this.type = type;
    }

    /**
     * Return the file that was changed.
     * 
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Return the type of change.
     * 
     * @return the type of change
     */
    public FileChangeType getType() {
        return this.type;
    }

    /**
     * Return the name of the file relative to the source folder.
     * 
     * @return the relative name
     */
    public String getRelativeName() {
        File folder = this.sourceFolder.getAbsoluteFile();
        File file = this.file.getAbsoluteFile();
        String folderName = PathUtils.cleanPath(folder.getPath());
        String fileName = PathUtils.cleanPath(file.getPath());

        if (!fileName.startsWith(folderName)) {
            throw new RuntimeException(fileName + " is not contained in the source folder " + folderName);
        }

        return fileName.substring(folderName.length() + 1);
    }

    @Override
    public int hashCode() {
        return this.file.hashCode() * 31 + this.type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof ChangedFile) {
            ChangedFile other = (ChangedFile) obj;
            return this.file.equals(other.file) && this.type.equals(other.type);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return this.file + " (" + this.type + ")";
    }
}
