package chapter2.part10;

public class BattercakeWithEggAndSausage extends BattercakeWithEgg {

    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return super.getMsg() + "+1根香肠";
    }
    
    @Override
    public int getPrice() {
        // TODO Auto-generated method stub
        return super.getPrice() + 2;
    }
}
