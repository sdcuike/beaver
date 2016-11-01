package com.doctor.commons;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class CollectionUtilsTest {

    @Test
    public void test_isEmpty_true() {
        Collection<?> collection = null;
        Assert.assertTrue(CollectionUtils.isEmpty(collection));
        Assert.assertTrue(CollectionUtils.isEmpty(Arrays.asList()));
    }

    @Test
    public void test_isEmpty_false() {
        Assert.assertFalse(CollectionUtils.isEmpty(Arrays.asList("s")));
    }

}
