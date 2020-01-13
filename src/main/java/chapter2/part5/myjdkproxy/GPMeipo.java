package chapter2.part5.myjdkproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import chapter2.part5.Customer;
import chapter2.part5.Person;

public class GPMeipo implements GPInvocationHandler {

    private Object target;
    
    public Object getInstance(Object target) {
        this.target = target;
        
        Class<?> clazz = target.getClass();
        return GPProxy.newProxyInstance(new GPClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // TODO Auto-generated method stub
        before();
        Object obj = null;
        try {
            obj = method.invoke(target, args);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        after();
        return obj;
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
        Customer customer = new Customer();
        Person obj = (Person)new GPMeipo().getInstance(customer);
        obj.findLove();
    }
    

}
