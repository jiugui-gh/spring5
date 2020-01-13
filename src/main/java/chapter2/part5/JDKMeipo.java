package chapter2.part5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKMeipo implements InvocationHandler {
	// 保存代理对象
	private Object target;
	
	public Object getInstance(Object target) {
		
		this.target = target;
		//返回代理对象
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
		
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		if("findLove".equals(method.getName())) {
			before();
			Object invoke = method.invoke(this.target, args);
			after();
			
			return invoke;
		}
		
		return method.invoke(this.target,args);
	}

	private void after() {
		// TODO Auto-generated method stub
		System.out.println("如果合适的化，就准备办事了");
	}

	private void before() {
		// TODO Auto-generated method stub
		System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求了");
		System.out.println("开始物色");

	}

	public static void main(String[] args) {
		Customer customer = new Customer();
		JDKMeipo jdkMeipo = new JDKMeipo();
		Person p = (Person)jdkMeipo.getInstance(customer);
		p.findLove();
	}
}
