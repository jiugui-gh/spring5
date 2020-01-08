package chapter2.part2;

public class JavaCourseFactory2 implements CourseFactory {

	public INote createNote() {
		// TODO Auto-generated method stub
		return new JavaNote();
	}

	public IVideo createVideo() {
		// TODO Auto-generated method stub
		return new JavaVideo();
	}

}
