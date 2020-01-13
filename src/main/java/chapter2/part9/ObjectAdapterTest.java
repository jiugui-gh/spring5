package chapter2.part9;

public class ObjectAdapterTest {

    public static void main(String[] args) {
        PowerAdapter powerAdapter = new PowerAdapter(new AC220());
        powerAdapter.outputDCSV();
    }
    
}
