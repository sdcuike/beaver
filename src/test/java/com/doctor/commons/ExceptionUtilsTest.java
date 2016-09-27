package com.doctor.commons;

import java.io.IOException;

import org.junit.Test;

public class ExceptionUtilsTest {

    @Test
    public void testPrintStackTraceToString() throws IOException {
        IllegalAccessError illegalAccessError = new IllegalAccessError("error");
        System.out.println(ExceptionUtils.printStackTraceToString(illegalAccessError));

        IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(
                "IndexOutOfBoundsException");
        System.out.println(ExceptionUtils.printStackTraceToString(indexOutOfBoundsException));

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("IllegalArgumentException");
        System.out.println(ExceptionUtils.printStackTraceToString(illegalArgumentException));
    }

    @Test
    public void testPrintFullStackTraceToString() throws IOException {

        IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(
                "IndexOutOfBoundsException");
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("IllegalArgumentException",
                indexOutOfBoundsException);

        System.out.println(ExceptionUtils.printFullStackTraceToString(illegalArgumentException));
    }

}
