package chapter2.part10.decorator;

import chapter2.part10.decorator.Battercake;

public class BaseBattercake extends Battercake {

    @Override
    protected String getMsg() {
        // TODO Auto-generated method stub
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        // TODO Auto-generated method stub
        return 5;
    }

}
