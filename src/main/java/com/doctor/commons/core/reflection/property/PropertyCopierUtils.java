package com.doctor.commons.core.reflection.property;

import java.lang.reflect.Field;

import com.doctor.commons.ReflectionUtils;

/**
 * 属性copy
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016.11.16
 *         <p>
 */
public final class PropertyCopierUtils {

    public static void copyBeanProperty(final Class<?> type, Object sourceBean, Object destinationBean) {
        Class<?> classT = type;
        try {
            while (classT != null) {
                final Field[] declaredFields = type.getDeclaredFields();
                for (Field field : declaredFields) {
                    ReflectionUtils.makeAccessible(field);
                    field.set(destinationBean, field.get(sourceBean));
                }

                classT = classT.getSuperclass();
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private PropertyCopierUtils() {
        throw new UnsupportedOperationException();
    }

}
