/**
 * 
 */
package database;

import model.Account;

import java.sql.Connection;
import java.sql.SQLException;

import beans.AccountBean;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * 
 */
public class AccountDAO {
	public void addAccount(Account account) {
		String sql = "INSERT INTO ACCOUNT( USERNAME, PASSWORD, FULLNAME, EMAIL, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, account.getUsername());
			statement.setString(2, account.getPassword());
			statement.setString(3, account.getFullName());
			statement.setString(4, account.getEmail());
			statement.setString(5, account.getPhoneNumber());
			
			
			statement.executeUpdate();
			System.out.println("Add account successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AccountBean findAccountByUsername(String username) {
		String sql = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
        AccountBean user = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new AccountBean();
                
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setFullName(rs.getString("FULLNAME"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPhone(rs.getString("PHONE_NUMBER"));
                user.setRole(rs.getString("ROLE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
	}
	
	public boolean addAccount(AccountBean user) {
		String sql = "INSERT INTO ACCOUNT (username, password, fullname, email, phone_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	public boolean updateAccount(Account account) {
	    String sql = "UPDATE ACCOUNT SET PASSWORD = ?, FULLNAME = ?, EMAIL = ?, PHONE_NUMBER = ? WHERE USERNAME = ?";

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // Thiết lập các giá trị cần cập nhật
	        stmt.setString(1, account.getPassword());
	        stmt.setString(2, account.getFullName());
	        stmt.setString(3, account.getEmail());
	        stmt.setString(4, account.getPhoneNumber());
	        stmt.setString(5, account.getUsername()); // Sử dụng USERNAME làm khóa chính

	        // Thực thi lệnh UPDATE
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean addAdminAccount(Account account) {
	    String sql = "INSERT INTO ACCOUNT (USERNAME, PASSWORD, FULLNAME, EMAIL, PHONE_NUMBER, ROLE) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, account.getUsername());
	        stmt.setString(2, account.getPassword());
	        stmt.setString(3, account.getFullName());
	        stmt.setString(4, account.getEmail());
	        stmt.setString(5, account.getPhoneNumber());
	        stmt.setString(6, "ADMIN"); // Thiết lập vai trò là ADMIN

	        int rowsInserted = stmt.executeUpdate();
	        return rowsInserted > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean isAdmin(String username) {
	    String sql = "SELECT ROLE FROM ACCOUNT WHERE USERNAME = ?";

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String role = rs.getString("ROLE");
	            return "ADMIN".equalsIgnoreCase(role); // So sánh không phân biệt hoa thường
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false; // Mặc định không phải admin nếu không tìm thấy tài khoản
	}
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
