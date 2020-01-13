package chapter2.part10.decorator;

public class EggDecorator extends BattercakeDecorator {

    public EggDecorator(Battercake battercake) {
        super(battercake);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doSomething() {}
    
    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return super.getMsg() + "+1个鸡蛋";
    }
    
    @Override
    protected int getPrice() {
        // TODO Auto-generated method stub
        return super.getPrice() + 1;
    }
}
