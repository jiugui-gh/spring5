package chapter2.part5.CGLib;

import java.lang.reflect.Method;

import chapter2.part5.Customer;
import chapter2.part5.Person;
import chapter2.part5.myjdkproxy.GPMeipo;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMeipo implements MethodInterceptor{

    private Object getInstance(Class<?> clazz) {
        
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        
        return enhancer.create();
    }
    
    @Override
    public Object intercept(Object arg0, Method method, Object[] arg2, MethodProxy methodProxy) throws Throwable {
        // TODO Auto-generated method stub
        before();
        Object result = methodProxy.invokeSuper(arg0, arg2);
        after();
        return result;
    }
    
    private void before() {
        // TODO Auto-generated method stub
        System.out.println("我是媒婆:我要给你找对象，现在已经确认了你的需求");
        System.out.println("开始物色");
    }
    
    private void after() {
        // TODO Auto-generated method stub
        System.out.println("如果合适的话，就准备办事");
    }
    
    public static void main(String[] args) {
       /* System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, CglibMeipo.class.getResource("").getPath());*/
       /* System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\Pinkboy\\Desktop\\test");*/
        Customer obj = (Customer)new CglibMeipo().getInstance(Customer.class);
        obj.findLove();
    }
}
