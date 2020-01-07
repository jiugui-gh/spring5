package chapter1.part1;

public class OpenCloseTest {

	public static void main(String[] args) {
		
		ICourse javaCourse = new JavaCourse(1,"java课程",100.00);
		
		ICourse javaDiscountCourse = new JavaDiscountCourse(1,"java课程",100.00);
		System.out.println(javaCourse.getPrice());
		System.out.println(javaDiscountCourse.getPrice());
		
		
	}
}
