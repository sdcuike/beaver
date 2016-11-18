package com.doctor.commons.core.reflection.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.doctor.commons.ReflectionUtils;

public class GetFieldInvoker implements Invoker {
    private final Field field;

    public GetFieldInvoker(final Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object obj, Object... args) throws IllegalAccessException, InvocationTargetException {
        ReflectionUtils.makeAccessible(field);
        return field.get(obj);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

}
