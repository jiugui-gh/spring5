package chapter2.part2;
// 创建一个抽象工厂
public interface CourseFactory {
    INote createNote();
    IVideo createVideo();
}
