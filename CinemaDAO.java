package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.MovieBean;
import model.Cinema;
import model.Date;

public class CinemaDAO {
	
		public void addCinema(Cinema cinema) {
			String sql = "INSERT INTO CINEMA( NAME, CITY, STREET, CONTACT_INFO) VALUES(?,?,?,?)";
			
			try (Connection connect = DatabaseUtil.getConnection()) {
				PreparedStatement statement = connect.prepareStatement(sql);
				statement.setString(1, cinema.getName());
				statement.setString(2, cinema.getCity());
				statement.setString(3, cinema.getStreet());
				statement.setString(4, cinema.getContactInfo());
				statement.executeUpdate();
				System.out.println("Add cinema successfully");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public List<Cinema> getAllCinemas() {
		    String sql = "SELECT * FROM CINEMA";
		    List<Cinema> cinemas = new ArrayList<>();

		    try (Connection connect = DatabaseUtil.getConnection();
		         PreparedStatement statement = connect.prepareStatement(sql);
		         ResultSet rs = statement.executeQuery()) {

		        while (rs.next()) {
		            Cinema cinema = new Cinema();
		            cinema.setCinemaID(rs.getInt("CINEMA_ID")); 
		            cinema.setName(rs.getString("NAME"));
		            cinema.setCity(rs.getString("CITY"));
		            cinema.setStreet(rs.getString("STREET"));
		            cinema.setContactInfo(rs.getString("CONTACT_INFO"));

		            cinemas.add(cinema); // Thêm đối tượng Cinema vào danh sách
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return cinemas; // Trả về danh sách các rạp chiếu
		}
		public List<String> getCinemaNames() {
		    String sql = "SELECT NAME FROM CINEMA"; // Chỉ lấy cột NAME
		    List<String> cinemaNames = new ArrayList<>();

		    try (Connection connect = DatabaseUtil.getConnection();
		         PreparedStatement statement = connect.prepareStatement(sql);
		         ResultSet rs = statement.executeQuery()) {

		        while (rs.next()) {
		            String cinemaName = rs.getString("NAME"); // Lấy tên rạp
		            cinemaNames.add(cinemaName); // Thêm vào danh sách tên
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return cinemaNames; // Trả về danh sách tên các rạp
		}
		 public List<Cinema> getCinemasByMovieId(int movieId) {
		        String sql = "SELECT c.CINEMA_ID, c.NAME, c.CITY, c.STREET, c.CONTACT_INFO " +
		                     "FROM CINEMA c " +
		                     "JOIN ROOM r ON c.CINEMA_ID = r.CINEMA_ID " +
		                     "JOIN SHOW s ON r.ROOM_ID = s.ROOM_ID " +
		                     "WHERE s.MOVIE_ID = ?";
		        List<Cinema> cinemas = new ArrayList<>();

		        try (Connection connect = DatabaseUtil.getConnection();
		             PreparedStatement statement = connect.prepareStatement(sql)) {

		            statement.setInt(1, movieId); // Thêm MOVIE_ID vào câu lệnh SQL
		            ResultSet rs = statement.executeQuery();

		            while (rs.next()) {
		                Cinema cinema = new Cinema();
		                cinema.setCinemaID(rs.getInt("CINEMA_ID"));
		                cinema.setName(rs.getString("NAME"));
		                cinema.setCity(rs.getString("CITY"));
		                cinema.setStreet(rs.getString("STREET"));
		                cinema.setContactInfo(rs.getString("CONTACT_INFO"));

		                cinemas.add(cinema); // Thêm đối tượng Cinema vào danh sách
		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		        return cinemas; // Trả về danh sách các rạp chiếu
		    }
		 
		 public Cinema findCinemaByID (int cinemaId) {
			 String sql = "SELECT * FROM CINEMA WHERE CINEMA_ID=?";
			 Cinema cinema = new Cinema();
			 try {
				 Connection conn = DatabaseUtil.getConnection();
				 PreparedStatement statement = conn.prepareStatement(sql);
				 statement.setInt(1, cinemaId);
				 ResultSet rs = statement.executeQuery();
				 while(rs.next()) {
					 cinema.setCinemaID(rs.getInt("CINEMA_ID"));
					 cinema.setName(rs.getString("NAME"));
					 cinema.setCity(rs.getString("CITY"));
		             cinema.setStreet(rs.getString("STREET"));
		             cinema.setContactInfo(rs.getString("CONTACT_INFO"));

				 }
			 }catch(SQLException e) {
				 e.printStackTrace();
			 }
			 return cinema;
		 }
		 		 
			
public static void main(String[] args) {
	CinemaDAO dao = new CinemaDAO();
	//for(MovieBean m : dao.findMovieByCinemaId(1)) System.out.println(m.toString());
}		
			
}

