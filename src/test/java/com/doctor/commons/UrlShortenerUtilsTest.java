package com.doctor.commons;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UrlShortenerUtilsTest {

    @Test
    @Ignore
    public void test() {
        ExecutorService service = Executors.newFixedThreadPool(100);
        for (long i = 0; i < Integer.MAX_VALUE; i++) {

            final long longUrlId = i;
            service.submit(new Runnable() {

                @Override
                public void run() {
                    String shorUrl = UrlShortenerUtils.encode2Base62ShorUrl(longUrlId);
                    long id = UrlShortenerUtils.decodeBase62ShorUrl2LongUrlId(shorUrl);
                    System.out.println(
                            "longUrlId:" + longUrlId + " shorUrl:" + shorUrl + " decodeBase62ShorUrl2LongUrlId:" + id);
                    Assert.assertThat(id, IsEqual.equalTo(longUrlId));

                }
            });

        }

        service.shutdown();
    }

    @Test
    public void test_int() {
        long longUrlId = Integer.MAX_VALUE;
        String shorUrl = UrlShortenerUtils.encode2Base62ShorUrl(longUrlId);

        long id = UrlShortenerUtils.decodeBase62ShorUrl2LongUrlId(shorUrl);
        System.out.println("longUrlId:" + longUrlId + " shorUrl:" + shorUrl + " decodeBase62ShorUrl2LongUrlId:" + id);
        Assert.assertThat(id, IsEqual.equalTo(longUrlId));

    }

    @Test
    public void test_long() {
        long longUrlId = Integer.MAX_VALUE + 1000L;
        String shorUrl = UrlShortenerUtils.encode2Base62ShorUrl(longUrlId);

        long id = UrlShortenerUtils.decodeBase62ShorUrl2LongUrlId(shorUrl);
        System.out.println("longUrlId:" + longUrlId + " shorUrl:" + shorUrl + " decodeBase62ShorUrl2LongUrlId:" + id);
        Assert.assertThat(id, IsEqual.equalTo(longUrlId));

    }

    @Test
    public void test_max_long() {
        long longUrlId = Long.MAX_VALUE;
        String shorUrl = UrlShortenerUtils.encode2Base62ShorUrl(longUrlId);
        long id = UrlShortenerUtils.decodeBase62ShorUrl2LongUrlId(shorUrl);
        System.out.println("longUrlId:" + longUrlId + " shorUrl:" + shorUrl + " decodeBase62ShorUrl2LongUrlId:" + id);
        Assert.assertThat(id, IsEqual.equalTo(longUrlId));
    }

    @Test
    public void test_shortUrl() throws NoSuchAlgorithmException {
        String url = "http://blog.csdn.net/doctor_who2004/article/details/52345501";
        String[] shortUrl = UrlShortenerUtils.shortUrl(url);
        System.out.println(Arrays.toString(shortUrl));
        Assert.assertThat(shortUrl.length, IsEqual.equalTo(4));
        for (String str : shortUrl) {
            Assert.assertThat(str.length(), IsEqual.equalTo(6));
        }
    }
}
