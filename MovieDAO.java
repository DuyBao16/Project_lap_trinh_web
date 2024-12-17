package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.MovieBean;
import model.Date;
import model.Movie;

public class MovieDAO {
	public void addMovie(Movie movie) {
		String sql = "INSERT INTO MOVIE( MOVIE_NAME, GENRE, DURATION, DESCRIPTION, RELEASE_DATE, RATING, POSTER_URL, TRAILER_URL)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
		
		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setString(1, movie.getMovieName());
			statement.setString(2, movie.getGenre());
			statement.setInt(3, movie.getDuration());
			statement.setString(4, movie.getDescription());
			statement.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate().toDateString()));			
			statement.setDouble(6, movie.getRating());
			statement.setString(7, movie.getPosterURL());
			statement.setString(8, movie.getTrailerURL());
			
			statement.executeUpdate();
			System.out.println("Add movie successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MovieBean findMovieByMovieID(int movieID) {
		String sql = "SELECT * FROM MOVIE WHERE MOVIE_ID = ?";
        MovieBean movie = null;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, movieID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	movie = new MovieBean();
            	movie.setMovieID(rs.getInt("MOVIE_ID"));
            	movie.setMovieName(rs.getString("MOVIE_NAME"));
            	movie.setGenre(rs.getString("GENRE"));
            	movie.setDuration(rs.getInt("DURATION"));
            	movie.setDescription(rs.getString("DESCRIPTION"));
                java.sql.Date sqlDate = rs.getDate("RELEASE_DATE");
                if (sqlDate != null) {
                    String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    movie.setReleaseDate(new Date(day, month, year));
                }

                movie.setRating(rs.getDouble("RATING")); 
                movie.setPosterURL(rs.getString("POSTER_URL"));
                movie.setTrailerURL(rs.getString("TRAILER_URL"));
             }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movie;
	}
	
	public List<MovieBean> getAllMovies() {
	    String sql = "SELECT * FROM MOVIE";
	    List<MovieBean> movies = new ArrayList<>();

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            MovieBean movie = new MovieBean();
	            movie.setMovieID(rs.getInt("MOVIE_ID"));
	            movie.setMovieName(rs.getString("MOVIE_NAME"));
	            movie.setGenre(rs.getString("GENRE"));
	            movie.setDuration(rs.getInt("DURATION"));
	            movie.setDescription(rs.getString("DESCRIPTION"));
	            
	            java.sql.Date sqlDate = rs.getDate("RELEASE_DATE");
	            if (sqlDate != null) {
	                String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
	                int year = Integer.parseInt(parts[0]);
	                int month = Integer.parseInt(parts[1]);
	                int day = Integer.parseInt(parts[2]);
	                movie.setReleaseDate(new Date(day, month, year));
	            }

	            movie.setRating(rs.getDouble("RATING"));
	            movie.setPosterURL(rs.getString("POSTER_URL"));
	            movie.setTrailerURL(rs.getString("TRAILER_URL"));

	            movies.add(movie);
	        }
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movies;
	}
	
	public boolean addMovie(MovieBean movie) {
		String sql = "INSERT INTO MOVIE ( MOVIE_NAME, GENRE, DURATION, DESCRIPTION, RELEASE_DATE, RATING, POSTER_URL, TRAILER_URL) "
							+ "VALUES ( ?, ?, ?, ?, ?,?,?,?)";

        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setString(1, movie.getMovieName());
        	stmt.setString(2, movie.getGenre());
        	stmt.setInt(3, movie.getDuration());
        	stmt.setString(4, movie.getDescription());
        	stmt.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate().toDateString()));			
        	stmt.setDouble(6, movie.getRating());
        	stmt.setString(7, movie.getPosterURL());
        	stmt.setString(8, movie.getTrailerURL());
			
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	public List<MovieBean> getNowShowingMovies() {
	    String sql = "SELECT * FROM MOVIE WHERE RELEASE_DATE BETWEEN CURRENT_DATE - INTERVAL '30 days' AND CURRENT_DATE";
	    List<MovieBean> movies = new ArrayList<>();

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            MovieBean movie = new MovieBean();
	            movie.setMovieID(rs.getInt("MOVIE_ID"));
	            movie.setMovieName(rs.getString("MOVIE_NAME"));
	            movie.setGenre(rs.getString("GENRE"));
	            movie.setDuration(rs.getInt("DURATION"));
	            movie.setDescription(rs.getString("DESCRIPTION"));

	            java.sql.Date sqlDate = rs.getDate("RELEASE_DATE");
	            if (sqlDate != null) {
	                String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
	                int year = Integer.parseInt(parts[0]);
	                int month = Integer.parseInt(parts[1]);
	                int day = Integer.parseInt(parts[2]);
	                movie.setReleaseDate(new Date(day, month, year));
	            }

	            movie.setRating(rs.getDouble("RATING"));
	            movie.setPosterURL(rs.getString("POSTER_URL"));
	            movie.setTrailerURL(rs.getString("TRAILER_URL"));

	            movies.add(movie);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movies;
	}
	
	public List<MovieBean> getUpcomingMovies() {
	    String sql = "SELECT * FROM MOVIE WHERE RELEASE_DATE > CURRENT_DATE";
	    List<MovieBean> movies = new ArrayList<>();

	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            MovieBean movie = new MovieBean();
	            movie.setMovieID(rs.getInt("MOVIE_ID"));
	            movie.setMovieName(rs.getString("MOVIE_NAME"));
	            movie.setGenre(rs.getString("GENRE"));
	            movie.setDuration(rs.getInt("DURATION"));
	            movie.setDescription(rs.getString("DESCRIPTION"));

	            java.sql.Date sqlDate = rs.getDate("RELEASE_DATE");
	            if (sqlDate != null) {
	                String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
	                int year = Integer.parseInt(parts[0]);
	                int month = Integer.parseInt(parts[1]);
	                int day = Integer.parseInt(parts[2]);
	                movie.setReleaseDate(new Date(day, month, year));
	            }

	            movie.setRating(rs.getDouble("RATING"));
	            movie.setPosterURL(rs.getString("POSTER_URL"));
	            movie.setTrailerURL(rs.getString("TRAILER_URL"));

	            movies.add(movie);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movies;
	}
	public List<String> getMovieNames() {
	    String sql = "SELECT MOVIE_NAME FROM MOVIE"; // Chỉ lấy cột NAME
	    List<String> movieNames = new ArrayList<>();

	    try (Connection connect = DatabaseUtil.getConnection();
	         PreparedStatement statement = connect.prepareStatement(sql);
	         ResultSet rs = statement.executeQuery()) {

	        while (rs.next()) {
	            String movieName = rs.getString("MOVIE_NAME"); 
	            movieNames.add(movieName); 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return movieNames; 
	}
	
	public boolean updateMovieByID(Movie movie) {
	    String sql = "UPDATE MOVIE SET MOVIE_NAME = ?, GENRE = ?, DURATION = ?, DESCRIPTION = ?, RELEASE_DATE = ?, RATING = ?, POSTER_URL = ?, TRAILER_URL = ? WHERE MOVIE_ID = ?";
	    
	    try (Connection conn = DatabaseUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // Gán giá trị cho các tham số trong câu lệnh SQL
	        stmt.setString(1, movie.getMovieName());
	        stmt.setString(2, movie.getGenre());
	        stmt.setInt(3, movie.getDuration());
	        stmt.setString(4, movie.getDescription());
	        stmt.setDate(5, java.sql.Date.valueOf(movie.getReleaseDate().toDateString()));			
	        stmt.setDouble(6, movie.getRating());
	        stmt.setString(7, movie.getPosterURL());
	        stmt.setString(8, movie.getTrailerURL());
	        stmt.setInt(9, movie.getMovieID()); // Tham số cuối cùng là MOVIE_ID

	        // Thực thi câu lệnh và kiểm tra số hàng bị ảnh hưởng
	        int rowsUpdated = stmt.executeUpdate();
	        return rowsUpdated > 0; // Trả về true nếu cập nhật thành công

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Trả về false nếu xảy ra lỗi
	    }
	}
	public boolean deleteMovieByID(int movieID) {
	    String checkShowSQL = "SELECT COUNT(*) FROM SHOW WHERE MOVIE_ID = ?";
	    String deleteShowSQL = "DELETE FROM SHOW WHERE MOVIE_ID = ?";
	    String deleteMovieSQL = "DELETE FROM MOVIE WHERE MOVIE_ID = ?";
	    Connection conn = null;
	    PreparedStatement checkShowStmt = null;
	    PreparedStatement deleteShowStmt = null;
	    PreparedStatement deleteMovieStmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DatabaseUtil.getConnection();
	        conn.setAutoCommit(false); // Tắt auto-commit để quản lý transaction

	        // Kiểm tra xem movieID có trong bảng SHOW hay không
	        checkShowStmt = conn.prepareStatement(checkShowSQL);
	        checkShowStmt.setInt(1, movieID);
	        rs = checkShowStmt.executeQuery();

	        if (rs.next()) { // Kiểm tra nếu có dữ liệu
	            int showCount = rs.getInt(1);

	            if (showCount == 0) {
	                // Không có liên kết với SHOW, chỉ xóa MOVIE
	                deleteMovieStmt = conn.prepareStatement(deleteMovieSQL);
	                deleteMovieStmt.setInt(1, movieID);
	                int movieRowsDeleted = deleteMovieStmt.executeUpdate();
	                if (movieRowsDeleted > 0) {
	                    conn.commit();
	                    return true;
	                }
	            } else {
	                // Có liên kết, xóa trong SHOW trước
	                deleteShowStmt = conn.prepareStatement(deleteShowSQL);
	                deleteShowStmt.setInt(1, movieID);
	                deleteShowStmt.executeUpdate();

	                // Sau đó xóa MOVIE
	                deleteMovieStmt = conn.prepareStatement(deleteMovieSQL);
	                deleteMovieStmt.setInt(1, movieID);
	                int movieRowsDeleted = deleteMovieStmt.executeUpdate();

	                if (movieRowsDeleted > 0) {
	                    conn.commit();
	                    return true;
	                }
	            }
	        }

	        // Nếu không thể xóa, rollback transaction
	        conn.rollback();
	        return false;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            if (conn != null) {
	                conn.rollback(); // Rollback nếu xảy ra lỗi
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        return false;

	    } finally {
	        // Đảm bảo tất cả các tài nguyên đều được đóng đúng cách
	        try {
	            if (rs != null) rs.close();
	            if (checkShowStmt != null) checkShowStmt.close();
	            if (deleteShowStmt != null) deleteShowStmt.close();
	            if (deleteMovieStmt != null) deleteMovieStmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public String getMovieNameByID(int id) {
		 String sql = "SELECT MOVIE_NAME FROM MOVIE WHERE MOVIE_ID=?";
		    String result="";
		    try (Connection conn = DatabaseUtil.getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        // Gán giá trị cho các tham số trong câu lệnh SQL
		        stmt.setInt(1, id);
		       
		        // Thực thi câu lệnh và kiểm tra số hàng bị ảnh hưởng
		       ResultSet rs=stmt.executeQuery();
		       if(rs.next()) result = rs.getString("MOVIE_NAME");
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return result;
	}

	public List<MovieBean> findMovieByCinemaId(int cinemaId){
		 String sql = "SELECT DISTINCT MOVIE.* "
		 		+ "FROM CINEMA JOIN ROOM ON CINEMA.CINEMA_ID = ROOM.CINEMA_ID "
		 		+ "JOIN SHOW ON SHOW.ROOM_ID = ROOM.ROOM_ID "
		 		+ "JOIN MOVIE ON MOVIE.MOVIE_ID = SHOW.MOVIE_ID "
		 		+ "WHERE CINEMA.CINEMA_ID = ?  ";
		 List<MovieBean> listMovie = new ArrayList<>();
		 try {
			 Connection conn = DatabaseUtil.getConnection();
			 PreparedStatement st = conn.prepareStatement(sql);
			 st.setInt(1, cinemaId);
			 ResultSet rs = st.executeQuery();
			 while(rs.next()) {
				 MovieBean movie = new MovieBean();
	            	movie.setMovieID(rs.getInt("MOVIE_ID"));
	            	movie.setMovieName(rs.getString("MOVIE_NAME"));
	            	movie.setGenre(rs.getString("GENRE"));
	            	movie.setDuration(rs.getInt("DURATION"));
	            	movie.setDescription(rs.getString("DESCRIPTION"));
	                java.sql.Date sqlDate = rs.getDate("RELEASE_DATE");
	                if (sqlDate != null) {
	                    String[] parts = sqlDate.toString().split("-"); // Tách theo định dạng yyyy-MM-dd
	                    int year = Integer.parseInt(parts[0]);
	                    int month = Integer.parseInt(parts[1]);
	                    int day = Integer.parseInt(parts[2]);

	                    movie.setReleaseDate(new Date(day, month, year));
	                }

	                movie.setRating(rs.getDouble("RATING")); 
	                movie.setPosterURL(rs.getString("POSTER_URL"));
	                movie.setTrailerURL(rs.getString("TRAILER_URL"));
	                
	                listMovie.add(movie);
			 }
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
		 return listMovie;
	 }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MovieDAO mdao= new MovieDAO();
		List<MovieBean> listMovie = mdao.getAllMovies();
		for(MovieBean mb:listMovie) System.out.println(mb.toString());
		
	}

}
