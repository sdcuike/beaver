package com.doctor.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.doctor.beaver.annotation.NotNull;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.08
 *         <p>
 */
public final class FileCopyUtils {
    public static final int DEFAULT_BUFFER_SIZE = StreamUtils.DEFAULT_BUFFER_SIZE;

    public static int copyNotClose(@NotNull final InputStream in, @NotNull final OutputStream out) throws IOException {
        return StreamUtils.copy(in, out);
    }

    public static int copy(@NotNull final File in, @NotNull final File out) throws FileNotFoundException, IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(in));
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(out));) {
            return copyNotClose(inputStream, outputStream);
        }
    }

    public static int copy(@NotNull final byte[] in, @NotNull final File out) throws IOException {
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(out));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(in);) {
            return StreamUtils.copy(inputStream, outputStream);
        }
    }

    public static byte[] copyToByteArray(@NotNull final File in) throws IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(in))) {
            return StreamUtils.copyToByteArray(inputStream);
        }
    }
}
