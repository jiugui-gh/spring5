package chapter2.part3;
/**
 * 静态内部类
 * @author Pinkboy
 *
 */
public class LazyInnerClassSingleton {

	private LazyInnerClassSingleton() {
		//防止通过反射破坏单例
		if(InnerClass.LAZY != null) {
			throw new RuntimeException("不能创建多个实例");
		}
	}
	
	public static final LazyInnerClassSingleton getInstance() {
		return InnerClass.LAZY;
	}
	
	static class InnerClass{
		private  static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
	}
	
	public static void main(String[] args) {
		System.out.println(LazyInnerClassSingleton.getInstance());
	}
}
