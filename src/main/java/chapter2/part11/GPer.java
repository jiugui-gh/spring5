package chapter2.part11;

import java.util.Observable;

public class GPer extends Observable {
    private String name = "GPer 生态圈";
    private static volatile GPer gper = null;
    
    private GPer() {}
    
    public static GPer getInstance() {
        //懒汉式    有并发问题     双重检查锁解决
        if(null == gper) {
            synchronized(GPer.class) {
                if(null == gper) {
                    gper = new GPer();
                }
            }
          
        }
        return gper;
    }
    
    public String getName() {
        return name;
    }
    
    public void publishQuestion(Question question) {
        System.out.println(question.getUserName() + "在" + this.name + "上提交了一个问题。");
        setChanged();
        notifyObservers(question);
    }
}
