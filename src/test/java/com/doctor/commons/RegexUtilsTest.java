package com.doctor.commons;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilsTest {

    @Test
    public void test_Patter_Digit_6_Same() {
        String input = "111111";
        boolean b = RegexUtils.isSameDigit6(input);
        Assert.assertTrue(b);

        input = "111112";
        b = RegexUtils.isSameDigit6(input);
        Assert.assertFalse(b);

        input = "aaaaaa";
        b = RegexUtils.isSameDigit6(input);
        Assert.assertFalse(b);
    }

    @Test
    public void test_isValidEmail() {
        String email = "";
        boolean b = RegexUtils.isValidEmail(email);
        Assert.assertFalse(b);

        email = "sf.jdfk@zhong";
        b = RegexUtils.isValidEmail(email);
        Assert.assertFalse(b);

        email = "sf.jdfk@zhong.com";
        b = RegexUtils.isValidEmail(email);
        Assert.assertTrue(b);
    }
}
