package chapter2.part5.myjdkproxy;

import java.lang.reflect.Method;

public interface GPInvocationHandler {
    public Object invoke(Object proxy,Method method,Object[] args) throws Throwable;
}
