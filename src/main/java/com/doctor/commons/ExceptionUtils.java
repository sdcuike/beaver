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

}
