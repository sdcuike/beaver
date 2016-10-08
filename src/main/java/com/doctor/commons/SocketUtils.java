package com.doctor.commons;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

import javax.net.ServerSocketFactory;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月8日<br/>
 */
@ThreadSafe
public final class SocketUtils {

    /**
     * 可用端口号最小值
     */
    public static final int Port_Range_Min = 1024;
    /**
     * 可用端口号最大值
     */
    public static final int Port_Range_Max = 65535;

    /**
     * 随机获取一个可用的TCP端口号
     * 
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableTcpPort() {
        return SocketType.Tcp.getRandomAvailablePort(Port_Range_Min, Port_Range_Max);
    }

    /**
     * 随机获取一个可用的TCP端口号
     * 
     * @param minPort
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableTcpPort(int minPort) {
        return SocketType.Tcp.getRandomAvailablePort(minPort, Port_Range_Max);
    }

    /**
     * 随机获取一个可用的TCP端口号
     * 
     * @param minPort
     * @param maxPort
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableTcpPort(int minPort, int maxPort) {
        return SocketType.Tcp.getRandomAvailablePort(minPort, maxPort);
    }

    public static SortedSet<Integer> getRandomAvailableTcpPorts(int maxNum, int minPort, int maxPort) {
        return SocketType.Tcp.getAvailablePorts(maxNum, minPort, maxPort);
    }

    /**
     * 随机获取一个可用的UDP端口号
     * 
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableUdpPort() {
        return SocketType.Udp.getRandomAvailablePort(Port_Range_Min, Port_Range_Max);
    }

    /**
     * 随机获取一个可用的UDP端口号
     * 
     * @param minPort
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableUdpPort(int minPort) {
        return SocketType.Udp.getRandomAvailablePort(minPort, Port_Range_Max);
    }

    /**
     * 随机获取一个可用的UDP端口号
     * 
     * @param minPort
     * @param maxPort
     * @return {@code int} -1:没找到; > 0:可用端口号
     */
    public static int getRandomAvailableUdpPort(int minPort, int maxPort) {
        return SocketType.Udp.getRandomAvailablePort(minPort, maxPort);
    }

    /**
     * 随机获取一组可用的UDP端口号
     * 
     * @param maxNum
     * @param minPort
     * @param maxPort
     * @return {@code SortedSet<Integer>}
     */
    public static SortedSet<Integer> getRandomAvailableUdpPorts(int maxNum, int minPort, int maxPort) {
        return SocketType.Udp.getAvailablePorts(maxNum, minPort, maxPort);
    }

    /**
     * 枚举也可以实现模版方法设计模式(Template Method Design Pattern)
     */
    public static enum SocketType {
        Tcp {
            @Override
            protected boolean isPortAvailable(int port) {
                try (ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1,
                        InetAddress.getLocalHost())) {
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        },
        Udp {
            @Override
            protected boolean isPortAvailable(int port) {
                try (DatagramSocket datagramSocket = new DatagramSocket(port, InetAddress.getLocalHost())) {
                    return true;
                } catch (SocketException | UnknownHostException e) {
                    return false;
                }
            }
        };

        /**
         * 判断此端口可用否
         * 
         * @param port
         * @return {@code boolean}
         */
        protected abstract boolean isPortAvailable(int port);

        /**
         * 随机生成一个范围内的端口
         * 
         * @param minPort
         * @param maxPort
         * @return {@code int}
         */
        int getRandomPort(int minPort, int maxPort) {
            if (minPort < 0 || maxPort < 0 || minPort > maxPort) {
                throw new IllegalArgumentException("端口范围不合法");
            }
            return ThreadLocalRandom.current().nextInt(minPort, maxPort + 1);
        }

        /**
         * 随机获取一个范围内可用的端口
         * 
         * @param minPort
         * @param maxPort
         * @return {@code int}
         */
        int getRandomAvailablePort(int minPort, int maxPort) {
            if (minPort < 0 || maxPort < 0 || minPort > maxPort) {
                throw new IllegalArgumentException("端口范围不合法");
            }

            for (int n = maxPort - minPort + 100; n > 0; n--) {
                int randomPort = getRandomPort(minPort, maxPort);
                boolean portAvailable = isPortAvailable(randomPort);
                if (portAvailable) {
                    return randomPort;
                }
            }
            return -1;
        }

        SortedSet<Integer> getAvailablePorts(int maxNum, int minPort, int maxPort) {
            if (minPort < 0 || maxPort < 0 || minPort > maxPort || maxPort - minPort < maxNum) {
                throw new IllegalArgumentException("端口范围不合法");
            }
            if (maxNum < 0) {
                throw new IllegalArgumentException("端口数目不合法");
            }
            SortedSet<Integer> sortedSet = new TreeSet<>();
            for (int n = maxNum + 100; (sortedSet.size() < maxNum) && (n > 0); n--) {
                int randomAvailablePort = getRandomAvailablePort(minPort, maxPort);
                if (randomAvailablePort != -1) {
                    sortedSet.add(randomAvailablePort);
                }
            }

            return sortedSet;
        }
    }
}
