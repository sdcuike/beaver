package com.doctor.commons;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class IDCardUtilsTest {

    @Test
    public void testTransformIdCard15to18() {
        String idCard15 = "110105710923582";
        String idCard18 = "110105197109235829";
        String transformIdCard15to18 = IDCardUtils.transformIdCard15to18(idCard15);
        Assert.assertThat(transformIdCard15to18, IsEqual.equalTo(idCard18));

        idCard15 = "522634520829128";
        idCard18 = "522634195208291285";
        transformIdCard15to18 = IDCardUtils.transformIdCard15to18(idCard15);
        Assert.assertThat(transformIdCard15to18, IsEqual.equalTo(idCard18));
    }

}
