package com.doctor.commons.core;

import java.io.InputStream;
import java.util.Properties;

import com.doctor.beaver.annotation.ThreadSafe;
import com.doctor.commons.StringUtils;

/**
 * Properties辅助工具类
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月10日
 *         <p>
 */
@ThreadSafe
public class PropertiesHelper {
    private Properties properties;

    public PropertiesHelper(Properties properties) {
        this.properties = properties;
    }

    public PropertiesHelper(String classpathPropertiesFileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(classpathPropertiesFileName)) {
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String propertyName) {
        String value = properties.getProperty(propertyName);
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.trim();
    }

    public String getString(String propertyName, String defaultValue) {
        String vlaue = getString(propertyName);
        if (StringUtils.isBlank(vlaue)) {
            return defaultValue;
        }
        return vlaue;
    }

    public boolean getBoolean(String propertyName) {
        String value = properties.getProperty(propertyName);
        return Boolean.parseBoolean(value.trim());
    }

    public boolean getBoolean(String propertyName, String defaultValue) {
        String value = properties.getProperty(propertyName);
        if (StringUtils.isBlank(value.trim())) {
            return Boolean.parseBoolean(defaultValue);
        }
        return Boolean.parseBoolean(value.trim());
    }

    public long getLong(String propertyName) {
        String value = properties.getProperty(propertyName);
        return Long.valueOf(value.trim());
    }

    public long getLong(String propertyName, String defaultValue) {
        String value = properties.getProperty(propertyName);
        if (StringUtils.isBlank(value)) {
            return Long.valueOf(defaultValue);
        }
        return Long.valueOf(value.trim());
    }

    public int getInt(String propertyName) {
        String value = properties.getProperty(propertyName);
        return Integer.parseInt(value.trim());
    }

    public int getInt(String propertyName, String defaultValue) {
        String value = properties.getProperty(propertyName);
        if (StringUtils.isBlank(value)) {
            return Integer.parseInt(defaultValue);
        }
        return Integer.parseInt(value.trim());
    }
}
