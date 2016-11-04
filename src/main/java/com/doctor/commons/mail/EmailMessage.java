package com.doctor.commons.mail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * 邮件内容
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.03
 *         <p>
 */
@ThreadSafe
public class EmailMessage {
    private final String  content;
    private final String  mimeType;
    private final Charset charset;

    public EmailMessage(String content, String mimeType) {
        this.content = content;
        this.mimeType = mimeType;
        this.charset = StandardCharsets.UTF_8;
    }

    public EmailMessage(String content, String mimeType, Charset charset) {
        this.content = content;
        this.mimeType = mimeType;
        this.charset = charset;
    }

    public String getContent() {
        return content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Charset getCharset() {
        return charset;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((charset == null) ? 0 : charset.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmailMessage other = (EmailMessage) obj;
        if (charset == null) {
            if (other.charset != null)
                return false;
        } else if (!charset.equals(other.charset))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (mimeType == null) {
            if (other.mimeType != null)
                return false;
        } else if (!mimeType.equals(other.mimeType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EmailMessage [content=" + content + ", mimeType=" + mimeType + ", charset=" + charset + "]";
    }
}
