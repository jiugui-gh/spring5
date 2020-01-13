package chapter2.part7;

public class PromotionActitity {

    private PromotionStrategy promotionStrategy;

    public PromotionActitity(PromotionStrategy promotionStrategy) {
        super();
        this.promotionStrategy = promotionStrategy;
    }
    
    public void execute() {
        this.promotionStrategy.doPromotion();
    }
    
    public static void main(String[] args) {
     //   test1();
        
        test2();
    }

    private static void test1() {
        CashbackStrategy cashbackStrategy = new CashbackStrategy();
        PromotionActitity promotionActitity1 = new PromotionActitity(cashbackStrategy);
        promotionActitity1.execute();
        
        GroupbuyStrategy groupbuyStrategy = new GroupbuyStrategy();
        PromotionActitity promotionActitity2 = new PromotionActitity(groupbuyStrategy);
        promotionActitity2.execute();
    }
    
    private static void test2() {
        
        PromotionActitity promotionActitity = null;
        
        String promotionKey = "COUPON";
        
        if("COUPON".equals(promotionKey)) {
            promotionActitity = new PromotionActitity(new CouponStrategy());
        }else if("CASHBACK".equals(promotionKey)) {
            promotionActitity = new PromotionActitity(new CashbackStrategy()); 
        }
        
        promotionActitity.execute();
    }
}
