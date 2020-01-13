package chapter2.part7;
// 返现促销策略
public class CashbackStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        // TODO Auto-generated method stub
        System.out.println("返现促销,返回的金额转到支付好账号");
    }

}
