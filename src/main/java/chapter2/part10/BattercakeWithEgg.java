package chapter2.part10;

public class BattercakeWithEgg extends Battercake {

    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return super.getMsg() + "+1个鸡蛋";
    }
    
    @Override
    public int getPrice() {
        // TODO Auto-generated method stub
        return super.getPrice() + 1;
    }
}
