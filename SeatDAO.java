package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.SeatBean;
import model.Seat;

public class SeatDAO {
	public void addSeat(Seat seat) {
		String sql = "INSERT INTO SEAT( ROOM_ID, ROW, SEAT_NUMBER, TYPE, STATUS) VALUES(?,?,?,?,?)";
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, seat.getRoomID());
			statement.setString(2, seat.getRow());
			statement.setInt(3, seat.getSeatNumber());
			statement.setString(4, seat.getType());
			statement.setString(5, seat.getStatus());
						statement.executeUpdate();
			System.out.println("Add seat successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SeatBean findSeatByID(int id) {
		String sql = "SELECT * FROM SEAT WHERE SEAT_ID = ?";
        SeatBean seat = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                seat = new SeatBean();
                seat.setSeatID(rs.getInt("SEAT_ID"));
                seat.setRoomID(rs.getInt("ROOM_ID"));
                seat.setRow(rs.getString("ROW"));
                seat.setSeatNumber(rs.getInt("SEAT_NUMBER"));
                seat.setType(rs.getString("TYPE"));
                seat.setStatus(rs.getString("STATUS"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seat;
	}
	
	public boolean addSeat(SeatBean seat) {
		String sql = "INSERT INTO SEAT ( ROOM_ID, ROW, SEAT_NUMBER, TYPE, STATUS) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, seat.getRoomID());
        	stmt.setString(2, seat.getRow());
			stmt.setInt(3, seat.getSeatNumber());
			stmt.setString(4, seat.getType());
			stmt.setString(5, seat.getStatus());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	public List<SeatBean> getAvailableSeats(int roomID) {
	    String sql = "SELECT * FROM SEAT WHERE STATUS = ? AND ROOM_ID =?"; // Truy vấn ghế có trạng thái "trong"
	    List<SeatBean> availableSeats = new ArrayList<>();

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ) {
	    	stmt.setString(1, "da_dat");
    		stmt.setInt(2, roomID);
	        
	    	ResultSet rs = stmt.executeQuery();
	    	while (rs.next()) {
	            SeatBean seat = new SeatBean();
	            seat.setSeatID(rs.getInt("SEAT_ID"));
	            seat.setRoomID(rs.getInt("ROOM_ID"));
	            seat.setRow(rs.getString("ROW"));
	            seat.setSeatNumber(rs.getInt("SEAT_NUMBER"));
	            seat.setType(rs.getString("TYPE"));
	            seat.setStatus(rs.getString("STATUS"));

	            availableSeats.add(seat); // Thêm ghế vào danh sách ghế trống
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return availableSeats; // Trả về danh sách ghế trống
	}
	public List<SeatBean> getBookedSeats(int roomID) {
	    String sql = "SELECT * FROM SEAT WHERE STATUS = ? AND ROOM_ID=?"; // Truy vấn ghế có trạng thái "trong"
	    List<SeatBean> availableSeats = new ArrayList<>();

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	    		
	         ) {
	    	stmt.setString(1, "trong");
    		stmt.setInt(2, roomID);
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
	            SeatBean seat = new SeatBean();
	            seat.setSeatID(rs.getInt("SEAT_ID"));
	            seat.setRoomID(rs.getInt("ROOM_ID"));
	            seat.setRow(rs.getString("ROW"));
	            seat.setSeatNumber(rs.getInt("SEAT_NUMBER"));
	            seat.setType(rs.getString("TYPE"));
	            seat.setStatus(rs.getString("STATUS"));

	            availableSeats.add(seat); // Thêm ghế vào danh sách ghế đã đặt
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return availableSeats; // Trả về danh sách ghế trống
	}

	public boolean setStatusSeat(int seatId, String newStatus) {
	    String sql = "UPDATE SEAT SET STATUS = ? WHERE SEAT_ID = ?";
	    
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, newStatus); // Đặt giá trị trạng thái mới
	        stmt.setInt(2, seatId);       // Đặt ID ghế cần cập nhật

	        int rowsUpdated = stmt.executeUpdate(); // Thực thi câu lệnh cập nhật
	        return rowsUpdated > 0; // Trả về true nếu có ít nhất 1 hàng được cập nhật

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu xảy ra lỗi
	    }
	}
	public int getSeatsCount(int roomId) {
	    String sql = "SELECT COUNT(*) FROM SEAT WHERE ROOM_ID = ?"; // Truy vấn đếm số ghế còn trống trong phòng cụ thể
	    int seatsCount = 0;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, roomId); // Gán giá trị cho tham số ROOM_ID trong câu lệnh SQL
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	seatsCount = rs.getInt(1); // Lấy kết quả đếm được từ cột đầu tiên
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return seatsCount; // Trả về số ghế còn trống trong phòng
	}

	public int getAvailableSeatsCount(int roomId) {
	    String sql = "SELECT COUNT(*) FROM SEAT WHERE STATUS = ? AND ROOM_ID = ?"; // Truy vấn đếm số ghế còn trống trong phòng cụ thể
	    int availableSeatsCount = 0;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, "trong");
	        stmt.setInt(2, roomId); // Gán giá trị cho tham số ROOM_ID trong câu lệnh SQL
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            availableSeatsCount = rs.getInt(1); // Lấy kết quả đếm được từ cột đầu tiên
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return availableSeatsCount; // Trả về số ghế còn trống trong phòng
	}
	public int getBookedSeatsCount(int roomId) {
	    String sql = "SELECT COUNT(*) FROM SEAT WHERE STATUS = ? AND ROOM_ID = ?"; // Truy vấn đếm số ghế đã được đặt trong phòng
	    int bookedSeatsCount = 0;

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, "da_dat");
	        stmt.setInt(2, roomId); // Gán giá trị cho tham số ROOM_ID trong câu lệnh SQL
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            bookedSeatsCount = rs.getInt(1); // Lấy kết quả đếm được từ cột đầu tiên
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookedSeatsCount; // Trả về số ghế đã được đặt trong phòng
	}
	
	public static void main(String[] args) {
		SeatDAO dao = new SeatDAO();
		//Seat seat = new Seat(1,"1",1,"vip");
		//dao.addSeat(seat);
		System.out.println(dao.findSeatByID(1));
	}

}
