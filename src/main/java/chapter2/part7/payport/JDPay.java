package chapter2.part7.payport;

public class JDPay extends Payment {

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "京东白条";
    }

    @Override
    protected double queryBalance(String uid) {
        // TODO Auto-generated method stub
        return 500;
    }

}
