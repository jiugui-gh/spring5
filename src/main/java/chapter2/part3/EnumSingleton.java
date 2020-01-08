package chapter2.part3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

public enum EnumSingleton {

	INSTANCE;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static EnumSingleton getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) throws Exception {
		EnumSingleton e1 = null;
		EnumSingleton e2 = EnumSingleton.getInstance();
		e2.setData(new Object());

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(EnumSingleton.class.getResource("").getPath()+"\\enum.obj"));
		out.writeObject(e2);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(EnumSingleton.class.getResource("").getPath()+"\\enum.obj"));
		e1 = (EnumSingleton)in.readObject();
		
		System.out.println(e2.getData());
		System.out.println(e1.getData());
		
		System.out.println(e1 == e2);
		System.out.println(e1.getData() == e2.getData());
		
		in.close();
		out.close();
		
		Class<?> clazz = EnumSingleton.class;
		Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(null);
		Object newInstance = declaredConstructor.newInstance(null);
		System.out.println(newInstance);
	}
}
