package com.doctor.commons;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月22日.
 *         <p>
 *         Refer
 *         {@link http://www.quickprogrammingtips.com/java/how-to-encrypt-and-decrypt-data-in-java-using-aes-algorithm.html}
 *         {@link http://stackoverflow.com/questions/6957406/is-cipher-thread-safe}
 */
@ThreadSafe
public final class AESUtils {

    /**
     * 密钥算法
     */
    private static final String KeyAlgorithm    = "AES";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    private static final String CipherAlgorithm = "AES/CBC/PKCS5Padding";

    private static final String IvParameterSpec = "0102030405060708";

    private static final int    KeySize128      = 128;

    /**
     * 生成Base64字符串密钥
     * 
     * @return {@code String} Base64字符串密钥
     * @throws NoSuchAlgorithmException
     */
    public static String generateBase64StringKey() throws NoSuchAlgorithmException {
        return generateBase64StringKey(KeySize128);
    }

    private static String generateBase64StringKey(Integer keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyAlgorithm);
        keyGenerator.init(keysize);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        //return DatatypeConverter.printBase64Binary(encoded);//android不支持,故不采用
        return Base64Utils.encodeToString(encoded);
    }

    /**
     * 加密数据
     * 
     * @param plainText 待加密字符串
     * @param base64StringKey Base64字符串密钥
     * @return {@code String} 加密后的Base64字符串
     * @throws GeneralSecurityException
     */
    public static String encryptToBase64String(String plainText, String base64StringKey)
            throws GeneralSecurityException {

        return encryptToBase64String(plainText, base64StringKey, IvParameterSpec);
    }

    public static String encryptToBase64String(String plainText, String base64StringKey,
                                               String ivParameterSpec16Size)
            throws GeneralSecurityException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParameterSpec16Size.getBytes(StandardCharsets.UTF_8));
        SecretKey key = getkeyFromBase64String(base64StringKey);
        Cipher cipher = Cipher.getInstance(CipherAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] bt = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        //return DatatypeConverter.printBase64Binary(bt);
        return Base64Utils.encodeToString(bt);
    }

    /**
     * 解密数据
     * 
     * @param cipheredBase64String 加密后的Base64字符串
     * @param base64StringKey Base64字符串密钥
     * @return {@code String} 解密后的明文
     * @throws GeneralSecurityException
     */
    public static String decryptFromBase64String(String cipheredBase64String, String base64StringKey)
            throws GeneralSecurityException {

        return decryptFromBase64String(cipheredBase64String, base64StringKey, IvParameterSpec);

    }

    public static String decryptFromBase64String(String cipheredBase64String, String base64StringKey,
                                                 String ivParameterSpec16Size)
            throws GeneralSecurityException {

        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivParameterSpec16Size.getBytes(StandardCharsets.UTF_8));
        SecretKey key = getkeyFromBase64String(base64StringKey);
        Cipher cipher = Cipher.getInstance(CipherAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] base64Binary = Base64Utils.decode(cipheredBase64String);
        byte[] bt = cipher.doFinal(base64Binary);
        return new String(bt, StandardCharsets.UTF_8);
    }

    private static SecretKey getkeyFromBase64String(String base64StringKey) {
        byte[] base64Binary = Base64Utils.decode(base64StringKey);
        return new SecretKeySpec(base64Binary, KeyAlgorithm);
    }

}
