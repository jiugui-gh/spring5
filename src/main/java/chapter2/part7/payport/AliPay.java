package chapter2.part7.payport;

public class AliPay extends Payment {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "支付宝";
    }

    @Override
    protected double queryBalance(String uid) {
        // TODO Auto-generated method stub
        return 900;
    }

}
