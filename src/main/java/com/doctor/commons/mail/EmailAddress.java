package com.doctor.commons.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.doctor.beaver.annotation.ThreadSafe;
import com.doctor.commons.StringUtils;

/**
 * 邮箱地址格式：支持以下格式<br>
 * user@host.domain 或者 nickName <user@host.domain> <br>
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.03
 *         <p>
 */
@ThreadSafe
public class EmailAddress {

    /**
     * 发件人/收件人的昵称
     */
    private final String nickName;
    /**
     * 邮箱地址user@host.domain格式
     */
    private final String email;

    public EmailAddress(EmailAddress emailAddress) {
        this(emailAddress.getNickName(), emailAddress.getEmail());
    }

    public EmailAddress(String nickName, String email) {
        this.nickName = nickName.trim();
        this.email = email.trim();
    }

    public EmailAddress(String emailAddress) {
        EmailAddress address = parse(emailAddress);
        this.nickName = address.nickName;
        this.email = address.email;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        if (StringUtils.isBlank(nickName)) {
            return email;
        }
        return nickName + "<" + email + ">";
    }

    public static EmailAddress parse(String emailAddress) {
        emailAddress = emailAddress.trim();
        int index = emailAddress.indexOf("<");
        if (index < 0) {
            return new EmailAddress("", emailAddress);
        } else {
            return new EmailAddress(emailAddress.substring(0, index), emailAddress.substring(index + 1, emailAddress.length() - 1));
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
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
        EmailAddress other = (EmailAddress) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (nickName == null) {
            if (other.nickName != null)
                return false;
        } else if (!nickName.equals(other.nickName))
            return false;
        return true;
    }

    public InternetAddress toInternetAddress() {
        try {
            if (StringUtils.isBlank(nickName)) {
                return new InternetAddress(email);
            }

            return new InternetAddress(email, nickName);

        } catch (AddressException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
