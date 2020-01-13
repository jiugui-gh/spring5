package chapter2.part6;

public class EmployeeB implements IEmployee {

    @Override
    public void doing(String command) {
        // TODO Auto-generated method stub
        System.out.println("我是员工B,我现在开始干" + command + "工作");
    }

}
