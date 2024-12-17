/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




/**
 * 
 */
public class DatabaseUtil {
	private static final String URL = "jdbc:postgresql://localhost:5432/letWatch";
	private static final String USER = "postgres";
	private static final String PASSWORD = "5432";
	
	
	public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver không được tìm thấy!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Không thể kết nối tới cơ sở dữ liệu!");
            e.printStackTrace();
        }
        return connection;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}

}
