package chapter2.part11;

import java.util.Observable;
import java.util.Observer;

public class Teacher implements Observer {

    private String name;
    
    public Teacher(String name) {
        super();
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        GPer gper = (GPer)o;
        Question question = (Question)arg;
        
        System.out.println("=============================");
        System.out.println(name + "老师，你好！您收到了一个来自" + gper.getName() + "的提问，希望您解答，内容如下" );
        System.out.println("提问者" + question.getUserName() + ",内容：" + question.getContent());
    }
}
