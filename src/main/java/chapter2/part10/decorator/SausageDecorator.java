package chapter2.part10.decorator;

public class SausageDecorator extends BattercakeDecorator {

    public SausageDecorator(Battercake battercake) {
        super(battercake);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doSomething() {}
    
    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return super.getMsg() + "+1根香肠";
    }
    
    @Override
    protected int getPrice() {
        // TODO Auto-generated method stub
        return super.getPrice() + 2;
    }
}
