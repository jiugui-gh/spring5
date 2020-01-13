package chapter2.part7.payport;

public class PayTest {

    public static void main(String[] args) {
        Order order = new Order("1","1231564",100,PayStrategy.UNION_PAY);
        PayState pay = order.pay();
        System.out.println(pay);
        
    }
}
