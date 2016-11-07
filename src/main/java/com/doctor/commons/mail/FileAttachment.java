package com.doctor.commons.mail;

import java.io.File;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 * 邮箱附件：文件附件-普通附件.<br>
 * 内联附件见<br>
 * {@link Open Declaration com.doctor.commons.mail.InlineAttachmentEmailMessage}
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

    public FileAttachment(File file, String contentId) {
        super(file.getName(), contentId);
        this.file = file;
    }

    @Override
    public DataSource getDataSource() {
        return new FileDataSource(file);
    }

}
