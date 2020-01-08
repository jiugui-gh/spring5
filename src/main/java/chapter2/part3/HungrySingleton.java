package chapter2.part3;
//饿汉式
public class HungrySingleton {

	private static HungrySingleton hungrySingleton = new HungrySingleton();
	
	public static HungrySingleton getInstance() {
		return hungrySingleton;
	}
	
	public static void main(String[] args) {
		System.out.println(HungrySingleton.getInstance());
	}
}
