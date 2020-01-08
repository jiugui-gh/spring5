package chapter2.part3;

public class LazyDoubleCheckSingleton {

	/**
	 * volatile关键字避免指令从排列 
	 * lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
	 * 指令1.分配内存给对象
	 * 指令2.初始化对象
	 * 指令3.设置lazyDoubleCheckSingleton指针指向内存地址
	 * 这三个指令随机重排列的话可能出现顺序为132
	 * 当指令执行到3的时候if(lazyDoubleCheckSingleton == null)判断为false  索引返回一个未初始化的对象
	 * 
	 */
	private static volatile LazyDoubleCheckSingleton lazyDoubleCheckSingleton;
	
	public static LazyDoubleCheckSingleton getInstance() {
		
		if(lazyDoubleCheckSingleton == null) {
			synchronized(LazyDoubleCheckSingleton.class) {
				
				if(lazyDoubleCheckSingleton == null) {
					lazyDoubleCheckSingleton = new LazyDoubleCheckSingleton();
				}
			}
		}
		return lazyDoubleCheckSingleton;
	}
}
