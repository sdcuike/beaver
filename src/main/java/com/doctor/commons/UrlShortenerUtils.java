package com.doctor.commons;

import java.security.NoSuchAlgorithmException;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * 短网址生成算法
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月28日
 *         <p>
 *         短址的长度一般设为 6 位，而每一位是由 [a - z, A - Z, 0 - 9] 总共 62 个字母组成的，所以6位的话，总共会有
 *         62^6 ~= 568亿种组合，基本上够用了<br>
 *         <p>
 *         本类提供两种算法：<br>
 *         1.基于自增序列 <br>
 *         2.基于MD5编码
 */
@ThreadSafe
public final class UrlShortenerUtils {
    private static final int Base = StringConstants.Alpha_Numeric.length();

    /**
     * 基于自增序列的短网址生成
     * <p>
     * <br>
     * 62进制算法思想<br>
     * <p>
     * Theoretical background <br>
     * You need a Bijective Function f. This is necessary so that you can find a
     * inverse function g('abc') = 123 for your f(123) = 'abc' function. This
     * means: There must be no x1, x2 (with x1 ≠ x2) that will make f(x1) =
     * f(x2), and for every y you must be able to find an x so that f(x) = y.
     * <br>
     * <p>
     * How to convert the ID to a shortened URL<br>
     * 1.Think of an alphabet we want to use. In your case that's [a-zA-Z0-9].
     * It contains 62 letters. <br>
     * 2.Take an auto-generated, unique numerical key (the auto-incremented id
     * of a MySQL table for example). For this example I will use 12510 (125
     * with a base of 10). Now you have to convert 12510 to X62 (base 62). 12510
     * = 2×621 + 1×620 = [2,1] 3.Now map the indices 2 and 1 to your alphabet.
     * This is how your mapping (with an array for example) could look like:
     * <br>
     * 0 → a<br>
     * 1 → b<br>
     * ...<br>
     * 25 → z<br>
     * ...<br>
     * 52 → 0<br>
     * 61 → 9<br>
     * With 2 → c and 1 → b you will receive cb62 as the shortened URL.
     * http://shor.ty/cb <br>
     * Refer:
     * <p>
     * {@link http://stackoverflow.com/questions/742013/how-to-code-a-url-shortener}
     * {@link http://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/}
     * {@link http://hashids.org/java/}
     * {@link http://carnage.github.io/2015/08/cryptanalysis-of-hashids}
     * {@link http://coddicted.com/design-a-tiny-url-or-url-shortener/}
     * 
     * @param longUrlId id,可以用数据库或分布式ID自增算法获取，该id是长url的主键映射
     * @return {@code String}
     */
    public static String encode2Base62ShorUrl(long longUrlId) {
        if (longUrlId == 0) {
            return StringConstants.Alpha_Numeric.substring(0, 1);
        }

        StringBuilder sb = new StringBuilder(6);
        while (longUrlId > 0) {
            char str = StringConstants.Alpha_Numeric.charAt((int) (longUrlId % Base));
            sb.append(str);
            longUrlId /= Base;
        }
        return sb.reverse().toString();
    }

    /**
     * {@link #encode2Base62ShorUrl(long)}的逆操作
     * 
     * @param shorUrl
     * @return {@code Long}
     */
    public static long decodeBase62ShorUrl2LongUrlId(String base62ShorUrl) {
        long sum = 0;
        for (int length = base62ShorUrl.length(), i = length - 1; i >= 0; i--) {
            int index = StringConstants.Alpha_Numeric.indexOf(base62ShorUrl.substring(i, i + 1));
            sum += index * Math.pow(62, length - i - 1);
        }
        return sum;
    }

    /**
     * 基于MD5编码短网址生成(碰撞概率比较小)
     * <p>
     * a.计算长地址的MD5码，将32位的MD5码分成4段，每段8个字符 (会得到4个短码，每个短码由以下处理这8个字符得到) <br>
     * b.将a得到的8个字符串看成一个16进制的数，与N=6 * 6个1表示的二进制数进行&操作 得到一个N=6 * 6长的二进制数 <br>
     * c.将b得到的数分成N=6段，每段6位，然后将这N=6个6位数分别与61进行&操作，将得到的
     * 数作为INDEX去字母表取相应的字母或数字，拼接就是一个长度为6的短网址。<br>
     * <p>
     * 是不是作为最终的短码，还要可能去数据库查询以下短码是否存在。
     * 
     * @param url
     * @return {@code String[]} 4个短码,取其一即可<br>
     * @throws NoSuchAlgorithmException
     */
    public static String[] shortUrl(String url) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("url is blank");
        }
        String[] shorUrls = new String[4];
        char[] shorUrl = new char[6];
        String md5Str = MD5Utils.md5To32LowerCaseHexString(url);
        for (int i = 0; i < 4; i++) {
            String str8 = md5Str.substring(i * 8, (i + 1) * 8);
            long result = Long.valueOf(str8, 16) & Long.valueOf("111111111111111111111111111111111111", 2);
            String binaryStr = Long.toBinaryString(result);
            binaryStr = StringUtils.leftPad(binaryStr, 36 - binaryStr.length(), "0");
            for (int j = 0; j < 6; j++) {
                String str6 = binaryStr.substring(j * 6, (j + 1) * 6);
                int index = Integer.valueOf(str6, 2) & 61;
                shorUrl[j] = StringConstants.Alpha_Numeric.charAt(index);
            }

            shorUrls[i] = String.valueOf(shorUrl);
        }

        return shorUrls;
    }
}
