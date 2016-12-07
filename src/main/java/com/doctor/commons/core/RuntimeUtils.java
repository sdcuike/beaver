package com.doctor.commons.core;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.06
 *         <p>
 */
public final class RuntimeUtils {

    /**
     * Returns the amount of available memory (jvm free memory plus never
     * allocated memory).
     * 
     * @return the amount of available memory,measured in bytes
     */
    public static long availableMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.freeMemory() + (runtime.maxMemory() - runtime.totalMemory());
    }

    public static double availableMemoryPercent() {
        return availableMemory() * 100.0D / Runtime.getRuntime().maxMemory();
    }

}
