package chapter2.part7.payport;

public class WechatPay extends Payment {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "微信支付";
    }

    @Override
    protected double queryBalance(String uid) {
        // TODO Auto-generated method stub
        return 256;
    }

}
