package chapter2.part10.decorator;

public class BattercakeTest {

    public static void main(String[] args) {
        Battercake cake;
        cake = new BaseBattercake();
        System.out.println(cake.getMsg() + "，总价格" + cake.getPrice());

        cake = new SausageDecorator(cake);
        System.out.println(cake.getMsg() + "，总价格" + cake.getPrice());
        
        cake = new EggDecorator(cake);
        System.out.println(cake.getMsg() + "，总价格" + cake.getPrice());
    }
}
