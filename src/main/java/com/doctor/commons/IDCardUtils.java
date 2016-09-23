package com.doctor.commons;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月23日
 *         <p>
 *         中华人民共和国国家标准《公民身份号码》（GB11643-1999），居民身份证号码国家标准。
 *         中国国家质量技术监督局于1999年7月1日发布国家标准：GB11643-1999《公民身份号码》。主要内容如下：
 *         <p>
 *         一、范围 该标准规定了公民身份号码的编码对象、号码的结构和表现形式，使每个编码对象获得一个唯一的、不变的法定号码。
 *         <p>
 *         二、编码对象 公民身份号码的编码对象是具有中华人民共和国国籍的公民。
 *         <p>
 *         三、号码的结构和表示形式
 *         <p>
 *         1、号码的结构 <br>
 *         公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
 *         三位数字顺序码和一位数字校验码。 <br>
 *         2、地址码 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。<br>
 *         3、出生日期码 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。<br>
 *         4、顺序码 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。<br>
 *         5、校验码 根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2中的校验码计算方法计算确定。
 *         <p>
 *         四、较验码的计算 根据ISO 7064:1983.MOD 11-2，较验码的计算方法如下：<br>
 *         （1）十七位数字本体码加权求和公式 <br>
 *         S = Sum(Ai * Wi)； <br>
 *         i = 0, ... , 16 ，分别对应身份证的前17位数字； <br>
 *         Ai，表示第i位置上的身份证号码数字值； <br/>
 *         Wi，表示第i位置上的加权因子，分别是 7，9，10，5，8，4，2，1，6，3，7，9，10，5，8，4，2； <br>
 *         sum，求和。 <br>
 *         （2）计算模 <br>
 *         Y = mod(S, 11) <br>
 *         （3）通过模得到对应的校验码 <br>
 *         Y: 0 1 2 3 4 5 6 7 8 9 10 <br>
 *         校验码: 1 0 X 9 8 7 6 5 4 3 2
 */
public final class IDCardUtils {
    private static final int    IDCardLength15 = 15;
    private static final int    IDCardLength18 = 18;
    /**
     * 加权因子
     */
    private static final int[]  WI             = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
    /**
     * 对应的校验码
     */
    private static final char[] CheckBit       = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    /**
     * 15位有效身份证 转化为18位身份证<br>
     * <p>
     * 15位身份证号码各位的含义:<br>
     * 1-2位省、自治区、直辖市代码； <br>
     * 3-4位地级市、盟、自治州代码； <br>
     * 5-6位县、县级市、区代码； <br>
     * 7-12位出生年月日
     * <p>
     * 比如670401代表1967年4月1日,与18位的第一个区别； 13-15位为顺序号，其中15位男为单数，女为双数；<br>
     * 与18位身份证号的第二个区别：没有最后一位的验证码。
     * 
     * @param idCard15 15位有效身份证
     * @return {@code String} 18位身份证
     */
    public static String transformIdCard15to18(String idCard15) {
        if (StringUtils.isBlank(idCard15) || idCard15.length() != IDCardLength15) {
            throw new IllegalArgumentException("15位身份证不合法");
        }
        StringBuffer idCard18 = new StringBuffer(idCard15);
        idCard18.insert(6, "19");
        int sum = 0;
        for (int i = 0, length = idCard18.length(); i < length; i++) {
            sum += Character.getNumericValue(idCard18.charAt(i)) * WI[i];
        }
        char checkCode = CheckBit[sum % 11];
        idCard18.append(checkCode);
        return idCard18.toString();
    }

}
