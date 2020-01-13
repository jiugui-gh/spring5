package chapter2.part7.payport;

import java.util.HashMap;
import java.util.Map;

public class PayStrategy {

    public static final String ALI_PAY = "AliPay";
    public static final String JD_PAY = "JdPay";
    public static final String UNION_PAY = "UnionPay";
    public static final String WECHAT_PAY = "WechatPay";
    public static final String DEFAUT_PAY = "AliPay";
    
    private static Map<String,Payment> payStrategy = new HashMap<String,Payment>();
    
    static {
        payStrategy.put(ALI_PAY, new AliPay());
        payStrategy.put(JD_PAY, new JDPay());
        payStrategy.put(UNION_PAY, new UnionPay());
        payStrategy.put(WECHAT_PAY, new WechatPay());
    }
    
    public static Payment getPayment(String payKey) {
        if(!payStrategy.containsKey(payKey)) {
            return payStrategy.get(DEFAUT_PAY);
        }
        return payStrategy.get(payKey);
    }
}
