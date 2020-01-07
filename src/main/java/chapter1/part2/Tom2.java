package chapter1.part2;

public class Tom2{

	public void study(ICourse course) {
		course.study();
	}

	public static void main(String[] args) {
		
		Tom2 tom2 = new Tom2();
		tom2.study(new JavaCourse());
		tom2.study(new PythonCourse());
	}
}
