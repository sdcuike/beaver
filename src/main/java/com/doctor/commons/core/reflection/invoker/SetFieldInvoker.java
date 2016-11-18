package com.doctor.commons.core.reflection.invoker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.doctor.commons.ReflectionUtils;

public class SetFieldInvoker implements Invoker {
    private final Field field;

    public SetFieldInvoker(final Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object obj, Object... args) throws IllegalAccessException, InvocationTargetException {
        ReflectionUtils.makeAccessible(field);
        field.set(obj, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

}
