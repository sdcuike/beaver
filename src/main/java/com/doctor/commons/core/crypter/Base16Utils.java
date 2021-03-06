package com.doctor.commons.core.crypter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.30.
 *         <p>
 */
public final class Base16Utils {

    private static final String Base16 = "0123456789ABCDEF";

    /**
     * Convert binary data to a hex-encoded String
     * 
     * @param b An array containing binary data
     * @return A String containing the encoded data
     */
    public static final String toString(byte[] b) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        for (int i = 0; i < b.length; i++) {
            short value = (short) (b[i] & 0xFF);
            byte high = (byte) (value >> 4);
            byte low = (byte) (value & 0xF);
            os.write(Base16.charAt(high));
            os.write(Base16.charAt(low));
        }

        return new String(os.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * Convert a hex-encoded String to binary data
     * 
     * @param str A String containing the encoded data
     * @return An array containing the binary data, or null if the string is
     *         invalid
     */
    public static final byte[] toByteArray(String str) {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        byte[] raw = str.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < raw.length; i++) {
            if (!Character.isWhitespace((char) raw[i])) {
                bs.write(raw[i]);
            }
        }

        byte[] in = bs.toByteArray();

        if ((in.length % 2) != 0) {
            return null;
        }

        bs.reset();

        DataOutputStream ds = new DataOutputStream(bs);

        for (int i = 0; i < in.length; i += 2) {
            byte high = (byte) Base16.indexOf(Character.toUpperCase(
                    (char) in[i]));
            byte low = (byte) Base16.indexOf(Character.toUpperCase(
                    (char) in[i + 1]));

            try {
                ds.writeByte((high << 4) + low);
            } catch (IOException e) {
            }
        }

        return bs.toByteArray();
    }

}
