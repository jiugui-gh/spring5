package chapter2.part9;

public class PowerAdapter implements DCS {

    private AC220 ac220;
    
    public PowerAdapter(AC220 ac220) {
        super();
        this.ac220 = ac220;
    }

    @Override
    public int outputDCSV() {
        // TODO Auto-generated method stub
        int adapterInput = ac220.outputAC220V();
        //变压器、
        int adapterOutput = adapterInput / 44;
        System.out.println("使用PowerAdapter 输入AC：" + adapterInput + "V,输出" + adapterOutput + "V");
        return adapterOutput;
    }

}
