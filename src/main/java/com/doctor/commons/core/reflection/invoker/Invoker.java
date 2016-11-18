package com.doctor.commons.core.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

public interface Invoker {

    Object invoke(Object obj, Object... args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}
