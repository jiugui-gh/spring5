package chapter2.part3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

public class SeriableSingleton implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final SeriableSingleton INSTANCE = new SeriableSingleton();
	
	public static  SeriableSingleton getInstence() {
		return INSTANCE;
	}
	// 如果既想要做到可序列化，又想要反序列化为同一对象，则必须实现readResolve方法。
	private Object readResolve() {
		return INSTANCE;
	}
	public static void main(String[] args) throws Exception {
		SeriableSingleton s1 = null;
		SeriableSingleton s2 = SeriableSingleton.getInstence();
		
		URL resource = SeriableSingleton.class.getClassLoader().getResource("");
		System.out.println(resource.getPath());
		System.out.println(SeriableSingleton.class.getResource("").getPath());
		
		//InputStream is = SeriableSingleton.class.getClassLoader().getResourceAsStream("/SeriableSingleton.obj");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("SeriableSingleton.obj"));
		out.writeObject(s2);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("SeriableSingleton.obj"));
		s1 = (SeriableSingleton)in.readObject();
		
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s1 == s2);
		
		in.close();
		out.close();
	}
}
