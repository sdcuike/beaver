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
package com.doctor.commons.core.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import com.doctor.beaver.annotation.Immutable;

/**
 * @author sdcuike Created At 2017.01.18
 */
@Immutable
public final class InetAddressUtils {
    private static final Pattern IPV4_PATTERN                = Pattern.compile(
            "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    private static final Pattern IPV6_STD_PATTERN            = Pattern.compile(
            "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile(
            "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    private static final char    COLON_CHAR                  = ':';
    private static final int     MAX_COLON_COUNT             = 7;

    public static boolean isIPv4Address(String ip) {
        return IPV4_PATTERN.matcher(ip).matches();
    }

    public static boolean isIPv6StdAddress(String ip) {
        return IPV6_STD_PATTERN.matcher(ip).matches();
    }

    public static boolean isIPv6HexCompressedAddress(String ip) {
        int count = 0;
        for (int i = 0, length = ip.length(); i < length; i++) {
            if (ip.charAt(i) == COLON_CHAR) {
                count++;
            }
        }

        return count <= MAX_COLON_COUNT && IPV6_HEX_COMPRESSED_PATTERN.matcher(ip).matches();
    }

    public static boolean isIPv6Address(String ip) {
        return isIPv6StdAddress(ip) || isIPv6HexCompressedAddress(ip);
    }

    /**
     * 验证：
     * <a href="https://www.vultr.com/tools/ipv4-converter/"> https://www.vultr.
     * com/tools/ipv4-converter/</a>
     * 
     * @param iPv4Address
     * @return {@code long}
     */
    public static long iPv4AddressToLong(String iPv4Address) {
        String[] arrays = iPv4Address.split("\\.");
        if (arrays.length != 4) {
            throw new IllegalArgumentException("不合法的ipv4");
        }

        long ip = ((Long.parseLong(arrays[0]) & 0xff) << 24)
                | ((Long.parseLong(arrays[1]) & 0xff) << 16)
                | ((Long.parseLong(arrays[2]) & 0xff) << 8)
                | (Long.parseLong(arrays[3]) & 0xff);

        return ip;
    }

    public static long iPv4AddressToLong(Inet4Address iPv4Address) {
        String hostAddress = iPv4Address.getHostAddress();
        return iPv4AddressToLong(hostAddress);
    }

    public static String longToIPv4Address(long iPv4) {
        String ip = ((iPv4 >>> 24) & 0xff) + "."
                + ((iPv4 >>> 16) & 0xff) + "."
                + ((iPv4 >>> 8) & 0xff) + "."
                + (iPv4 & 0xff);

        return ip;
    }

    public static InetAddress longToIPV4Address(long iPv4) throws UnknownHostException {
        String address = longToIPv4Address(iPv4);
        return InetAddress.getByName(address);
    }

    private InetAddressUtils() {
        throw new UnsupportedOperationException();
    }
}
