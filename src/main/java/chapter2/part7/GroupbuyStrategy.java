package chapter2.part7;
// 拼团策略
public class GroupbuyStrategy implements PromotionStrategy {

    @Override
    public void doPromotion() {
        // TODO Auto-generated method stub
        System.out.println("拼团,满20人成团，全团享受团价"); 
    }

}
