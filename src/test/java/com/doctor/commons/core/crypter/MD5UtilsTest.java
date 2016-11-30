package com.doctor.commons.core.crypter;

import java.security.NoSuchAlgorithmException;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.doctor.commons.core.crypter.MD5Utils;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月21日
 *         </p>
 * @see http://md5jiami.51240.com/
 */
public class MD5UtilsTest {

    private String str   = "java的md5的加密算法代码_百度知 道";
    private String hex32 = "4ceb4cccc14be77751f8c07784545dcb";
    private String hex16 = "c14be77751f8c077";

    @Test
    public void test_md5To32LowerCaseHexString() throws NoSuchAlgorithmException {
        String md5To32LowerCaseHexString = MD5Utils.md5To32LowerCaseHexString(null);
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(null));
        md5To32LowerCaseHexString = MD5Utils.md5To32LowerCaseHexString("");
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(""));

        md5To32LowerCaseHexString = MD5Utils.md5To32LowerCaseHexString(str);
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(hex32));
    }

    @Test
    public void test_md5To16LowerCaseHexString() throws NoSuchAlgorithmException {
        String md5To32LowerCaseHexString = MD5Utils.md5To16LowerCaseHexString(null);
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(null));
        md5To32LowerCaseHexString = MD5Utils.md5To16LowerCaseHexString("");
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(""));

        md5To32LowerCaseHexString = MD5Utils.md5To16LowerCaseHexString(str);
        Assert.assertThat(md5To32LowerCaseHexString, IsEqual.equalTo(hex16));
    }

}
