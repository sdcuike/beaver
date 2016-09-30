package com.doctor.commons;

/**
 * 字符串表示的版本比较工具类 <br>
 * 定三位，参见苹果自身产品。<br>
 * 版本号范围通常定为1.0.0-9.9.9，<br>
 * 小更新第三位+1，大更新第二位+1。 通常第一位变化为一年一变，是特大变化时才改动。
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月29日
 *         <p>
 */
public final class VersionStringUtils {

    /**
     * 比较版本号形式字符串
     * 
     * @param versionStrX the first {@code String} to compare
     * @param versionStrY the second {@code String} to compare
     * @return the value {@code 0} if {@code versionStrX == versionStrY}; a
     *         value less than {@code 0} if {@code versionStrX < versionStrY};
     *         and a value greater than {@code 0} if
     *         {@code versionStrX > versionStrY}
     */
    public static int compare(String versionStrX, String versionStrY) {
        String[] versionX = versionStrX.split("\\.");
        String[] versionY = versionStrY.split("\\.");
        int lengthX = versionX.length;
        int lengthY = versionY.length;
        if (lengthX != lengthY) {
            throw new IllegalArgumentException("版本字符串格式不合法");
        }

        int i = 0;
        while (i < lengthX && versionX[i].equals(versionY[i])) {
            i++;
        }

        if (i < lengthX) {
            return Integer.compare(Integer.parseInt(versionX[i]), Integer.parseInt(versionY[i]));
        }
        return 0;
    }
}
