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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.doctor.beaver.annotation.Immutable;

/**
 * @author sdcuike
 *
 *         Created At 2017年1月17日 下午11:48:29
 */
@Immutable
public final class RSAUtils {
    private static final String Key_Algorithm = "RSA";
    private static final int    KEY_SIZE      = 2048;

    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(KEY_SIZE);
    }

    public static RsaKeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(Key_Algorithm);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        return new RsaKeyPair(Base64Utils.encodeToString(publicKey.getEncoded()),
                Base64Utils.encodeToString(privateKey.getEncoded()));
    }

    public static final class RsaKeyPair {
        private String base64PublicKey;
        private String base64PrivateKey;

        public RsaKeyPair(String base64PublicKey, String base64PrivateKey) {
            this.base64PublicKey = base64PublicKey;
            this.base64PrivateKey = base64PrivateKey;
        }

        public String getBase64PublicKey() {
            return base64PublicKey;
        }

        public String getBase64PrivateKey() {
            return base64PrivateKey;
        }

        @Override
        public String toString() {
            return "RsaKeyPair [base64PublicKey=" + base64PublicKey + ", base64PrivateKey=" + base64PrivateKey + "]";
        }
    }
}
