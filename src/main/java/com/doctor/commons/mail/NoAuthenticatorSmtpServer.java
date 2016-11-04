package com.doctor.commons.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.URLName;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.doctor.commons.ExceptionUtils;
import com.doctor.commons.lang.tuple.Pair;
import com.sun.mail.smtp.SMTPTransport;

public final class NoAuthenticatorSmtpServer {

    private static final String SMTP_PROTOCOL_PREFIX        = "smtp://";

    private static final String mail_mime_charset           = "mail.mime.charset";
    private static final String mail_smtp_connectiontimeout = "mail.smtp.connectiontimeout";
    private static final String mail_smtp_timeout           = "mail.smtp.timeout";
    private static final String mail_smtp_host              = "mail.smtp.host";
    public static final String  mail_host                   = "mail.host";
    private static final String mail_smtp_localhost         = "mail.smtp.localhost";
    private static final String mail_smtp_debug             = "mail.debug";

    private boolean             debug                       = false;

    private Properties          config                      = new Properties();
    {
        config = System.getProperties();
        config.setProperty(mail_mime_charset, "UTF-8");
        if (debug) {
            config.setProperty(mail_smtp_debug, "true");
        }
        config.setProperty(mail_smtp_timeout, "30000");
        config.setProperty(mail_smtp_connectiontimeout, "30000");
        config.setProperty(mail_smtp_localhost, "doctorwho.com");
        config.setProperty(mail_smtp_host, "doctorwho.com");
        config.setProperty(mail_host, "doctorwho.com");
    }

    public NoAuthenticatorSmtpServer(Properties config) {
        for (Entry<Object, Object> es : config.entrySet()) {
            this.config.put(es.getKey(), es.getValue());
        }
    }

    public Pair<Boolean, String> sendMail(final MimeMessage mimeMessage) throws MessagingException, IOException {
        final javax.mail.internet.MimeMessage mailMessage = MimeMessageExtend.of(mimeMessage);

        List<EmailAddress> emailAddresses = new ArrayList<>();
        emailAddresses.addAll(mimeMessage.getTo());
        emailAddresses.addAll(mimeMessage.getCc());
        emailAddresses.addAll(mimeMessage.getBcc());

        Pair<Boolean, String> result = new Pair<>();
        for (EmailAddress e : emailAddresses) {
            String email = e.getEmail();
            String hostName = email.substring(email.lastIndexOf("@") + 1);
            Set<URLName> mxRecordsForHost = getMXRecordsForHost(hostName);
            if (mxRecordsForHost.isEmpty()) {
                throw new IllegalArgumentException("mxRecords for " + email + " is empty");
            }

            boolean sendSuccessfully = false;
            Iterator<URLName> recordIterator = mxRecordsForHost.iterator();
            while (!sendSuccessfully && recordIterator.hasNext()) {

                SMTPTransport transport = null;
                try {
                    Session session = getSession();
                    Properties props = session.getProperties();
                    //
                    props.put("mail.smtp.from", mimeMessage.getFrom().getEmail());

                    URLName url = recordIterator.next();

                    transport = (SMTPTransport) session.getTransport(url);
                    transport.connect();
                    transport.sendMessage(mailMessage, new Address[] { e.toInternetAddress() });
                    sendSuccessfully = true;
                    String lastServerResponse = transport.getLastServerResponse();

                    result.setLeft(Boolean.TRUE);
                    result.setRight(lastServerResponse);
                } catch (Exception e1) {
                    result.setRight(ExceptionUtils.printStackTraceToString(e1));
                } finally {
                    if (transport != null) {
                        transport.close();
                        transport = null;
                    }
                }

            }
        }

        return result;
    }

    private Session getSession() {
        return Session.getInstance(config, null);
    }

    private Set<URLName> getMXRecordsForHost(String hostName) throws TextParseException {

        Set<URLName> recordsColl = new HashSet<>();

        boolean foundOriginalMX = true;
        Record[] records = new Lookup(hostName, Type.MX).run();

        /*
         * Sometimes we should send an email to a subdomain which does not have
         * own MX record and MX server. At this point we should find an upper
         * level domain and server where we can deliver our email. Example:
         * subA.subB.domain.name has not own MX record and subB.domain.name is
         * the mail exchange master of the subA domain too.
         */
        if (records == null || records.length == 0) {
            foundOriginalMX = false;
            String upperLevelHostName = hostName;
            while (records == null &&
                    upperLevelHostName.indexOf(".") != upperLevelHostName.lastIndexOf(".") &&
                    upperLevelHostName.lastIndexOf(".") != -1) {
                upperLevelHostName = upperLevelHostName.substring(upperLevelHostName.indexOf(".") + 1);
                records = new Lookup(upperLevelHostName, Type.MX).run();
            }
        }

        if (records != null) {
            // Sort in MX priority (higher number is lower priority)
            Arrays.sort(records, new Comparator<Record>() {
                @Override
                public int compare(Record arg0, Record arg1) {
                    return ((MXRecord) arg0).getPriority() - ((MXRecord) arg1).getPriority();
                }
            });
            // Create records collection
            for (int i = 0; i < records.length; i++) {
                MXRecord mx = (MXRecord) records[i];
                String targetString = mx.getTarget().toString();
                URLName uName = new URLName(
                        SMTP_PROTOCOL_PREFIX +
                                targetString.substring(0, targetString.length() - 1));
                recordsColl.add(uName);
            }
        } else {
            foundOriginalMX = false;
        }

        /*
         * If we found no MX record for the original hostname (the upper level
         * domains does not matter), then we add the original domain name
         * (identified with an A record) to the record collection, because the
         * mail exchange server could be the main server too. We append the A
         * record to the first place of the record collection, because the
         * standard says if no MX record found then we should to try send email
         * to the server identified by the A record.
         */
        if (!foundOriginalMX) {
            Record[] recordsTypeA = new Lookup(hostName, Type.A).run();
            if (recordsTypeA != null && recordsTypeA.length > 0) {
                recordsColl.add(new URLName(SMTP_PROTOCOL_PREFIX + hostName));
            }
        }

        return recordsColl;
    }

}
