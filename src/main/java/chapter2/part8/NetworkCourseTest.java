package chapter2.part8;

public class NetworkCourseTest {

    
    public static void main(String[] args) {
        JavaCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();
        System.out.println("--------------");
        NetworkCourse bigDataCourse = new BigDataCourse(true);
        bigDataCourse.createCourse();
    }
}
