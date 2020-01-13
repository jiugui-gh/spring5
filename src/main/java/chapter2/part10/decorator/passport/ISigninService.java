package chapter2.part10.decorator.passport;

public interface ISigninService {

    ResultMsg regist(String username,String password);
    ResultMsg login(String username,String password);
}
