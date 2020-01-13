package chapter2.part5.myjdkproxy;
import chapter2.part5.Person;
import java.lang.reflect.*;
public class TestCore implements chapter2.part5.Person{
GPInvocationHandler h;
    public TestCore(GPInvocationHandler h) {
        this.h = h;
    }
    public void findLove() {
        try{
            Method m  = chapter2.part5.Person.class.getMethod("findLove",new Class[] {});
            this.h.invoke(this,m,new Object[]{});
        }catch(Error _ex){}catch(Throwable e){
            throw new UndeclaredThrowableException(e);
        }
    }
}

