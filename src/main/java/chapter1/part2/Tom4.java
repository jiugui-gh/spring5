package chapter1.part2;

public class Tom4 {

	private ICourse course;

	public void setCourse(ICourse course) {
		this.course = course;
	}
	
	public void study() {
		course.study();
	}
	
	public static void main(String[] args) {
		Tom4 tom4 = new Tom4();
		
		tom4.setCourse(new PythonCourse());
		tom4.study();
	}
}
