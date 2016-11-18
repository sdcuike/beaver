package com.doctor.commons.core.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.doctor.commons.ReflectionUtils;

public class MethodInvoker implements Invoker {
    private final Method method;

    public MethodInvoker(final Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Object obj, Object... args) throws IllegalAccessException, InvocationTargetException {
        ReflectionUtils.makeAccessible(method);
        return method.invoke(obj, args);
    }

    @Override
    public Class<?> getType() {
        if (method.getParameterTypes().length == 1) {
            return method.getParameterTypes()[0];
        }
        return method.getReturnType();
    }
}
