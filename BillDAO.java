package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.BillBean;
import model.Bill;
import model.BookTicket;
import model.Date;

/**
 * Data Access Object for BILL
 */
public class BillDAO {

    // Phương thức thêm một hóa đơn mới vào bảng BILL
    public void addBill(Bill bill) {
        String sql = "INSERT INTO BILL ( BOOKING_ID, BILL_DATE, PAY_METHOD, STATUS) VALUES (?, ?, ?, ?) ";

        try (Connection connect = DatabaseUtil.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {
        	 statement.setInt(1, bill.getBookingID());  // Thêm booking ID vào
             statement.setDate(2, java.sql.Date.valueOf(bill.getBillDate().toDateString()));
             statement.setString(3, bill.getPayMethod());
             statement.setString(4, bill.getStatus());
            statement.executeUpdate();
            System.out.println("Bill added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức tìm thông tin hóa đơn dựa vào ID
    public BillBean findBillById(int billId) {
        String sql = "SELECT * FROM BILL WHERE BILL_ID = ?";
        BillBean bill = null;

        try (Connection connect = DatabaseUtil.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, billId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                bill = new BillBean();
                bill.setBillID(rs.getInt("BILL_ID"));
                bill.setBookingID(rs.getInt("BOOKING_ID"));
                java.sql.Date sqlDate = rs.getDate("BILL_DATE");
                if (sqlDate != null) {
                    String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    bill.setBillDate(new Date(day, month, year));
                }
                bill.setPayMethod(rs.getString("PAY_METHOD"));
                bill.setStatus(rs.getString("STATUS"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bill;
    }

    // Phương thức thêm hóa đơn với kiểu dữ liệu `BillBean`
    public boolean addBill(BillBean bill) {
        String sql = "INSERT INTO BILL ( BOOKING_ID, BILL_DATE, PAY_METHOD, STATUS) VALUES ( ?, ?, ?, ?)";

        try (Connection connect = DatabaseUtil.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

        	 statement.setInt(1, bill.getBookingID());  // Thêm booking ID vào
             statement.setDate(2, java.sql.Date.valueOf(bill.getBillDate().toDateString()));
             statement.setString(3, bill.getPayMethod());
             statement.setString(4, bill.getStatus());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding bill:");
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức chính để kiểm tra
    public static void main(String[] args) {
    	Date date = new Date(16,10,2004);
    	BookTicket bt = new BookTicket(1, 1,  date, 20, "da thanh toan");
    	Bill bill = new Bill(1,date,"chuyen khoan","tt");
    	BillDAO dao = new BillDAO();
    	//dao.addBill(bill);
    	System.out.println(dao.findBillById(1));
    }
}