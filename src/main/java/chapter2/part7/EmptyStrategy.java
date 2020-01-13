package chapter2.part7;
// 无优惠策略
public class EmptyStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        // TODO Auto-generated method stub
        System.out.println("无促销活动");
    }

}
