package chapter2.part3;
/**
 * 静态代码块 + 饿汉式
 * @author Pinkboy
 *
 */
public class HungryStaticSingleton {

	private static HungryStaticSingleton hungryStaticSingleton;
	
	static {
		hungryStaticSingleton = new HungryStaticSingleton();
	}
	
	public static HungryStaticSingleton getInstance() {
		return hungryStaticSingleton;
	}
	
	public static void main(String[] args) {
		System.out.println(HungryStaticSingleton.getInstance());
	}
}
