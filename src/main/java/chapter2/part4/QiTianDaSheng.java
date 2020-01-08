package chapter2.part4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class QiTianDaSheng extends Monkey implements Serializable,Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JinGuBang jinGuBang;
	
	public QiTianDaSheng() {
		this.birthday = new Date();
		this.jinGuBang = new JinGuBang();
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return this.deepClone();
	}

	private Object deepClone() {
		// TODO Auto-generated method stub
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			QiTianDaSheng clone = (QiTianDaSheng)ois.readObject();
			
			return clone;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		QiTianDaSheng qtds = new QiTianDaSheng();
		QiTianDaSheng clone = (QiTianDaSheng)qtds.clone();
		
		System.out.println(qtds.jinGuBang == clone.jinGuBang);
	}
}
