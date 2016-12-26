package com.doctor.commons;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016.12.26<br/>
 *         <p>
 *         1.银行卡号由以下三部分组成：发卡行标识代码（BIN号）、发卡行自定义位、校验码。<br>
 *         2.银行卡号的前6位是BIN号，是bank identification
 *         number的缩写，由国际标准化组织（ISO）分配给各从事跨行转接交换的银行卡组织。
 *         3.一般地：4字头——VISA；5字头——Mastercard；62——中国银联；3字头——运通、JCB等。<br>
 *         4.发卡行自定义位长度可以是6-12位，一般可能会包含银行内部分行标识，比如第6-7位，01代表北京地区的卡，其余的就是随即生成了。
 *         <br>
 *         5.最后卡号最末位的校验码，具有一定的计算规则，用于防伪。银联标准卡符合中国银联标准，使用中国银联分配的BIN码(目前为622126-
 *         622925，共800个)，可以在中国银联支付网络范围和协议网络下使用。<br>
 */
@ThreadSafe
public final class BankCardUtils {

    /**
     * 银行卡卡号校验
     * <p>
     * Luhn算法被用于最后一位为校验码的一串数字的校验，通过如下规则计算校验码的正确性：<br>
     * 按照从右往左的顺序，从这串数字的右边开始，包含校验码，将偶数位数字乘以2，如果每次乘二操作的结果大于9（如 8 × 2 =
     * 16），然后计算个位和十位数字的和（如 1 ＋ 6 = 7）或者用这个结果减去9（如 16 - 9 ＝ 7）;<br>
     * 第一步操作过后会得到新的一串数字，计算所有数字的和（包含校验码）;<br>
     * 用第二步操作得到的和进行“模10”运算，如果结果位0，表示校验通过，否则失败。 <br>
     * <p>
     * 参考:
     * <a href="https://zh.wikipedia.org/wiki/Luhn%E7%AE%97%E6%B3%95">Luhn算法</a>
     * 
     * @param cardNo
     * @return {@code true } 银行卡号合法
     */
    public static boolean isValidBankCardNo(final String cardNo) {
        int[] cardNoArr = new int[cardNo.length()];

        for (int i = 0; i < cardNo.length(); i++) {
            cardNoArr[i] = cardNo.charAt(i) - '0';
        }

        for (int i = cardNoArr.length - 2; i >= 0; i -= 2) {
            cardNoArr[i] <<= 1;
            cardNoArr[i] = cardNoArr[i] / 10 + cardNoArr[i] % 10;
        }

        int sum = 0;
        for (int i = 0; i < cardNoArr.length; i++) {
            sum += cardNoArr[i];
        }

        return sum % 10 == 0;
    }

}
