package com.doctor.commons.mail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件内容
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.03
 *         <p>
 */
public abstract class AbstractMimeMessage {

    protected String                subject         = "";

    protected Charset               subjectCharset  = StandardCharsets.UTF_8;

    protected EmailAddress          from;
    protected List<EmailAddress>    to              = new ArrayList<>();

    protected List<EmailAddress>    replyTo         = new ArrayList<>();

    protected List<EmailAddress>    cc              = new ArrayList<>();
    protected List<EmailAddress>    bcc             = new ArrayList<>();

    protected List<EmailMessage>    emailMessage    = new ArrayList<>();
    protected List<EmailAttachment> emailAttachment = new ArrayList<>();
    protected Map<String, String>   headers         = new HashMap<>();
    protected Date                  sentDate        = new Date();
    /**
     * typically between 1 (highest) and 5 (lowest)
     */
    protected int                   priority        = 5;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Charset getSubjectCharset() {
        return subjectCharset;
    }

    public void setSubjectCharset(Charset subjectCharset) {
        this.subjectCharset = subjectCharset;
    }

    public void setFrom(EmailAddress from) {
        this.from = from;
    }

    public EmailAddress getFrom() {
        return from;
    }

    public List<EmailAddress> getTo() {
        return to;
    }

    public void setTo(List<EmailAddress> to) {
        this.to = to;
    }

    public void addTo(EmailAddress... tos) {
        this.to.addAll(Arrays.asList(tos));
    }

    public void setReplyTo(List<EmailAddress> replyTo) {
        this.replyTo = replyTo;
    }

    public List<EmailAddress> getReplyTo() {
        return replyTo;
    }

    public void addReplyTo(EmailAddress... replyTos) {
        this.replyTo.addAll(Arrays.asList(replyTos));
    }

    public void setCc(List<EmailAddress> cc) {
        this.cc = cc;
    }

    public List<EmailAddress> getCc() {
        return cc;
    }

    public void addCc(EmailAddress... ccs) {
        this.cc.addAll(Arrays.asList(ccs));
    }

    public List<EmailAddress> getBcc() {
        return bcc;
    }

    public void setBcc(List<EmailAddress> bcc) {
        this.bcc = bcc;
    }

    public void addBcc(EmailAddress... bccs) {
        this.bcc.addAll(Arrays.asList(bccs));
    }

    public List<EmailMessage> getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(List<EmailMessage> emailMessage) {
        this.emailMessage = emailMessage;
    }

    public void addEmailMessage(EmailMessage... emailMessage) {
        this.emailMessage.addAll(Arrays.asList(emailMessage));
    }

    public void addEmailMessage(String content, String mimeType, Charset charset) {
        this.emailMessage.add(new EmailMessage(content, mimeType, charset));
    }

    public void addEmailMessage(String content, String mimeType) {
        this.emailMessage.add(new EmailMessage(content, mimeType));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public String getHeader(String name) {
        return this.headers.get(name);
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public List<EmailAttachment> getEmailAttachment() {
        return emailAttachment;
    }

    public void setEmailAttachment(List<EmailAttachment> emailAttachment) {
        this.emailAttachment = emailAttachment;
    }

    public void addEmailAttachment(EmailAttachment... attachment) {
        this.emailAttachment.addAll(Arrays.asList(attachment));
    }

}
