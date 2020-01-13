package chapter2.part7.payport;

public class UnionPay extends Payment {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "银联支付";
    }

    @Override
    protected double queryBalance(String uid) {
        // TODO Auto-generated method stub
        return 120;
    }

}
