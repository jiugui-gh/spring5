package chapter1.part2;

public class Tom {
    public void studyJavaCourse() {
    	System.out.println("Tom 在学习Java的课程");
    }
	
    public void studyPythonCourse() {
    	System.out.println("Tom 在学习Python的课程");
    }
    
    public static void main(String[] args) {
		Tom tom = new Tom();
		tom.studyJavaCourse();
		tom.studyPythonCourse();
	}
}
