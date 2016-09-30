package com.doctor.commons;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

public class VersionStringUtilsTest {

    @Test
    public void testCompare_equals0() {
        String versionStrX = "0.0.0";
        String versionStrY = "0.0.0";
        int compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertThat(compare, IsEqual.equalTo(0));
        versionStrX = "0.0.1";
        versionStrY = "0.0.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertThat(compare, IsEqual.equalTo(0));

        versionStrX = "0.1.1";
        versionStrY = "0.1.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertThat(compare, IsEqual.equalTo(0));
        versionStrX = "1000.1000.1";
        versionStrY = "1000.1000.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertThat(compare, IsEqual.equalTo(0));
    }

    @Test
    public void testCompare_less0() {
        String versionStrX = "0.0.0";
        String versionStrY = "0.0.1";
        int compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare < 0);

        versionStrX = "0.0.0";
        versionStrY = "1.1.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare < 0);

        versionStrX = "0.110.0";
        versionStrY = "1.1.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare < 0);

        versionStrX = "1.110.0";
        versionStrY = "10.1.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare < 0);
    }

    @Test
    public void testCompare_greater0() {
        String versionStrX = "0.0.1";
        String versionStrY = "0.0.0";
        int compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare > 0);

        versionStrX = "1.1.1";
        versionStrY = "1.1.0";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare > 0);

        versionStrX = "1.10.1";
        versionStrY = "1.1.1";
        compare = VersionStringUtils.compare(versionStrX, versionStrY);
        Assert.assertTrue(compare > 0);

    }

}
