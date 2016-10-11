package com.doctor.commons;

import java.lang.reflect.Method;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月10日<br/>
 */
@ThreadSafe
public class MethodUtils {

    public static boolean isEqualsMethod(Method method) {
        return "equals".equals(method.getName()) && 1 == method.getParameterTypes().length && Object.class.equals(method.getParameterTypes()[0]);
    }

    public static boolean isHashCodeMethod(Method method) {
        return "hashCode".equals(method.getName()) && 0 == method.getParameterTypes().length;
    }

    public static boolean isToStringMethod(Method method) {
        return "toString".equals(method.getName()) && 0 == method.getParameterTypes().length;
    }

    public static boolean isCloneMethod(Method method) {
        return "clone".equals(method.getName()) && 0 == method.getParameterTypes().length;
    }

    public static boolean isFinalizeMethod(Method method) {
        return "finalize".equals(method.getName()) && 0 == method.getParameterTypes().length;
    }

}
