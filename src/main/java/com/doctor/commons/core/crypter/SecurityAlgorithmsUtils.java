/*
 * Copyright 2017 Zhongan.com All right reserved. This software is the
 * confidential and proprietary information of Zhongan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Zhongan.com.
 */
package com.doctor.commons.core.crypter;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.doctor.beaver.annotation.Immutable;

/**
 * 安全算法
 * 
 * @author sdcuike Created At 2017年1月18日 14:38:39
 */
@Immutable
public final class SecurityAlgorithmsUtils {

    public static Set<Service> getAllSecurityService() {
        Set<Service> set = new HashSet<>();

        for (Provider provider : Security.getProviders()) {
            set.addAll(provider.getServices());
        }

        return Collections.unmodifiableSet(set);
    }

    public static Set<Service> getAllSecurityServiceByType(ServiceType serviceType) {
        Set<Service> set = new HashSet<>();

        for (Provider provider : Security.getProviders()) {
            for (Service service : provider.getServices()) {
                if (service.getType().equals(serviceType.name())) {
                    set.add(service);
                }
            }
        }

        return Collections.unmodifiableSet(set);
    }

    public enum ServiceType {
        KeyFactory,
        TransformService,
        CertPathBuilder,
        Cipher,
        SecureRandom,
        Signature,
        AlgorithmParameterGenerator,
        KeyPairGenerator,
        XMLSignatureFactory,
        CertificateFactory,
        MessageDigest,
        KeyInfoFactory,
        KeyAgreement,
        CertStore,
        Configuration,
        SSLContext,
        SaslServerFactory,
        AlgorithmParameters,
        TrustManagerFactory,
        GssApiMechanism,
        TerminalFactory,
        KeyGenerator,
        Mac,
        CertPathValidator,
        Policy,
        SaslClientFactory,
        SecretKeyFactory,
        KeyManagerFactory,
        KeyStore;
    }
}
