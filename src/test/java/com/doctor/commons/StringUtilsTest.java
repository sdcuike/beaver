package com.doctor.commons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月21日
 *         </p>
 */

public class StringUtilsTest {

    @Test
    public void test_isBlank_param_null() {
        boolean b = StringUtils.isBlank(null);
        assertTrue(b);
    }

    @Test
    public void test_isBlank_param_empty() {
        boolean b = StringUtils.isBlank("");
        assertTrue(b);
        b = StringUtils.isBlank("  ");
        assertTrue(b);
    }

    @Test
    public void test_isBlank_param() {
        boolean b = StringUtils.isBlank("sd");
        assertFalse(b);
        b = StringUtils.isBlank(" s  d ");
        assertFalse(b);
    }

    @Test
    public void test_leftPad_num_0() {
        String str = "";
        String padStr = "00";
        String leftPad = StringUtils.leftPad(str, 0, padStr);
        Assert.assertThat(leftPad.length(), IsEqual.equalTo(str.length()));

        leftPad = StringUtils.leftPad(str, -1, padStr);
        Assert.assertThat(leftPad.length(), IsEqual.equalTo(str.length()));
    }

    @Test
    public void test_leftPad_() {
        String str = "";
        String padStr = "00";
        int num = 1;
        String leftPad = StringUtils.leftPad(str, 1, padStr);
        Assert.assertThat(leftPad.length(), IsEqual.equalTo(str.length() + num * padStr.length()));
        Assert.assertThat(leftPad, IsEqual.equalTo(padStr + str));

        leftPad = StringUtils.leftPad(str, -1, padStr);
        Assert.assertThat(leftPad.length(), IsEqual.equalTo(str.length()));
    }
}
