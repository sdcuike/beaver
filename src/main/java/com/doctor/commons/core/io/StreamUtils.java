package com.doctor.commons.core.io;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channel;
import java.nio.charset.Charset;

import com.doctor.beaver.annotation.NotNull;
import com.doctor.beaver.annotation.Nullable;

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
        return copyToByteArray(inputStream, DEFAULT_BUFFER_SIZE);
    }

    public static byte[] copyToByteArray(@NotNull final InputStream inputStream, @NotNull final int bufferSize) throws IOException {
        //ByteArrayOutputStream无需关闭流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bufferSize);
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String copyToString(@NotNull final InputStream inputStream, @NotNull final Charset charset) throws IOException {
        return copyToString(inputStream, charset, DEFAULT_BUFFER_SIZE);
    }

    public static String copyToString(@NotNull final InputStream inputStream, @NotNull final Charset charset, @NotNull final int bufferSize) throws IOException {
        int readNum = -1;
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[bufferSize];

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
        return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    public static int copy(@NotNull final InputStream inputStream, @NotNull final OutputStream outputStream, @NotNull final int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        int readNum = -1;
        while ((readNum = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, readNum);
            count += readNum;
        }
        outputStream.flush();
        return count;
    }

    public static int copyNotClose(@NotNull final Reader in, @NotNull final Writer out) throws IOException {
        return copyNotClose(in, out, DEFAULT_BUFFER_SIZE);
    }

    public static int copyNotClose(@NotNull final Reader in, @NotNull final Writer out, @NotNull final int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];
        int count = 0;
        int readByte = -1;

        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
            count += readByte;
        }
        out.flush();
        return count;
    }

    public static void copyNotClose(@NotNull final String in, @NotNull final Writer out) throws IOException {
        out.write(in);
    }

    public static String copyToString(@NotNull final Reader in) throws IOException {
        StringWriter out = new StringWriter();
        copyNotClose(in, out);
        return out.toString();
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

    //以下closeQuietly方法，请优先使用try-with-resources 语法；最好用在finally块，try中显示关闭资源。

    public static void closeQuietly(@Nullable final Channel channel) {
        if (channel == null) {
            return;
        }

        try {
            channel.close();
        } catch (IOException e) {

        }
    }

    public static void closeQuietly(@Nullable final InputStream in) {
        closeQuietly(in);
    }

    public static void closeQuietly(@Nullable final OutputStream out) {
        closeQuietly(out);
    }

    public static void closeQuietly(@Nullable final Reader reader) {
        closeQuietly(reader);
    }

    public static void closeQuietly(@Nullable final Writer writer) {
        closeQuietly(writer);
    }

    public static void closeQuietly(@Nullable final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

        } catch (IOException e) {

        }
    }

    public static void closeQuietly(@Nullable final Closeable... closeables) {
        if (closeables == null) {
            return;
        }

        for (Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    public static void closeQuietly(@Nullable final ServerSocket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    public static void closeQuietly(@Nullable final Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {

            }
        }
    }
}
