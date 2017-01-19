package com.doctor.commons.core.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class InetAddressUtilsTest {

    @Test
    public void test_iPv4AddressToLong() throws UnknownHostException {
        String iPv4Address = "192.0.2.1";
        long ip = InetAddressUtils.iPv4AddressToLong(iPv4Address);
        Assert.assertThat(ip, IsEqual.equalTo(3221225985L));
        Assert.assertThat(InetAddressUtils.longToIPv4Address(ip), IsEqual.equalTo(iPv4Address));

        ip = InetAddressUtils.iPv4AddressToLong((Inet4Address) Inet4Address.getByName(iPv4Address));
        Assert.assertThat(ip, IsEqual.equalTo(3221225985L));
        InetAddress inetAddress = InetAddressUtils.longToIPV4Address(ip);
        Assert.assertThat(inetAddress, IsEqual.equalTo(InetAddress.getByName(iPv4Address)));

    }

}
