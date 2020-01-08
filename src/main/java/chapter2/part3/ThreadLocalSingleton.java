package chapter2.part3;

public class ThreadLocalSingleton {

	private static int count = 0;
	private static ThreadLocal<ThreadLocalSingleton> t = new ThreadLocal<ThreadLocalSingleton>() {
		@Override
		protected ThreadLocalSingleton initialValue() {
			// TODO Auto-generated method stub
			System.out.println("初始化" + count++);
			return new ThreadLocalSingleton();
		}
	};
	
	
	public static void main(String[] args) {
		System.out.println(t.get());
		System.out.println(t.get());
		System.out.println(t.get());
		System.out.println(t.get());
		
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println(t.get());
				System.out.println(t.get());
				System.out.println(t.get());
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				System.out.println(t.get());
				System.out.println(t.get());
				System.out.println(t.get());
			}
			
		});
		t1.start();
		t2.start();
	}
}
