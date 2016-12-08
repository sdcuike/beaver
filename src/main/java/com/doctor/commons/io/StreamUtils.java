package com.doctor.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.doctor.beaver.annotation.NotNull;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.08
 *         <p>
 */
public final class StreamUtils {

    public static final int    DEFAULT_BUFFER_SIZE = 4096;
    public static final byte[] EMPTY_BYTE_ARRAY    = new byte[] {};

    public static byte[] copyToByteArray(@NotNull final InputStream inputStream) throws IOException {
        //ByteArrayOutputStream无需关闭流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String copyToString(@NotNull final InputStream inputStream, @NotNull final Charset charset) throws IOException {
        int readNum = -1;
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);

        while ((readNum = inputStreamReader.read(buffer)) != -1) {
            sb.append(buffer, 0, readNum);
        }

        return sb.toString();
    }

    public static void copyByteToStream(@NotNull final byte[] bytes, @NotNull final OutputStream out) throws IOException {
        out.write(bytes);
    }

    public static void copyStringToStream(String str, Charset charset, OutputStream out) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(out, charset);
        writer.write(str);
        writer.flush();
    }

    public static int copy(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int count = 0;
        int readNum = -1;
        while ((readNum = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readNum);
            count += readNum;
        }
        outputStream.flush();
        return count;
    }

    /**
     * 忽略数据流中剩余内容
     * 
     * @param inputStream
     * @return 读取的字节数目
     * @throws IOException
     */
    public static int drain(@NotNull final InputStream inputStream) throws IOException {
        int count = 0;
        int readByte = -1;

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while ((readByte = inputStream.read(buffer)) != -1) {
            count += readByte;
        }
        return count;
    }

}
