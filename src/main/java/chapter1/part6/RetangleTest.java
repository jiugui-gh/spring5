package chapter1.part6;

public class RetangleTest {

	public static void resize(Rectangle rectangle) {
		// 长方向的宽应该大于高  我们让高不断自增直到与宽相等
		while(rectangle.getWidth() >= rectangle.getHeight()) {
			rectangle.setHeight(rectangle.getHeight() + 1);
			System.out.println("width:" + rectangle.getWidth() + ",height" + rectangle.getHeight());
		}
		
		System.out.println("resize 方法结束" + "width:" + rectangle.getWidth() + ",height" + rectangle.getHeight());
	}
	
	// 违背了里氏替换原则，将父类替换为子类后，程序运行结果没有达到预期
	public static void main(String[] args) {
		
		// 计算错误
		/*Rectangle rectangle = new Rectangle();
		rectangle.setWidth(20);
		rectangle.setHeight(10);
		resize(rectangle);*/
		
		// 无限循环
		Square square = new Square();
		square.setLength(10);
		resize(square);
	}
}
