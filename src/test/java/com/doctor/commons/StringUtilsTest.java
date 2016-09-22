package com.doctor.commons;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

}
