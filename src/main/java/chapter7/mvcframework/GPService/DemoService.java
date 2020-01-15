package chapter7.mvcframework.GPService;

import chapter7.mvcframework.annotation.GPService;

@GPService
public class DemoService implements IDemoService {

    @Override
    public String get(String name) {
        // TODO Auto-generated method stub
        return "My name is " + name;
    }

}
