package chapter2.part4;

import java.io.Serializable;

public class JinGuBang implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public float h = 100;
	public float d = 10;
	public void big() {
		this.d *= 2;
		this.h *= 2;
	}
	
	public void smal() {
		this.d /= 2;
		this.h /= 2;
	}
}
