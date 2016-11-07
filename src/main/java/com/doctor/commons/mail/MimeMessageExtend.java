package com.doctor.commons.mail;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 内部使用
 * <p>
 * This is a utility class that generates unique values. The generated String
 * contains only US-ASCII characters and hence is safe for use in RFC822
 * headers.
 * </p>
 * How do I set or change the SMTP Message-ID with javax.mail?
 * 
 * @see http://stackoverflow.com/questions/8366843/how-do-i-set-or-change-the-
 *      smtp-message-id-with-javax-mail
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.03
 *         <p>
 */
class MimeMessageExtend extends MimeMessage {

    public static final String Multipart_Subtype_Mixed       = "mixed";

    public static final String Multipart_Subtype_Related     = "related";

    public static final String Multipart_Subtype_Alternative = "alternative";

    public static final String Content_Type_Alternative      = "text/alternative";

    public static final String Content_Type_Html             = "text/html";

    public static final String Content_Type_Charset_Suffix   = ";charset=";

    public static final String Header_Priority               = "X-Priority";

    public static final String Header_Content_Id             = "Content-ID";

    public MimeMessageExtend(Session session) {
        super(session);
    }

    @Override
    protected void updateMessageID() throws MessagingException {
        setHeader("Message-ID", "<" + UniqueValue.getUniqueMessageIDValue() + ">");
    }

    /**
     * 邮件真实发送地址与邮件宣称的地址不一样如何解决:setSender /setFrom设置同一地址，不同则会出现上述文档所说内容
     */
    @Override
    public void setFrom(Address address) throws MessagingException {
        super.setFrom(address);
        super.setSender(address);
    }

    public static MimeMessage of(com.doctor.commons.mail.MimeMessage message) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = new MimeMessageExtend(null);
        //from
        mimeMessage.setFrom(message.getFrom().toInternetAddress());

        //to
        mimeMessage.setRecipients(Message.RecipientType.TO, of(message.getTo()));

        //replyTo
        mimeMessage.setReplyTo(of(message.getReplyTo()));

        //cc
        mimeMessage.setRecipients(Message.RecipientType.CC, of(message.getCc()));

        //bcc
        mimeMessage.setRecipients(Message.RecipientType.BCC, of(message.getBcc()));

        //subject
        mimeMessage.setSubject(message.getSubject(), message.getSubjectCharset().name());

        //sentDate
        mimeMessage.setSentDate(message.getSentDate());

        //headers
        for (Entry<String, String> e : message.getHeaders().entrySet()) {
            mimeMessage.setHeader(e.getKey(), e.getValue());
        }

        //priority
        mimeMessage.setHeader(Header_Priority, Integer.toString(message.getPriority().getPriority()));

        List<EmailMessage> emailMessages = message.getEmailMessage();
        List<EmailAttachment> emailAttachments = message.getEmailAttachment();
        MimeMultipart body = new MimeMultipart();
        mimeMessage.setContent(body);
        for (EmailMessage e : emailMessages) {
            if (e instanceof InlineAttachmentEmailMessage) {
                //正文+潜入图片混合
                InlineAttachmentEmailMessage ie = (InlineAttachmentEmailMessage) e;
                MimeBodyPart bodyPart = new MimeBodyPart();
                MimeMultipart mixedSubPart = new MimeMultipart("related");
                MimeBodyPart text = new MimeBodyPart();
                text.setContent(ie.getContent(), Content_Type_Html + Content_Type_Charset_Suffix + ie.getCharset().name().toLowerCase());

                MimeBodyPart image = new MimeBodyPart();
                image.setContentID(ie.getEmailAttachment().getContentId());
                image.setDataHandler(new DataHandler(ie.getEmailAttachment().getDataSource()));
                mixedSubPart.addBodyPart(text);
                mixedSubPart.addBodyPart(image);
                bodyPart.setContent(mixedSubPart);
                body.addBodyPart(bodyPart);
            } else {
                MimeBodyPart bodyPart = new MimeBodyPart();
                if (e.getMimeType().equals(MimeTypes.MIME_TEXT_PLAIN)) {
                    bodyPart.setText(e.getContent(), e.getCharset().name());
                } else if (e.getMimeType().equals(MimeTypes.MIME_TEXT_HTML)) {
                    bodyPart.setContent(e.getContent(), Content_Type_Html + Content_Type_Charset_Suffix + e.getCharset().name().toLowerCase());
                }
                body.addBodyPart(bodyPart);
            }
        }

        for (EmailAttachment e : emailAttachments) {
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.setDataHandler(new DataHandler(e.getDataSource()));
            attachment.setFileName(MimeUtility.encodeText(e.getName()));
            body.addBodyPart(attachment);
        }

        return mimeMessage;
    }

    private static Address[] of(List<EmailAddress> addresses) {
        Address[] add = new Address[addresses.size()];
        for (int i = 0, length = addresses.size(); i < length; i++) {
            add[i] = addresses.get(i).toInternetAddress();
        }
        return add;
    }
}
