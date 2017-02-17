package com.doctor.commons.core;

import java.lang.management.ManagementFactory;

/**
 * Get an application process ID.
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2017.02.17
 *         <p>
 */
public class ApplicationPid {
    private final String pid;

    public ApplicationPid() {
        this.pid = getJvmPid();
    }

    public ApplicationPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public static ApplicationPid get() {
        return new ApplicationPid();
    }

    @Override
    public String toString() {
        return "ApplicationPid [pid=" + pid + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pid == null) ? 0 : pid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApplicationPid other = (ApplicationPid) obj;
        if (pid == null) {
            if (other.pid != null)
                return false;
        } else if (!pid.equals(other.pid))
            return false;
        return true;
    }

    private String getJvmPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        } catch (Exception e) {
            return null;
        }
    }
}
