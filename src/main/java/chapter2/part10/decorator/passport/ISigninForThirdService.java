package chapter2.part10.decorator.passport;

public interface ISigninForThirdService extends ISigninService {
    ResultMsg loginForQQ(String id);
    ResultMsg loginForWechat(String id);
    ResultMsg loginForToken(String token);
}
