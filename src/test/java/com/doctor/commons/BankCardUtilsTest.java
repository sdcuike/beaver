package com.doctor.commons;

import org.junit.Assert;
import org.junit.Test;

public class BankCardUtilsTest {

    @Test
    public void testIsValidBankCardNo() {
        String cardNo = "6228480402564890018";
        boolean b = BankCardUtils.isValidBankCardNo(cardNo);
        Assert.assertTrue(b);
    }

}
