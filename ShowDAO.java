package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.ShowBean;
import model.Date;
import model.Show;

public class ShowDAO {
	public void addShow(Show show) {
		String sql = "INSERT INTO SHOW( MOVIE_ID, ROOM_ID, START_TIME, END_TIME) VALUES(?,?,?,?)";
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, show.getMovieID()); 
			statement.setInt(2, show.getRoomID());
			statement.setTimestamp(3, Timestamp.valueOf(show.getStartTime().toTimestampString()));
			statement.setTimestamp(4, Timestamp.valueOf(show.getEndTime().toTimestampString()));
			statement.executeUpdate();
			System.out.println("Add show successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ShowBean findShowByShowID(int id) {
		String sql = "SELECT * FROM SHOW WHERE SHOW_ID = ?";
        ShowBean show = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                show = new ShowBean();
                show.setShowID(rs.getInt("SHOW_ID"));
                show.setMovieID(rs.getInt("MOVIE_ID"));
                show.setRoomID(rs.getInt("ROOM_ID"));
                
                java.sql.Timestamp startTimestamp = rs.getTimestamp("START_TIME");
                if (startTimestamp != null) {
                    String[] dateTimeParts = startTimestamp.toString().split(" ");
                    String[] dateParts = dateTimeParts[0].split("-");
                    String[] timeParts = dateTimeParts[1].split(":");
                    
                    int year = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int day = Integer.parseInt(dateParts[2]);
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    int second = (int)Double.parseDouble(timeParts[2]);

                    Date startTime = new Date( hour, minute, second,year, month, day);
                    show.setStartTime(startTime);
                }

                // Chuyển đổi END_TIME
                java.sql.Timestamp endTimestamp = rs.getTimestamp("END_TIME");
                if (endTimestamp != null) {
                    String[] dateTimeParts = endTimestamp.toString().split(" ");
                    String[] dateParts = dateTimeParts[0].split("-");
                    String[] timeParts = dateTimeParts[1].split(":");
                    
                    int year = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int day = Integer.parseInt(dateParts[2]);
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    int second = (int)Double.parseDouble(timeParts[2]);

                    Date endTime = new Date(hour, minute, second,year, month, day);
                    show.setEndTime(endTime);
                }            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return show;
	}
	
	public boolean addShow(ShowBean show) {
		String sql = "INSERT INTO SHOW ( MOVIE_ID, ROOM_ID, START_TIME, END_TIME) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, show.getMovieID());
        	stmt.setInt(2, show.getRoomID());
        	stmt.setTimestamp(3, Timestamp.valueOf(show.getStartTime().toTimestampString()));
			stmt.setTimestamp(4, Timestamp.valueOf(show.getEndTime().toTimestampString()));
			
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	
	

	    public static List<Show> getShowsByMovieAndRoom(int movieId, int roomId) {
	        List<Show> shows = new ArrayList<>();
	        
	        // Truy vấn lấy danh sách các buổi chiếu theo movieId và roomId
	        String sql = "SELECT  s.START_TIME, s.END_TIME " +
	                     "FROM SHOW s " +
	                     "WHERE s.MOVIE_ID = ? AND s.ROOM_ID = ?";  // Lọc theo movieId và roomId

	        try (Connection connection = DatabaseUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            // Set các tham số vào câu lệnh SQL
	            statement.setInt(1, movieId);
	            statement.setInt(2, roomId);

	            // Thực thi truy vấn
	            ResultSet rs = statement.executeQuery();

	            // Duyệt qua các kết quả và tạo đối tượng Show
	            while (rs.next()) {
	            	Date startTime=null;
	    	        Date endTime=null;
	    	        
	            	java.sql.Timestamp startTimestamp = rs.getTimestamp("START_TIME");
	                if (startTimestamp != null) {
	                    String[] dateTimeParts = startTimestamp.toString().split(" ");
	                    String[] dateParts = dateTimeParts[0].split("-");
	                    String[] timeParts = dateTimeParts[1].split(":");
	                    
	                    int year = Integer.parseInt(dateParts[0]);
	                    int month = Integer.parseInt(dateParts[1]);
	                    int day = Integer.parseInt(dateParts[2]);
	                    int hour = Integer.parseInt(timeParts[0]);
	                    int minute = Integer.parseInt(timeParts[1]);
	                    int second = (int)Double.parseDouble(timeParts[2]);

	                    startTime = new Date( hour, minute, second,year, month, day);
	                    
	                }

	                // Chuyển đổi END_TIME
	                java.sql.Timestamp endTimestamp = rs.getTimestamp("END_TIME");
	                if (endTimestamp != null) {
	                    String[] dateTimeParts = endTimestamp.toString().split(" ");
	                    String[] dateParts = dateTimeParts[0].split("-");
	                    String[] timeParts = dateTimeParts[1].split(":");
	                    
	                    int year = Integer.parseInt(dateParts[0]);
	                    int month = Integer.parseInt(dateParts[1]);
	                    int day = Integer.parseInt(dateParts[2]);
	                    int hour = Integer.parseInt(timeParts[0]);
	                    int minute = Integer.parseInt(timeParts[1]);
	                    int second = (int)Double.parseDouble(timeParts[2]);

	                    endTime = new Date(hour, minute, second,year, month, day);
	                }  
	                // Tạo đối tượng Show và thêm vào danh sách
	                Show show = new Show( startTime, endTime);
	                shows.add(show);
	            }
	            

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return shows;
	    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShowDAO dao = new ShowDAO();
		Date start = new Date(20,12,40,11,12,2024);
		Date end = new Date(22,10,40,11,12,2024);
		Show show = new Show(1, 1, start, end);
		//dao.addShow(show);
		System.out.println(dao.findShowByShowID(1));
	}

}
