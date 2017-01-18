package com.doctor.commons.core.crypter;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.doctor.commons.core.crypter.RSAUtils.RsaKeyPair;

public class RSAUtilsTest {

    @Test
    public void test_encrypt_decrypt() throws Throwable {
        //        String plainText = AESUtils.generateBase64StringKey();
        String plainText = "import com.doctor.commons.core.crypter.RSAUtils.RsaKeyPair;ThreadLocal  类，"
                + "主要提供和线程绑定的数据存储，一般以单例模式方式使用此类。";

        RsaKeyPair keyPair = RSAUtils.generateKeyPair();

        String encryptText = RSAUtils.encryptToBase64StringByPrivateKey(plainText, keyPair.getBase64StringPrivateKey());
        String decryptedText = RSAUtils.decryptFromBase64StringByPublicKey(encryptText, keyPair.getBase64StringPublicKey());
        Assert.assertThat(decryptedText, IsEqual.equalTo(plainText));
        //
        String encryptText2 = RSAUtils.encryptToBase64StringByPublicKey(plainText, keyPair.getBase64StringPublicKey());
        String decryptedText2 = RSAUtils.decryptFromBase64StringByPrivateKey(encryptText2, keyPair.getBase64StringPrivateKey());
        Assert.assertThat(decryptedText2, IsEqual.equalTo(plainText));

    }

    @Test
    public void test_sign() throws Throwable {
        String plainText = "jdk对ThreadLocal  类的设计主要考虑了两方面：线程安全、内存泄漏风险"
                + "Java provides some APIs to generate and verify digital signature. One important class is Signature. ";
        RsaKeyPair keyPair = RSAUtils.generateKeyPair();

        String signedBase64String = RSAUtils.signToBase64String(plainText, keyPair.getBase64StringPrivateKey());
        System.out.println(signedBase64String);
        Assert.assertTrue(RSAUtils.verifyBase64SignedString(plainText, signedBase64String, keyPair.getBase64StringPublicKey()));
    }

}
