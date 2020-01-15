package chapter7.mvcframework.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.mvcframework.GPService.IDemoService;
import chapter7.mvcframework.annotation.GPAutowired;
import chapter7.mvcframework.annotation.GPController;
import chapter7.mvcframework.annotation.GPRequestMapping;
import chapter7.mvcframework.annotation.GPRequestParam;

@GPController
@GPRequestMapping("/demo")
public class DemoAction {

    @GPAutowired
    private IDemoService demoService;

    
    @GPRequestMapping("/query")
    public void query(HttpServletRequest req,HttpServletResponse resp,@GPRequestParam("name") String name) {
        String result = demoService.get(name);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @GPRequestMapping("/add")
    public void add(HttpServletRequest req,HttpServletResponse resp,@GPRequestParam("a") Integer a,@GPRequestParam("b") Integer b) {
        try {
            resp.getWriter().write(a + " + " + b + " = " + (a + b));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @GPRequestMapping("/remove")
    public void remove(HttpServletRequest req,HttpServletResponse resp,@GPRequestParam("id") Integer id) {
        System.out.println("调用remove");
    }
}
