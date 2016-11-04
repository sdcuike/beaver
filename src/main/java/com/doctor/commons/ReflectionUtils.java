package com.doctor.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月9日<br/>
 */
@ThreadSafe
public final class ReflectionUtils {
    private static final ConcurrentHashMap<Class<?>, Field[]>        DeclaredFields_Cache  = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<Class<?>, Method[]>       DeclaredMethods_Cache = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<Class<?>, Constructor<?>> Constructor_Default   = new ConcurrentHashMap<>(256);
    private static final ConcurrentHashMap<String, Constructor<?>>   Constructor_          = new ConcurrentHashMap<>(256);

    public static <T> T newInstance(Class<?> clazz) throws ReflectiveOperationException {
        return newInstance(clazz, true, null, null);
    }

    public static <T> T newInstance(Class<?> clazz, boolean accessibleFlag) throws ReflectiveOperationException {
        return newInstance(clazz, accessibleFlag, null, null);
    }

    public static <T> T newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object[] parameterValues)
            throws ReflectiveOperationException {
        return newInstance(clazz, true, parameterTypes, parameterValues);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz, boolean accessibleFlag, Class<?>[] parameterTypes,
                                    Object[] parameterValues)
            throws ReflectiveOperationException {

        if (clazz.isInterface()) {
            throw new IllegalArgumentException("接口不可以实例化" + clazz);
        }
        //只有静态内部类才有权限被外部访问
        if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
            throw new IllegalArgumentException("只有静态内部类才有权限被外部访问" + clazz);
        }

        Constructor<?> constructor = null;
        boolean defaultConstructor = false;
        if (parameterTypes == null || parameterTypes.length == 0) {
            defaultConstructor = true;
            constructor = Constructor_Default.get(clazz);
            if (constructor == null) {
                constructor = clazz.getDeclaredConstructor();
                Constructor_Default.put(clazz, constructor);
            }

        } else {
            String key = clazz.toString() + Arrays.toString(parameterTypes);
            constructor = Constructor_.get(key);
            if (constructor == null) {
                constructor = clazz.getDeclaredConstructor(parameterTypes);
                Constructor_.put(key, constructor);
            }
        }

        if (accessibleFlag) {
            makeAccessible(constructor);
        }

        T instance = null;
        if (defaultConstructor) {
            instance = (T) constructor.newInstance();
        } else {
            instance = (T) constructor.newInstance(parameterValues);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field field, Object target)
            throws IllegalArgumentException, IllegalAccessException {
        makeAccessible(field);
        return (T) field.get(target);
    }

    public static void setFieldValue(Field field, Object target, Object value) throws IllegalArgumentException, IllegalAccessException {
        makeAccessible(field);
        field.set(target, value);
    }

    public static void invokeMethod(Object target, Method method, Object... args) throws ReflectiveOperationException {
        makeAccessible(method);
        method.invoke(target, args);
    }

    /**
     * 为了避免底层方法安全检查和数组copy，缓存一份,提供性能
     * 
     * @param clazz
     * @return {@code Field[]} 缓存的Field数组,无元素空数组
     */
    public static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] fields = DeclaredFields_Cache.get(clazz);
        if (fields == null) {
            fields = clazz.getDeclaredFields();
            DeclaredFields_Cache.put(clazz, fields);
        }

        return fields;
    }

    /**
     * 为了避免底层方法安全检查和数组copy，缓存一份,提供性能;java8的default方法不支持
     * 
     * @param clazz
     * @return {@code Method[]} 缓存的Method数组，无元素空数组
     */
    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] methods = DeclaredMethods_Cache.get(clazz);
        if (methods == null) {
            methods = clazz.getDeclaredMethods();
            DeclaredMethods_Cache.put(clazz, methods);
        }

        return methods;
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Constructor<?> constructor) {
        if ((!Modifier.isPublic(constructor.getModifiers()) ||
                !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) && !constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
    }

    public static void clearCache() {
        DeclaredFields_Cache.clear();
        DeclaredMethods_Cache.clear();
        Constructor_Default.clear();
    }
}
