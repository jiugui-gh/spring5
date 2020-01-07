package chapter1.part1;

public class JavaDiscountCourse extends JavaCourse {

	public JavaDiscountCourse(Integer id, String name, Double price) {
		super(id, name, price);
		// TODO Auto-generated constructor stub
	}
	// 返回原来的价格
	public Double getOriginPrice() {
		return super.getPrice();
	}
	// 覆盖了父类的非抽象方法   违反了里氏替换原则
	/*public Double getPrice() {
		return super.getPrice() * 0.61;
	}*/
	
	public Double getDiscountPrice() {
		return super.getPrice() * 0.61;
	}
}
