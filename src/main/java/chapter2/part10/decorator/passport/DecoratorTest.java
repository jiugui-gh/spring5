package chapter2.part10.decorator.passport;

public class DecoratorTest {

    public static void main(String[] args) {
        ISigninForThirdService signinForThirdService = new SigninForThirdService(new SigninService());
    }
}
