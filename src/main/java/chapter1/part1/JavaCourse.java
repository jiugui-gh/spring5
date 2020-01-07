package chapter1.part1;

public class JavaCourse implements ICourse {
	private Integer id;
	private String name;
	private Double price;
	
	public JavaCourse(Integer id, String name, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public Double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}

	

}
