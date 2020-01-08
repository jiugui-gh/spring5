package chapter2.part2;

public class MethodFactoryTest {

	public static void main(String[] args) {
		// 工厂方法缺点  创建过多，                   
		PythonCourseFactory pyFactory = new PythonCourseFactory();
		ICourse create = pyFactory.create();
		create.record();
	}
}
