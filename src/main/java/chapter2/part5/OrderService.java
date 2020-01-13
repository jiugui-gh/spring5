package chapter2.part5;

public class OrderService implements IOrderService {

	private OrderDao orderDao;
	
	
	public OrderService() {
		this.orderDao = new OrderDao();
	}


	public int createOrder(Order order) {
		// TODO Auto-generated method stub
		System.out.println("OrderService调用OrderDao创建订单");
		return orderDao.insert(order);
	}

}
