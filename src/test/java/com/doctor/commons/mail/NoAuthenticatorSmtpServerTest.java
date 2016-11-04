package com.doctor.commons.mail;

import java.io.File;
import java.util.Properties;

import org.junit.Test;

import com.doctor.commons.lang.tuple.Pair;

public class NoAuthenticatorSmtpServerTest {

    @Test
    public void testSendMail() throws Throwable {
        Properties config = new Properties();
        NoAuthenticatorSmtpServer server = new NoAuthenticatorSmtpServer(config);
        MimeMessage mimeMessage = MimeMessage.create();
        mimeMessage.from("神秘博士", "sdcuike@alibaba.com").subject("您有一封来自20年后的您的来信")
                .to("20年后的您", "rain.mr@foxmail.com").emailMessageForText("您好，时间的裂缝")
                .cc("cuikexiang@zhongan.com").emailAttachmentForFile(new File("D:/1/1_ZAAPPJK-2016-11-04-18-00-03.zip"), "照片.zip");
        Pair<Boolean, String> sendMail = server.sendMail(mimeMessage);
        System.out.println(sendMail);
    }

}
