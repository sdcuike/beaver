package com.doctor.commons.mail;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 内部使用
 * <p>
 * This is a utility class that generates unique values. The generated String
 * contains only US-ASCII characters and hence is safe for use in RFC822
 * headers.
 * </p>
 * 
 * @see javax.mail.internet.UniqueValue
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.03
 *         <p>
 */
class UniqueValue {

    private static final String     suffix = "sdcuike@doctorwho.com";

    /**
     * A global unique number, to ensure uniqueness of generated strings.
     */
    private static final AtomicLong id     = new AtomicLong();

    /**
     * Get a unique value for use in a Message-ID. This implementation generates
     * it by concatenating a newly created object's <code>hashCode()</code>, a
     * global ID (incremented on every use), the current time (in milliseconds),
     * the string "JavaMail", and this user's local address generated by
     * <code>InternetAddress.getLocalAddress()</code>. (The address defaults to
     * "javamailuser@localhost" if <code>getLocalAddress()</code> returns null.)
     * <p>
     * 为了不暴漏本地机器，正常：Message-ID:
     * <597255128.0.1465725132963.JavaMail.doctor@doctorwho-MacBook-Pro.local>
     * 会暴露你的个人信息，请修改机器hostname和用户名， 这里统一设置了。
     * 
     * @param ssn Session object used to get the local address
     * @see javax.mail.internet.InternetAddress
     */
    public static String getUniqueMessageIDValue() {
        StringBuffer s = new StringBuffer();
        // Unique string is <hashcode>.<id>.<currentTime>.JavaMail.<suffix>
        s.append(s.hashCode()).append('.').append(getUniqueId()).append('.').append(System.currentTimeMillis()).append('.').append("JavaMail.").append(suffix);
        return s.toString();
    }

    /**
     * Ensure ID is unique by synchronizing access. XXX - Could use
     * AtomicInteger.getAndIncrement() in J2SE 5.0.
     */
    private static long getUniqueId() {
        return id.incrementAndGet();
    }

}
