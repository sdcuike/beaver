package com.doctor.commons;

import org.junit.Test;

public class RandomStringUtilsTest {

    @Test
    public void testRandom_() {
        int n = 10;
        String str = "testRandom1234567890";
        while (n > 0) {
            System.out.println(RandomStringUtils.random(n, str));
            n--;
        }
    }

    @Test
    public void testRandom_randomAlpha() {
        int n = 10;
        while (n > 0) {
            System.out.println(RandomStringUtils.randomAlpha(n));
            n--;
        }
    }

    @Test
    public void testRandom_randomAlphaNumeric() {
        int n = 10;
        while (n > 0) {
            System.out.println(RandomStringUtils.randomAlphaNumeric(n));
            n--;
        }
    }

    @Test
    public void testRandom_randomLowerCaseLetters() {
        int n = 10;
        while (n > 0) {
            System.out.println(RandomStringUtils.randomLowerCaseLetters(n));
            n--;
        }
    }

    @Test
    public void testRandom_randomUpperCaseLetters() {
        int n = 10;
        while (n > 0) {
            System.out.println(RandomStringUtils.randomUpperCaseLetters(n));
            n--;
        }
    }

}
