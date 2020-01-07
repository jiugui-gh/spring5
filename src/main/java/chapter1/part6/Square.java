package chapter1.part6;
/**
 * 正方形
 * @author Pinkboy
 *
 */
public class Square extends Rectangle {
	private long length;

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public long getHeight() {
		// TODO Auto-generated method stub
		return getLength();
	}

	@Override
	public void setHeight(long height) {
		// TODO Auto-generated method stub
		setLength(height);
	}

	@Override
	public long getWidth() {
		// TODO Auto-generated method stub
		return getLength();
	}

	@Override
	public void setWidth(long width) {
		// TODO Auto-generated method stub
		setLength(width);
	}
	
}
