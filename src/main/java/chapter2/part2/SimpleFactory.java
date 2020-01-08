package chapter2.part2;

import java.util.Date;

public class SimpleFactory {

	public static Object create(Class<? extends Object> clazz) {
		
		try {
			if (null != clazz) {
				return clazz.newInstance();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		Object create = SimpleFactory.create(Date.class);
		System.out.println(create);
	}
}
