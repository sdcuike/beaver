package com.doctor.commons.core.reflection.property;

import java.util.Locale;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * 类的属性工具类
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月24日
 *         <p>
 */
@ThreadSafe
public final class PropertyNamerUtils {

    /**
     * 通过类的Accessor Methods 或Mutator Methods 的名字获取对应属性名
     * 
     * @param accessorsOrMutatorsName
     * @return
     * @throws ReflectiveOperationException
     */
    public static String accessorOrMutatorNameToPropertyName(String accessorsOrMutatorsName) throws ReflectiveOperationException {
        if (accessorsOrMutatorsName.startsWith("is")) {
            accessorsOrMutatorsName = accessorsOrMutatorsName.substring(2);
        } else if (accessorsOrMutatorsName.startsWith("get") || accessorsOrMutatorsName.startsWith("set")) {
            accessorsOrMutatorsName = accessorsOrMutatorsName.substring(3);
        } else {
            throw new ReflectiveOperationException("Error parsing property name '" + accessorsOrMutatorsName + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        if (accessorsOrMutatorsName.length() == 1 || (accessorsOrMutatorsName.length() > 1 && !Character.isUpperCase(accessorsOrMutatorsName.charAt(1)))) {
            accessorsOrMutatorsName = accessorsOrMutatorsName.substring(0, 1).toLowerCase(Locale.ENGLISH) + accessorsOrMutatorsName.substring(1);
        }

        return accessorsOrMutatorsName;
    }

    public static boolean isProperty(String methodName) {
        return methodName.startsWith("get") || methodName.startsWith("set") || methodName.startsWith("is");
    }

    public static boolean isGetter(String methodName) {
        return methodName.startsWith("get") || methodName.startsWith("is");
    }

    public static boolean isSetter(String methodName) {
        return methodName.startsWith("set");
    }

    /**
     * 构造函数私有并反射调用抛出异常
     */
    private PropertyNamerUtils() {
        throw new UnsupportedOperationException();
    }

}
