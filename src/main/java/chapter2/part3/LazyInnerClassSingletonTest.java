package chapter2.part3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LazyInnerClassSingletonTest {

	public static void main(String[] args) {
		
		try {
			// 在很无聊的时候进行破坏
			Class<?> clazz = LazyInnerClassSingleton.class;
			
			// 通过反射调用使用的构造函数
			Constructor<?> con = clazz.getDeclaredConstructor(null);
			// 设置访问权限
			con.setAccessible(true);
			
			//暴力初始化
			Object obj1 = con.newInstance();
			Object obj2 = con.newInstance();
		
			System.out.println(obj1);
			System.out.println(obj2);
			
			System.out.println(obj1 == obj2);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
