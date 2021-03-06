/*
 * Copyright 2016    https://github.com/sdcuike Inc. 
 * All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.doctor.commons.core.crypter;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.doctor.beaver.annotation.Immutable;

/**
 * RSA算法实现
 * 
 * @author sdcuike Created At 2017年1月17日 下午11:48:29
 */
@Immutable
public final class RSAUtils {
    private static final String Key_Algorithm       = "RSA";
    private static final int    KEY_SIZE            = 2048;                  //1024容易破解；

    private static final String Cipher_Algorithm    = "RSA/ECB/PKCS1Padding";

    private static final String Signature_Algorithm = "SHA512withRSA";

    /**
     * 生成公钥、私钥
     * 
     * @return {@code RsaKeyPair}
     * @throws NoSuchAlgorithmException
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(KEY_SIZE);
    }

    /**
     * 生成公钥、私钥
     * 
     * @param keySize key的大小
     * @return {@code RsaKeyPair}
     * @throws NoSuchAlgorithmException
     */
    public static RsaKeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Key_Algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        return new RsaKeyPair(Base64Utils.encodeToString(publicKey.getEncoded()),
                Base64Utils.encodeToString(privateKey.getEncoded()));
    }

    /**
     * 私钥加密
     * 
     * @param plainText 明文
     * @param base64StringPrivateKey 私钥（以base64字符串形式使用）
     * @return {@code String}
     * @throws GeneralSecurityException
     */
    public static String encryptToBase64StringByPrivateKey(String plainText, String base64StringPrivateKey) throws GeneralSecurityException {
        PrivateKey privateKey = getPrivateKeyFromBase64String(base64StringPrivateKey);
        Cipher cipher = Cipher.getInstance(Cipher_Algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(bytes);
    }

    /**
     * 公钥解密
     * 
     * @param encryptedBase64String
     * @param base64StringPublicKey
     * @return
     * @throws GeneralSecurityException
     */
    public static String decryptFromBase64StringByPublicKey(String encryptedBase64String, String base64StringPublicKey) throws GeneralSecurityException {
        PublicKey publicKey = getPublicKeyFromBase64String(base64StringPublicKey);
        Cipher cipher = Cipher.getInstance(Cipher_Algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes = Base64Utils.decode(encryptedBase64String);
        byte[] bs = cipher.doFinal(bytes);
        return new String(bs, StandardCharsets.UTF_8);
    }

    public static String encryptToBase64StringByPublicKey(String plainText, String base64StringPublicKey) throws GeneralSecurityException {
        PublicKey publicKey = getPublicKeyFromBase64String(base64StringPublicKey);
        Cipher cipher = Cipher.getInstance(Cipher_Algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64Utils.encodeToString(bytes);
    }

    public static String decryptFromBase64StringByPrivateKey(String encryptedBase64String, String base64StringPrivateKey) throws GeneralSecurityException {
        PrivateKey privateKey = getPrivateKeyFromBase64String(base64StringPrivateKey);
        Cipher cipher = Cipher.getInstance(Cipher_Algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = Base64Utils.decode(encryptedBase64String);
        byte[] bs = cipher.doFinal(bytes);
        return new String(bs, StandardCharsets.UTF_8);
    }

    public static String signToBase64String(String plainText, String base64StringPrivateKey) throws GeneralSecurityException {
        Signature signature = getSignature();
        signature.initSign(getPrivateKeyFromBase64String(base64StringPrivateKey));
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] sign = signature.sign();
        return Base64Utils.encodeToString(sign);
    }

    public static boolean verifyBase64SignedString(String plainText, String base64SignedString, String base64StringPublicKey) throws GeneralSecurityException {
        Signature signature = getSignature();
        signature.initVerify(getPublicKeyFromBase64String(base64StringPublicKey));
        signature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = Base64Utils.decode(base64SignedString);
        return signature.verify(bytes);
    }

    private static PublicKey getPublicKeyFromBase64String(String base64StringPublicKey) throws GeneralSecurityException {
        KeyFactory keyFactory = getKeyFactory();
        return keyFactory.generatePublic(new X509EncodedKeySpec(Base64Utils.decode(base64StringPublicKey)));
    }

    private static PrivateKey getPrivateKeyFromBase64String(String base64StringPrivateKey) throws GeneralSecurityException {
        KeyFactory keyFactory = getKeyFactory();
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64Utils.decode(base64StringPrivateKey)));
    }

    private static KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(Key_Algorithm);
    }

    private static Signature getSignature() throws NoSuchAlgorithmException {
        return Signature.getInstance(Signature_Algorithm);
    }

    public static final class RsaKeyPair {
        private String base64StringPublicKey;
        private String base64StringPrivateKey;

        public RsaKeyPair(String base64StringPublicKey, String base64StringPrivateKey) {
            this.base64StringPublicKey = base64StringPublicKey;
            this.base64StringPrivateKey = base64StringPrivateKey;
        }

        public String getBase64StringPublicKey() {
            return base64StringPublicKey;
        }

        public String getBase64StringPrivateKey() {
            return base64StringPrivateKey;
        }

        @Override
        public String toString() {
            return "RsaKeyPair [base64StringPublicKey=" + base64StringPublicKey + ", base64StringPrivateKey=" + base64StringPrivateKey + "]";
        }

    }
}
