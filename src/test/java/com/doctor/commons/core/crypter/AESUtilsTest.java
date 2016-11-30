package com.doctor.commons.core.crypter;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.doctor.commons.core.crypter.AESUtils;

public class AESUtilsTest {

    private String base64StringKey = "ABaaSRoBIDgzUF5A2Oq/Dw==";
    private String plainText       = "AES (Advanced Encryption Standard) is a strong symmetric encryption algorithm."
            + " A secret key is used for the both encryption and decryption of data. Only someone who has access to"
            + " the same secret key can decrypt data. AES encryption provides strong protection to your data.";

    private String ivParameterSpec = plainText.substring(0, 16);

    @Test
    public void testGenerateBase64StringKey() throws Throwable {
        String generateBase64StringKey = AESUtils.generateBase64StringKey();

        System.out.println(generateBase64StringKey);

    }

    @Test
    public void testEncryptToBase64StringStringString() throws Throwable {
        String encryptedBase64String = AESUtils.encryptToBase64String(plainText, base64StringKey);
        System.out.println(encryptedBase64String);
        String decryptFromBase64String = AESUtils.decryptFromBase64String(encryptedBase64String, base64StringKey);
        System.out.println(decryptFromBase64String);
        Assert.assertThat(decryptFromBase64String, IsEqual.equalTo(plainText));

    }

    @Test
    public void testEncryptToBase64StringStringStringIvParameterSpec() throws Throwable {
        String encryptedBase64String = AESUtils.encryptToBase64String(plainText, base64StringKey, ivParameterSpec);
        System.out.println(encryptedBase64String);
        String decryptFromBase64String = AESUtils.decryptFromBase64String(encryptedBase64String, base64StringKey,
                ivParameterSpec);
        System.out.println(decryptFromBase64String);
        Assert.assertThat(decryptFromBase64String, IsEqual.equalTo(plainText));
    }

}
