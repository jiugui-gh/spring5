package chapter2.part5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceDynamicProxy implements InvocationHandler {

	private Object target;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	
	public Object getInstance(Object obj) {
		this.target = obj;
		
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(),this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		if("createOrder".equals(method.getName())) {
			before(args[0]);
			Object reuslt = method.invoke(this.target, args);
			after();
			return reuslt;
		}
		return method.invoke(this.target, args);
	}
	private void before(Object obj) {
		// TODO Auto-generated method stub
		System.out.println("Proxy beofre method");
		try {
			Long time = (Long)obj.getClass().getMethod("getCreateTime", null).invoke(obj, null);
			String year = format.format(new Date(time));
			System.out.println("自动分配到【DB_"+year+"】数据源粗合理数据");
			DynamicDataSourceEntry.set(year);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void after() {
		// TODO Auto-generated method stub
		System.out.println("Proxy after method");
	}

	public static void main(String[] args) {
		OrderService service = new OrderService();
		OrderServiceDynamicProxy proxy = new OrderServiceDynamicProxy();
		IOrderService instance = (IOrderService)proxy.getInstance(service);
		
		Order order = new Order();
		order.setCreateTime(new Date().getTime());
		order.setId("122");
		order.setOrderInfo("死货订单");
		
		instance.createOrder(order);
	}

}
