package chapter2.part3;

public class LazySimpleSingleton {

	private static LazySimpleSingleton lazySimpleSingleton;
	
	public static synchronized LazySimpleSingleton  getInstance() {
		if(lazySimpleSingleton == null) {
			lazySimpleSingleton = new LazySimpleSingleton();
		}
		
		return lazySimpleSingleton;
	}
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println(LazySimpleSingleton.getInstance());
			}
			
		});
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println(LazySimpleSingleton.getInstance());
			}
			
		});
		t1.start();
		t2.start();
		
		
		
	}
}
