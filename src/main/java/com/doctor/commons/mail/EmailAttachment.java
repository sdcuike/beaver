package com.doctor.commons.mail;

import java.util.UUID;

import javax.activation.DataSource;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * 邮箱附件
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.04
 *         <p>
 */
@ThreadSafe
public abstract class EmailAttachment {
    protected final String name;
    protected final String contentId;

    protected EmailAttachment(String name) {
        this.name = name;
        this.contentId = UUID.randomUUID().toString();
    }

    protected EmailAttachment(String name, String contentId) {
        this.name = name;
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public String getContentId() {
        return contentId;
    }

    public abstract DataSource getDataSource();
}
