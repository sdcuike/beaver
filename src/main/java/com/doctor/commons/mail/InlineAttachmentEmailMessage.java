package com.doctor.commons.mail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
