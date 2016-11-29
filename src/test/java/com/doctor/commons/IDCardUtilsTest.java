package com.doctor.commons;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Test
    public void test() {
        Set<Entry<String, String>> entrySet = IDCardUtils.AreCodeUtil.entrySet();
        ArrayList<Entry<String, String>> list = new ArrayList<>(entrySet);
        Collections.sort(list, new Comparator<Entry<String, String>>() {
            public int compare(Entry<String, String> a, Entry<String, String> b) {
                return a.getKey().compareTo(b.getKey());
            }
        });
        for (Entry<String, String> entry : list) {
            if (entry.getKey().length() == 2) {
                System.out.println(entry.getKey() + "==" + entry.getValue());
            }

        }
    }

    /**
     * 生成省、市map结构
     * 
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void test_AreCodeUtil_formatFromAreCodeSource() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> formatFromAreCodeSource = IDCardUtils.AreCodeUtil.formatFromAreCodeSource();
        String string = objectMapper.writeValueAsString(formatFromAreCodeSource);
        System.out.println(string);

        Map<String, String> ProvinceCode = new LinkedHashMap<>();
        Map<String, String> cityCode = new LinkedHashMap<>();
        for (Entry<String, String> e : formatFromAreCodeSource.entrySet()) {
            String key = e.getKey();
            if (key.length() == 2) {
                ProvinceCode.put(key, e.getValue());
            } else if (key.length() == 4) {
                cityCode.put(key, e.getValue());
            }
        }

        System.out.println("ProvinceCode:" + ProvinceCode);
        System.out.println("cityCode:" + cityCode);
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> e : cityCode.entrySet()) {
            // CityCode.put("11", "北京市");
            sb.append("CityCode.put(\"").append(e.getKey()).append("\"").append(",");
            sb.append("\"").append(e.getValue()).append("\");");
        }
        //复制下面的输出到代码出格式化即可
        System.out.println(sb);
    }

    @Test
    public void test_isValidIdCard15_true() {
        String idCard15 = "110105710923582";
        boolean b = IDCardUtils.isValidIdCard15(idCard15);
        Assert.assertThat(b, IsEqual.equalTo(true));
    }

    @Test
    public void test_isValidIdCard15_param_PatternForIdCard15() {
        String idCard15 = "11010571092358X";
        boolean b = IDCardUtils.isValidIdCard15(idCard15);
        Assert.assertThat(b, IsEqual.equalTo(false));
    }

    @Test
    public void test_isValidIdCard15_param_date_0() {
        String idCard15 = "110105710933582";
        boolean b = IDCardUtils.isValidIdCard15(idCard15);
        Assert.assertThat(b, IsEqual.equalTo(false));
    }

    @Test
    public void test_isValidIdCard15_param_date_1() {
        String idCard15 = "110105710230582";
        boolean b = IDCardUtils.isValidIdCard15(idCard15);
        Assert.assertThat(b, IsEqual.equalTo(false));
    }

    @Test
    public void test_isValidIdCard18_true() {
        String idCard18 = "110105197109235829";
        boolean b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "23213119830817173X";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "310102198312252934";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "341621198808284713";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "331021198703270628";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "37078119790127719x";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(false));

        idCard18 = "410526198809018242";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "331003199504222393";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

    }

    @Test
    public void test_isValidIdCard18_PatternForIdCard18_true() {
        String idCard18 = "512501197203035172";
        boolean b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));

        idCard18 = "51081119840301735X";
        b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(true));
    }

    @Test
    public void test_isValidIdCard18_param_date_false() {
        String idCard18 = "44011119880230214X";
        boolean b = IDCardUtils.isValidIdCard18(idCard18);
        Assert.assertThat(b, IsEqual.equalTo(false));
    }

    @Test
    public void test_getGender_() {
        String idCard18 = "44011119880230214X";
        System.out.println(idCard18.length());
        String gender = IDCardUtils.getGender(idCard18);
        Assert.assertThat(gender, IsEqual.equalTo("F"));
    }
}
