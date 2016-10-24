package com.doctor.commons;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk代理辅助类
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年10月21日 <br/>
 */
public final class JdkProxyFactoryUtil {

    /**
     * @param typeForInterface
     * @param handler
     * @return
     */
    public static <T> T newProxyInstance(Class<T> typeForInterface, InvocationHandler handler) {
        return typeForInterface.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[] { typeForInterface }, handler));
    }

    public static class InvocationHandlerImpl implements InvocationHandler {
        private Object targetObject;

        public InvocationHandlerImpl(Object targetObject) {
            this.targetObject = targetObject;
        }

        @Override
        public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!preInvoke(method, args)) {
                return null;
            }
            Object invoke = null;
            try {
                invoke = method.invoke(targetObject, args);
                postInvoke(invoke, method, args, null);
            } catch (Exception e) {
                postInvoke(invoke, method, args, e);
                throw e;
            }

            return invoke;
        }

        /**
         * @param method
         * @param args
         * @return {@code boolean} true，方法继续执行
         */
        public boolean preInvoke(Method method, Object[] args) {
            return true;

        }

        public void postInvoke(Object invokeResult, Method method, Object[] args, Exception e) {

        }

    }

}
