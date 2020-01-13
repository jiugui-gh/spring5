package chapter2.part10.decorator;

public abstract class BattercakeDecorator extends Battercake {

    // 静态代理，委派
    private Battercake battercake;
    
    public BattercakeDecorator(Battercake battercake) {
        super();
        this.battercake = battercake;
    }

    protected abstract void doSomething();
    
    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return this.battercake.getMsg();
    }

    @Override
    protected int getPrice() {
        // TODO Auto-generated method stub
        return this.battercake.getPrice();
    }

}
