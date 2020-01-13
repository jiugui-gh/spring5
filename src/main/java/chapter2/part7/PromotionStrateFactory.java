package chapter2.part7;

import java.util.HashMap;
import java.util.Map;

public class PromotionStrateFactory {

    private static Map<String,PromotionStrategy> PROMOTION_STRATEGY_MAP = new HashMap<String,PromotionStrategy>();
    
    static {
        PROMOTION_STRATEGY_MAP.put(PromotionKey.COUPON, new CouponStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.CASHBACK, new CashbackStrategy());
        PROMOTION_STRATEGY_MAP.put(PromotionKey.GROUPBUY, new GroupbuyStrategy());
    }
    
    private static final PromotionStrategy NON_PROMOTION = new EmptyStrategy();
    
    public static PromotionStrategy getPromotionStrategy(String promotionKey) {
        PromotionStrategy promotionStrategy = PROMOTION_STRATEGY_MAP.get(promotionKey);
        return promotionStrategy == null ? NON_PROMOTION : promotionStrategy;
    }
    
    private interface PromotionKey{
        String COUPON = "COUPON";
        String CASHBACK = "CASHBACK";
        String GROUPBUY = "GROUPBUY";
    }
    
    public static void main(String[] args) {
        String promotionKey = "GROUPBUY";
        PromotionStrategy promotionStrategy = PromotionStrateFactory.getPromotionStrategy(promotionKey);
        PromotionActitity promotionActitity = new PromotionActitity(promotionStrategy);
        promotionActitity.execute();
    }
}
