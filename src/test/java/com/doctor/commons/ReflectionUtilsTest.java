package com.doctor.commons;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReflectionUtilsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_newInstance_默认构造函数调用() throws Throwable {
        @SuppressWarnings("unchecked")
        Map<String, String> map = ReflectionUtils.newInstance(HashMap.class);
        Assert.assertTrue(map.isEmpty());
        String string = ReflectionUtils.newInstance(String.class);
        Assert.assertThat(string, IsEqual.equalTo(""));

    }

    @Test
    public void test_newInstance_私有默认构造函数调用() throws Throwable {
        expectedException.expect(IllegalAccessException.class);
        ReflectionUtils.newInstance(TestClass.class, false);
    }

    @Test
    public void test_newInstance_私有默认构造函数调用_true() throws Throwable {
        ReflectionUtils.newInstance(TestClass.class);
        ReflectionUtils.newInstance(TestClass.class);
    }

    @Test
    public void test_newInstance_带参构造函数调用() throws Throwable {
        Integer integer = ReflectionUtils.newInstance(Integer.class, new Class<?>[] { int.class },
                new Object[] { 2 });
        Assert.assertThat(integer, IsEqual.equalTo(Integer.valueOf(2)));
        integer = ReflectionUtils.newInstance(Integer.class, new Class<?>[] { int.class },
                new Object[] { 2 });
        Assert.assertThat(integer, IsEqual.equalTo(Integer.valueOf(2)));

        TestClass testClass = ReflectionUtils.newInstance(TestClass.class, new Class<?>[] { int.class }, new Object[] {
                3 });
        Assert.assertThat(testClass.getIndex(), IsEqual.equalTo(3));
    }

    private static class TestClass {
        private int index = -1;

        private TestClass() {
        }

        public TestClass(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
