package chapter2.part5;

public class Father {
	private Son son;

	public Father(Son son) {
		super();
		this.son = son;
	}
	
	public void findLove() {
		System.out.println("父亲帮忙物色");
		son.findLove();
	}
	
	public static void main(String[] args) {
		Son son2 = new Son();
		Father fa = new Father(son2);
		
		fa.findLove();
	}
}
