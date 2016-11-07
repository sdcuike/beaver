package com.doctor.commons.mail;

import java.io.IOException;

import com.doctor.commons.lang.tuple.Pair;

public interface SmtpServer {
    Pair<Boolean, String> sendMail(final MimeMessage mimeMessage) throws IOException;
}
