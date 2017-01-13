package com.doctor.commons;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.ReflectPermission;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.doctor.beaver.annotation.ThreadSafe;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月9日<br/>
 */
@ThreadSafe
public final class ReflectionUtils {
    private static final Map<Class<?>, Set<Field>>           DeclaredFields_Cache       = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Set<Method>>          DeclaredMethods_Cache      = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Constructor<?>>       Constructor_Default        = new ConcurrentHashMap<>();
    private static final Map<String, Constructor<?>>         Constructor_               = new ConcurrentHashMap<>();
    private static final Map<String, Set<Method>>            methodCache                = new ConcurrentHashMap<>();
    private static final Map<Method, List<Class<?>>>         parameterTypeCache         = new ConcurrentHashMap<>();
    private static final Map<Method, List<Annotation>>       methodAnnotationCache      = new ConcurrentHashMap<>();
    private static final Map<Method, List<List<Annotation>>> methodParamAnnotationCache = new ConcurrentHashMap<>();

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
     * @return {@code Set[]} 缓存的Field数组,无元素空数组
     */
    public static Set<Field> getDeclaredFields(Class<?> clazz) {
        Set<Field> fields = DeclaredFields_Cache.get(clazz);

        if (fields == null) {
            List<Field> list = Arrays.asList(clazz.getDeclaredFields());
            fields = Collections.unmodifiableSet(new HashSet<>(list));
            DeclaredFields_Cache.put(clazz, fields);
        }

        return fields;
    }

    /**
     * 为了避免底层方法安全检查和数组copy，缓存一份,提供性能;java8的default方法不支持
     * 
     * @param clazz
     * @return {@code Set<Method>} 缓存的Method数组，无元素空数组
     */
    public static Set<Method> getDeclaredMethods(Class<?> clazz) {
        Set<Method> methods = DeclaredMethods_Cache.get(clazz);
        if (methods == null) {
            List<Method> list = Arrays.asList(clazz.getDeclaredMethods());
            DeclaredMethods_Cache.put(clazz, Collections.unmodifiableSet(new HashSet<>(list)));
        }

        return methods;
    }

    public static void makeAccessible(Field field) {

        if (canAccessPrivateMethods()) {
            if ((!Modifier.isPublic(field.getModifiers()) ||
                    !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                    Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
                field.setAccessible(true);
            }
        }
    }

    public static void makeAccessible(Method method) {

        if (canAccessPrivateMethods()) {
            if ((!Modifier.isPublic(method.getModifiers()) ||
                    !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
                method.setAccessible(true);
            }
        }
    }

    public static void makeAccessible(Constructor<?> constructor) {
        if (canAccessPrivateMethods()) {
            if ((!Modifier.isPublic(constructor.getModifiers()) ||
                    !Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) && !constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
        }
    }

    /**
     * 检查反射配置是
     * 
     * @return
     */
    public static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            //SecurityException 异常表明: - if access is not permitted based on the current security policy.
            return false;
        }

        return true;
    }

    public static String getMethodSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        if (method.getReturnType() != null) {
            sb.append(method.getReturnType().getName()).append("#");
        }

        sb.append(method.getName());

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes != null) {
            for (int i = 0, length = parameterTypes.length; i < length; i++) {
                if (i == 0) {
                    sb.append(":");
                } else {
                    sb.append(",");
                }
                sb.append(parameterTypes[i].getName());
            }
        }
        return sb.toString();
    }

    public static Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
            result = (Class<?>) ((ParameterizedType) src).getRawType();
        } else if (src instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance((Class<?>) componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }

    /**
     * 根据方法名寻找类的所有方法
     * 
     * @param classes
     * @param name
     * @return {@code Set<Method>}
     */
    public static Set<Method> findCandidateMethods(Class<?> clazz, String name) {
        String cacheKey = clazz.getName() + "::" + name;

        if (methodCache.containsKey(cacheKey)) {
            return methodCache.get(cacheKey);
        }

        Set<Method> methods = new HashSet<>();
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name)) {
                methods.add(method);
            }
        }

        methods = Collections.unmodifiableSet(methods);
        methodCache.put(cacheKey, methods);

        return methods;
    }

    /**
     * @param method
     * @return {@code List<Class<?>>}
     */
    public static List<Class<?>> getParameterTypes(Method method) {
        if (parameterTypeCache.containsKey(method)) {
            return parameterTypeCache.get(method);
        }

        List<Class<?>> types = Arrays.asList(method.getParameterTypes());
        parameterTypeCache.put(method, types);

        return types;
    }

    public static <T extends Annotation> List<T> getAnnotations(Method method, Class<T> type) {
        return filterAnnotations(getAnnotations(method), type);
    }

    public static <T extends Annotation> List<T> filterAnnotations(Collection<Annotation> annotations, Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Annotation annotation : annotations) {
            if (type.isInstance(annotation)) {
                result.add(type.cast(annotation));
            }
        }

        return result;
    }

    public static List<Annotation> getAnnotations(Method method) {
        if (methodAnnotationCache.containsKey(method)) {
            return methodAnnotationCache.get(method);
        }

        List<Annotation> annotations = Collections.unmodifiableList(Arrays.asList(method.getAnnotations()));
        methodAnnotationCache.put(method, annotations);

        return annotations;
    }

    /**
     * Returns the parameter {@link Annotation}s of the given type for the given
     * {@link Method}.
     * 
     * @param <T> the {@link Annotation} type
     * @param type the type
     * @param method the {@link Method}
     * @return the {@link Annotation}s
     */
    public static <T extends Annotation> List<List<T>> getParameterAnnotations(Method method, Class<T> type) {
        List<List<T>> annotations = new ArrayList<>();

        for (List<Annotation> paramAnnotations : getParameterAnnotations(method)) {
            annotations.add(filterAnnotations(paramAnnotations, type));
        }

        return annotations;
    }

    /**
     * Returns the parameter {@link Annotation}s for the given {@link Method}.
     * 
     * @param method the {@link Method}
     * @return the {@link Annotation}s
     */
    public static List<List<Annotation>> getParameterAnnotations(Method method) {
        if (methodParamAnnotationCache.containsKey(method)) {
            return methodParamAnnotationCache.get(method);
        }

        List<List<Annotation>> annotations = new ArrayList<>();
        for (Annotation[] paramAnnotations : method.getParameterAnnotations()) {
            List<Annotation> listAnnotations = new ArrayList<>();
            Collections.addAll(listAnnotations, paramAnnotations);
            annotations.add(listAnnotations);
        }

        annotations = Collections.unmodifiableList(annotations);
        methodParamAnnotationCache.put(method, annotations);
        return annotations;
    }

    public static void clearCache() {
        DeclaredFields_Cache.clear();
        DeclaredMethods_Cache.clear();
        Constructor_Default.clear();
        Constructor_.clear();
        methodCache.clear();
        parameterTypeCache.clear();
        methodAnnotationCache.clear();
        methodParamAnnotationCache.clear();
    }
}
