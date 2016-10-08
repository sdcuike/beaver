package com.doctor.commons;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月27日<br/>
 */
@ThreadSafe
public final class ExceptionUtils {

    public static String printStackTraceToString(final Throwable t) throws IOException {
        try (StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);) {
            t.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        }
    }

    public static String printFullStackTraceToString(final Throwable t) throws IOException {
        try (StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);) {
            Throwable caseT = t;
            while (caseT != null) {
                caseT.printStackTrace(pw);
                caseT = caseT.getCause();
            }
            pw.flush();
            sw.flush();
            return sw.toString();
        }
    }

    /**
     * 判断异常是否是非受检异常。具体查看 java doc {@link Error}与{@link RuntimeException}
     * 
     * @param ex
     * @return {@code boolean}
     * @see java.lang.Exception
     * @see java.lang.RuntimeException
     * @see java.lang.Error
     */
    public static boolean isUnCheckedException(Throwable ex) {
        return (ex instanceof Error || ex instanceof RuntimeException);
    }

    /**
     * 判断异常是否时候受检异常
     * 
     * @param ex
     * @return {@code boolean}
     * @see java.lang.Exception
     * @see java.lang.RuntimeException
     * @see java.lang.Error
     */
    public static boolean isCheckedException(Throwable ex) {
        return !isUnCheckedException(ex);
    }

}
