package com.doctor.commons.mail;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

public class MimeMessage extends AbstractMimeMessage {

    public static MimeMessage create() {
        return new MimeMessage();
    }

    public MimeMessage subject(String subject) {
        setSubject(subject);
        return this;
    }

    public MimeMessage subjectCharset(Charset subjectCharset) {
        setSubjectCharset(subjectCharset);
        return this;
    }

    public MimeMessage from(EmailAddress from) {
        setFrom(from);
        return this;
    }

    public MimeMessage from(String nickName, String email) {
        setFrom(new EmailAddress(nickName, email));
        return this;
    }

    public MimeMessage from(String from) {
        setFrom(new EmailAddress(from));
        return this;
    }

    public MimeMessage to(EmailAddress... to) {
        addTo(to);
        return this;
    }

    public MimeMessage to(String nickName, String email) {
        addTo(new EmailAddress(nickName, email));
        return this;
    }

    public MimeMessage to(String to) {
        addTo(new EmailAddress(to));
        return this;
    }

    public MimeMessage replyTo(EmailAddress... replyTo) {
        addReplyTo(replyTo);
        return this;
    }

    public MimeMessage replyTo(String nickName, String email) {
        addReplyTo(new EmailAddress(nickName, email));
        return this;
    }

    public MimeMessage replyTo(String replyTo) {
        addReplyTo(new EmailAddress(replyTo));
        return this;
    }

    public MimeMessage cc(EmailAddress... cc) {
        addCc(cc);
        return this;
    }

    public MimeMessage cc(String nickName, String email) {
        addCc(new EmailAddress(nickName, email));
        return this;
    }

    public MimeMessage cc(String cc) {
        addCc(new EmailAddress(cc));
        return this;
    }

    public MimeMessage bcc(EmailAddress... bcc) {
        addBcc(bcc);
        return this;
    }

    public MimeMessage bcc(String nickName, String email) {
        addBcc(new EmailAddress(nickName, email));
        return this;
    }

    public MimeMessage bcc(String bcc) {
        addBcc(new EmailAddress(bcc));
        return this;
    }

    public MimeMessage emailMessage(EmailMessage... emailMessage) {
        addEmailMessage(emailMessage);
        return this;
    }

    public MimeMessage emailMessage(String content, String mimeType, Charset charset) {
        addEmailMessage(content, mimeType, charset);
        return this;
    }

    public MimeMessage emailMessage(String content, String mimeType) {
        addEmailMessage(content, mimeType);
        return this;
    }

    public MimeMessage emailMessageForText(String content, Charset charset) {
        addEmailMessage(content, MimeTypes.MIME_TEXT_PLAIN, charset);
        return this;
    }

    public MimeMessage emailMessageForText(String content) {
        addEmailMessage(content, MimeTypes.MIME_TEXT_PLAIN);
        return this;
    }

    public MimeMessage emailMessageForHtml(String content, Charset charset) {
        addEmailMessage(content, MimeTypes.MIME_TEXT_HTML, charset);
        return this;
    }

    public MimeMessage emailMessageForHtml(String content) {
        addEmailMessage(content, MimeTypes.MIME_TEXT_HTML);
        return this;
    }

    public MimeMessage emailMessageForHtmlInlineAttachment(Charset charset, EmailAttachment emailAttachment) {
        addEmailMessage(new InlineAttachmentEmailMessage(charset, emailAttachment));
        return this;
    }

    public MimeMessage emailMessageForHtmlInlineAttachment(EmailAttachment emailAttachment) {
        addEmailMessage(new InlineAttachmentEmailMessage(emailAttachment));
        return this;
    }

    public MimeMessage header(String name, String value) {
        setHeader(name, value);
        return this;
    }

    public MimeMessage sentDate(Date date) {
        setSentDate(date);
        return this;
    }

    public MimeMessage priority(int priority) {
        setPriority(priority);
        return this;
    }

    public MimeMessage emailAttachment(EmailAttachment emailAttachment) {
        addEmailAttachment(emailAttachment);
        return this;
    }

    public MimeMessage emailAttachmentForFile(File file, String name) {
        addEmailAttachment(new FileAttachment(file, UUID.randomUUID().toString()));
        return this;
    }
}
