package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Room;

public class RoomDAO {
	public void addRoom(Room room) {
		String sql = "INSERT INTO ROOM( CINEMA_ID, NAME, SEAT_LAYOUT) VALUES (?,?,?)" ;
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, room.getCinemaID());
			statement.setString(2, room.getName());
			statement.setString(3, room.getSeatLayout());
			statement.executeUpdate();
			System.out.println("Add room successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getRoomCountByMovieName(String movieName) {
	    String sql = "SELECT COUNT(DISTINCT r.ROOM_ID) AS room_count " +
	                 "FROM SHOW s " +
	                 "INNER JOIN MOVIE m ON s.MOVIE_ID = m.MOVIE_ID " +
	                 "INNER JOIN ROOM r ON s.ROOM_ID = r.ROOM_ID " +
	                 "WHERE m.MOVIE_NAME = ?";
	    int roomCount = 0;

	    try (Connection connect = DatabaseUtil.getConnection();
	         PreparedStatement statement = connect.prepareStatement(sql)) {

	        statement.setString(1, movieName);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                roomCount = rs.getInt("room_count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return roomCount;
	}
	// Phương thức lấy danh sách các phòng chiếu dựa trên movieID
    public List<Room> getRoomsByMovieID(int movieID) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT r.ROOM_ID, r.NAME, r.CINEMA_ID, r.SEAT_LAYOUT " +
                     "FROM ROOM r " +
                     "JOIN SHOW s ON r.ROOM_ID = s.ROOM_ID " +
                     "WHERE s.MOVIE_ID = ?";  // Lọc theo movieID
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, movieID);  // Đặt giá trị movieID vào câu truy vấn
            
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getInt("ROOM_ID"));
                    room.setName(rs.getString("NAME"));
                    room.setCinemaID(rs.getInt("CINEMA_ID"));
                    room.setSeatLayout(rs.getString("SEAT_LAYOUT"));
                    rooms.add(room);  // Thêm phòng chiếu vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;  // Trả về danh sách phòng chiếu
    }

	public static void main(String[] args) {
		RoomDAO romdao = new RoomDAO();
		Room room = new Room(1,"sds","d");
		romdao.addRoom(room);
		
	}
}
