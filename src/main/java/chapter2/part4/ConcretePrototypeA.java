package chapter2.part4;

import java.util.Arrays;
import java.util.List;

public class ConcretePrototypeA implements Prototype,Cloneable {

	private int age;
	private String name;
	private List<String> hobbies;
	
	public Prototype clone() {
		// TODO Auto-generated method stub
		
		try {
			ConcretePrototypeA clone = (ConcretePrototypeA)super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		/*ConcretePrototypeA obj = new ConcretePrototypeA();
		obj.setAge(this.age);
		obj.setName(this.name);
		obj.setHobbies(this.hobbies);
		return obj;*/
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public static void main(String[] args) {
		//创建一个具体的需要克隆的对象
		ConcretePrototypeA a = new ConcretePrototypeA();
		a.setAge(10);
		a.setName("酒鬼");
		a.setHobbies(Arrays.asList(new String[] {"打篮球","踢足球"}));
		
		Client client = new Client(a);
		ConcretePrototypeA b = (ConcretePrototypeA)client.startClone(a);
		
		System.out.println(a.getName() == b.getName());
		System.out.println(a.getAge() == b.getAge());
		System.out.println(a.getHobbies() == b.getHobbies());
		
		
	}
}
