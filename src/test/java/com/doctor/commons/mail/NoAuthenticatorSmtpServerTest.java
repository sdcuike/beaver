package com.doctor.commons.mail;

import java.io.File;

import com.doctor.commons.lang.tuple.Pair;

public class NoAuthenticatorSmtpServerTest {

    public static void main(String[] args) throws Throwable {

        SmtpServer server = NoAuthenticatorSmtpServer.create();
        MimeMessage mimeMessage = MimeMessage.create().priority(EmailPriority.Highest);
        mimeMessage.from("神秘博士", "sdcuike@alibaba.com").subject("您有一封来自20年后的您的来信")
                .to("20年后的您", "rain.mr@foxmail.com").emailMessageForText("您好，时间的裂缝")
                .cc("redrain_2016@qq.com").emailAttachmentForFile(new File("D:/1/1_ZAAPPJK-2016-11-04-18-00-03.zip"))
                .emailMessageForHtmlInlineAttachment("<a href = \"http://blog.csdn.net/doctor_who2004\"> Beaver</a></br><img src = \"cid:logo_jpg\"></a>",
                        new FileAttachment(new File("C:/QQ浏览器截屏未命名.jpg"), "logo_jpg"));
        Pair<Boolean, String> sendMail = server.sendMail(mimeMessage);
        System.out.println(sendMail);
    }

}
