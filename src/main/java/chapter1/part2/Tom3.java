package chapter1.part2;

public class Tom3 {

	private ICourse course;

	public Tom3(ICourse course) {
		super();
		this.course = course;
	}
	
	public void study() {
		course.study();
	}
	
	public static void main(String[] args) {
		
		Tom3 tom3 = new Tom3(new JavaCourse());
		tom3.study();
	}
}
