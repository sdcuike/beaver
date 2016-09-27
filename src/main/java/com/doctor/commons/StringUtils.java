package com.doctor.commons;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月21日
 */
@ThreadSafe
public final class StringUtils {

    /**
     * @param cs
     * @return {@code ture} 参数为null、空和Whitespace
     */
    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return true;
        }

        for (int i = 0, length = cs.length(); i < length; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * @param byteArray
     * @return 16进制字符串
     */
    public static String toHexString(final byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder(37);
        for (int i = 0, length = byteArray.length; i < length; i++) {
            String hexString = Integer.toHexString(0xFF & byteArray[i]);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    private StringUtils() {
        throw new UnsupportedOperationException();
    }
}
