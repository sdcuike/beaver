package com.doctor.commons.mail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 内嵌附件(目前只有内嵌图片附件）邮件内容
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.07
 *         <p>
 */
public class InlineAttachmentEmailMessage extends EmailMessage {
    private final EmailAttachment emailAttachment;

    public InlineAttachmentEmailMessage(Charset charset, EmailAttachment emailAttachment) {
        super(null, MimeTypes.MIME_TEXT_HTML, charset);
        this.emailAttachment = emailAttachment;
    }

    public InlineAttachmentEmailMessage(EmailAttachment emailAttachment) {
        super(null, MimeTypes.MIME_TEXT_HTML, StandardCharsets.UTF_8);
        this.emailAttachment = emailAttachment;
    }

    public EmailAttachment getEmailAttachment() {
        return emailAttachment;
    }
}
