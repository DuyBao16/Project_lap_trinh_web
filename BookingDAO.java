/**
 * 
 */
package database;

import model.BookTicket;
import model.Date;

import java.sql.Connection;
import java.sql.SQLException;

import beans.BookingBean;

import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * 
 */
public class BookingDAO {
	public void addBookTicket(BookTicket bookTicket) {
		String sql = "INSERT INTO BOOK_TICKET(ACCOUNT_ID, SHOW_ID, BOOK_DATE, TOTAL_AMOUNT, STATUS) VALUES (?,?,?,?,?)";
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, bookTicket.getAccountID());
			statement.setInt(2, bookTicket.getShowID());
			statement.setDate(3, java.sql.Date.valueOf(bookTicket.getBookDate().toDateString()));			
			statement.setDouble(4, bookTicket.getTotalAmount());
			statement.setString(5, bookTicket.getStatus());
			
			
			statement.executeUpdate();
			System.out.println("Add booking successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BookingBean findBookingByBookID(int bookingID) {
		String sql = "SELECT * FROM BOOK_TICKET WHERE BOOKING_ID = ?";
        BookingBean booking = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                booking = new BookingBean();
                booking.setBookingID(rs.getInt("BOOKING_ID"));
                booking.setAccountID(rs.getInt("ACCOUNT_ID"));
                booking.setShowID(rs.getInt("SHOW_ID"));
                java.sql.Date sqlDate = rs.getDate("BOOK_DATE");
                if (sqlDate != null) {
                    String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    booking.setBookDate(new Date(day, month, year));
                }

                booking.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
                booking.setStatus(rs.getString("STATUS"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
	}
	
	public boolean addBooking(BookingBean booking) {
		String sql = "INSERT INTO BOOK_TICKET ( ACCOUNT_ID, SHOW_ID, BOOK_DATE, TOTAL_AMOUNT, STATUS) VALUES ( ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, booking.getAccountID());
        	stmt.setInt(2, booking.getShowID());
        	stmt.setDate(3, java.sql.Date.valueOf(booking.getBookDate().toDateString()));			
        	stmt.setDouble(4, booking.getTotalAmount());
        	stmt.setString(5, booking.getStatus());
		
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date(16,10,2004);
		BookTicket bt = new BookTicket(1,1,date,22,"đá");
		BookingDAO dao = new BookingDAO();
		//dao.addBookTicket(bt);
		System.out.println(dao.findBookingByBookID(1));
	}

}
