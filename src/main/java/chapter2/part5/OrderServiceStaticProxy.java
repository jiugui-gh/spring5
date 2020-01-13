package chapter2.part5;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceStaticProxy implements IOrderService {

	private IOrderService orderService;
	
	private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	
	public OrderServiceStaticProxy(IOrderService orderService) {
		super();
		this.orderService = orderService;
	}

	public int createOrder(Order order) {
		// TODO Auto-generated method stub
		before();
		Long createTime = order.getCreateTime();
		Date date = new Date(createTime);
		String year = yearFormat.format(date);
		System.out.println("静态代理类自动分配到【DB_"+year+"】数据源处理数据");
		System.out.println(DynamicDataSourceEntry.get());
		DynamicDataSourceEntry.set(year);
		orderService.createOrder(order);
		after();
		return 0;
	}

	private void after() {
		// TODO Auto-generated method stub
		System.out.println("Proxy after method");
	}

	private void before() {
		// TODO Auto-generated method stub
		System.out.println("Proxy beofre method");
	}
	
	public static void main(String[] args) {
		OrderService service = new OrderService();
		OrderServiceStaticProxy proxy = new OrderServiceStaticProxy(service);
		
		Order order = new Order();
		order.setCreateTime(new Date().getTime());
		order.setId("1");
		order.setOrderInfo("第一单");
	
		proxy.createOrder(order);
		
		Thread t = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				OrderService service = new OrderService();
				OrderServiceStaticProxy proxy = new OrderServiceStaticProxy(service);
				
				Order order = new Order();
				order.setCreateTime(new Date().getTime());
				order.setId("1");
				order.setOrderInfo("第一单");
			
				proxy.createOrder(order);
			}
			
		});
		t.start();
	}
}
