package com.doctor.commons;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月21日
 * @see https://www.mkyong.com/java/java-md5-hashing-example/
 */
@ThreadSafe
public final class MD5Utils {

    private static String MD5 = "MD5";

    /**
     * @param str
     * @return 32位 小写 16进制字符串
     * @throws NoSuchAlgorithmException
     */
    public static String md5To32LowerCaseHexString(String str) throws NoSuchAlgorithmException {
        return md5ToHexString(str);
    }

    /**
     * @param str
     * @return 16位 小写 16进制字符串
     * @throws NoSuchAlgorithmException
     */
    public static String md5To16LowerCaseHexString(String str) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return md5ToHexString(str).substring(8, 24);
    }

    private static String md5ToHexString(String str) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        MessageDigest digest = MessageDigest.getInstance(MD5);
        digest.update(str.getBytes(StandardCharsets.UTF_8));
        byte[] b = digest.digest();

        return StringUtils.toHexString(b);
    }

    private MD5Utils() {
        throw new UnsupportedOperationException();
    }

}
