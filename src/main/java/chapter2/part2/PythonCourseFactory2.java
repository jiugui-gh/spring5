package chapter2.part2;

public class PythonCourseFactory2 implements CourseFactory {

	public INote createNote() {
		// TODO Auto-generated method stub
		return new PythonNote();
	}

	public IVideo createVideo() {
		// TODO -generated method stub
		return new PythonVideo();
	}

}
