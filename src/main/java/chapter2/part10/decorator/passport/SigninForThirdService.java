package chapter2.part10.decorator.passport;

public class SigninForThirdService implements ISigninForThirdService {

    private ISigninService signin;
    
    public SigninForThirdService(ISigninService signin) {
        super();
        this.signin = signin;
    }

    @Override
    public ResultMsg regist(String username, String password) {
        // TODO Auto-generated method stub
        return signin.regist(username, password);
    }

    @Override
    public ResultMsg login(String username, String password) {
        // TODO Auto-generated method stub
        return signin.login(username, password);
    }

    @Override
    public ResultMsg loginForQQ(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultMsg loginForWechat(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultMsg loginForToken(String token) {
        // TODO Auto-generated method stub
        return null;
    }

}
