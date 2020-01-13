package chapter2.part7.payport;

public class Order {

    private String uid;
    private String orderId;
    private double amount;
    private String payKey;
    public Order(String uid, String orderId, double amount,String payKey) {
        super();
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
        this.payKey = payKey;
    }
    
    public PayState pay() {
        return pay(payKey);
    }

    private PayState pay(String payKey) {
        // TODO Auto-generated method stub
        Payment payment = PayStrategy.getPayment(payKey);
        System.out.println("欢迎使用" + payment.getName());
        System.out.println("本次交易金额为：" + amount + "，开始扣款");
        PayState pay = payment.pay(uid, amount);
        return pay;
    }
}
