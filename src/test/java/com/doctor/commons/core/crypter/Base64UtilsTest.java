package com.doctor.commons.core.crypter;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.doctor.commons.core.crypter.Base64Utils;

public class Base64UtilsTest {
    String str = "http://mvnrepository.com/artifact/commons-codec/commons-codec/1.10";

    @Test
    public void testEncodeToStringString() {

        String encodeToString = Base64Utils.encodeToString(str);
        String encodeBase64String = Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodeToString);
        System.out.println(encodeBase64String);
        Assert.assertThat(encodeToString, IsEqual.equalTo(encodeBase64String));
    }

    @Test
    public void testDecodeToStringString() {
        String strs = "aHR0cDovL212bnJlcG9zaXRvcnkuY29tL2FydGlmYWN0L2NvbW1vbnMtY29kZWMvY29tbW9ucy1jb2RlYy8xLjEw";
        String dString = Base64Utils.decodeToString(strs);
        Assert.assertThat(dString, IsEqual.equalTo(str));

    }

}
