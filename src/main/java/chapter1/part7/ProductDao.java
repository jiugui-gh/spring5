package chapter1.part7;

public class ProductDao {

	private DBConnection2 dbConnection;

	public void setDbConnection(DBConnection2 dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	public void addProduct() {
		String conn = dbConnection.getConnection();
		System.out.println("使用" + conn + "增加产品");
	}
	
	public static void main(String[] args) {
		ProductDao productDao = new ProductDao();
		productDao.setDbConnection(new MySQLConnection());
		productDao.addProduct();
		
		productDao.setDbConnection(new OracleConnection());
		productDao.addProduct();
	}
}
