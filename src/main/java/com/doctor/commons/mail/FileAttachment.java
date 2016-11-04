package com.doctor.commons.mail;

import java.io.File;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 * 邮箱附件：文件附件
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.04
 *         <p>
 */
public class FileAttachment extends EmailAttachment {
    private final File file;

    public FileAttachment(File file) {
        super(file.getName());
        this.file = file;
    }

    public FileAttachment(File file, String name) {
        super(name);
        this.file = file;
    }

    public FileAttachment(File file, String name, String contentId) {
        super(name, contentId);
        this.file = file;
    }

    @Override
    public DataSource getDataSource() {
        return new FileDataSource(file);
    }

}
