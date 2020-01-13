package chapter2.part6;

import java.util.HashMap;
import java.util.Map;

public class Leader implements IEmployee {

    private Map<String,IEmployee> targets = new HashMap<String,IEmployee>();
    
    public Leader() {
        targets.put("加密", new EmployeeA());
        targets.put("登陆", new EmployeeB());
    }
    
    @Override
    public void doing(String command) {
        // TODO Auto-generated method stub
        IEmployee iEmployee = targets.get(command);
        
        if(iEmployee != null) {
            iEmployee.doing(command);
        }
    }

}
