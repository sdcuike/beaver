package com.doctor.commons;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.doctor.commons.JdkProxyFactoryUtil.InvocationHandlerImpl;

public class JdkProxyFactoryUtilTest {
    public static void main(String[] args) {
        Hell hell = new Hell();
        InvocationHandlerImpl invocationHandlerImpl = new InvocationHandlerImpl(hell) {

            @Override
            public boolean preInvoke(Method method, Object[] args) {
                System.out.println("preInvoke:" + " method:" + method + " args: " + Arrays.toString(args));
                return true;
            }

            @Override
            public void postInvoke(Object invokeResult, Method method, Object[] args, Exception e) {
                System.out.println("postInvoke:" + "invokeResult:" + invokeResult + " arg:" + Arrays.toString(args) + " Exception:" + e);
            }

        };

        Hello hello = JdkProxyFactoryUtil.newProxyInstance(Hello.class, invocationHandlerImpl);
        String string = hello.get("doctor who");

        System.out.println(string);
    }

    interface Hello {
        String get(String name);
    }

    static class Hell implements Hello {

        @Override
        public String get(String name) {

            return name + " :sd";
        }

    }
}
