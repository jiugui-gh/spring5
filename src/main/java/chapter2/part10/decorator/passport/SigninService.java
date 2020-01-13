package chapter2.part10.decorator.passport;

public class SigninService implements ISigninService {

    @Override
    public ResultMsg regist(String username, String password) {
        // TODO Auto-generated method stub
        return new ResultMsg(200, "注册成功", new Member());
    }

    @Override
    public ResultMsg login(String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }

}
