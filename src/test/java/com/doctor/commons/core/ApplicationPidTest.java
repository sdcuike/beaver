package com.doctor.commons.core;

import org.junit.Test;

public class ApplicationPidTest {

    @Test
    public void testGetPid() {
        ApplicationPid applicationPid = ApplicationPid.get();
        System.out.println(applicationPid.getPid());
        String implementationVersion = ApplicationPidTest.class.getPackage().getImplementationVersion();
    }

}
