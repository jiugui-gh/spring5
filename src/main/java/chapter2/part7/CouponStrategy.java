package chapter2.part7;
// 优惠券抵扣策略
public class CouponStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        // TODO Auto-generated method stub
        System.out.println("领取优惠券,课程价格直接减优惠卷面值抵扣");
    }

}
