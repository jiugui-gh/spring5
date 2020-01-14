package chapter2.part11;

public class ObserverTest {

    public static void main(String[] args) {
        GPer gper = GPer.getInstance();
        Teacher teacher1 = new Teacher("酒鬼");
        Teacher teacher2 = new Teacher("死货一个");
        
        gper.addObserver(teacher1);
        gper.addObserver(teacher2);
        
        Question question = new Question();
        question.setContent("为什么母猪会上树");
        question.setUserName("小明");
        gper.publishQuestion(question);
        
    }
}
