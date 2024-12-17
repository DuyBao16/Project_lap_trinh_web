package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Ticket;

public class TicketDAO {
	public void addTicket(Ticket ticket) {
		String sql = "INSERT INTO TICKET( BOOKING_ID, SEAT_ID, PRICE) VALUES(?,?,?)";

		try (Connection connect = DatabaseUtil.getConnection()) {
			PreparedStatement statement = connect.prepareStatement(sql);
			statement.setInt(1, ticket.getBookingID());
			statement.setInt(2, ticket.getSeatID());
			statement.setDouble(3, ticket.getPrice());
			statement.executeUpdate();
			System.out.println("Add show successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Ticket> getAllTickets() {
		List<Ticket> tickets = new ArrayList<>();
		String sql = "SELECT * FROM TICKET";

		try {
			Connection connect = DatabaseUtil.getConnection();
			if (connect == null) {
				throw new SQLException("Kết nối cơ sở dữ liệu không thành công.");
			}
			PreparedStatement statement = connect.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int ticketId = resultSet.getInt("TICKET_ID");
				int bookingId = resultSet.getInt("BOOKING_ID");
				int seatId = resultSet.getInt("SEAT_ID");
				double price = resultSet.getDouble("PRICE");

				Ticket ticket = new Ticket(ticketId, bookingId, seatId, price);
				tickets.add(ticket);
			}
			connect.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tickets;
	}

	public double getPriceByTicketId(int ticketId) {
		String sql = "SELECT PRICE FROM TICKET WHERE TICKET_ID = ?";
		double price = 0.0;

		try (Connection connect = DatabaseUtil.getConnection();
				PreparedStatement statement = connect.prepareStatement(sql)) {

			// Set ticketId vào PreparedStatement
			statement.setInt(1, ticketId);

			// Thực thi truy vấn và lấy kết quả
			ResultSet resultSet = statement.executeQuery();

			// Nếu có kết quả, lấy giá vé
			if (resultSet.next()) {
				price = resultSet.getDouble("PRICE");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return price;
	}

	public String getMovieNameByTicketID(int ticketID) {
		String movieName = null;
		String query = "SELECT MOVIE.MOVIE_NAME " + "FROM MOVIE " + "JOIN SHOW ON MOVIE.MOVIE_ID = SHOW.MOVIE_ID "
				+ "JOIN BOOK_TICKET ON SHOW.SHOW_ID = BOOK_TICKET.SHOW_ID "
				+ "JOIN TICKET ON BOOK_TICKET.BOOKING_ID = TICKET.BOOKING_ID " + "WHERE TICKET.TICKET_ID = ?";
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketID);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					movieName = resultSet.getString("MOVIE_NAME");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movieName;
	}

	public String getShowStartTimeByTicketID(int ticketID) {
		String startTime = null;
		String query = "SELECT SHOW.START_TIME " + "FROM SHOW "
				+ "JOIN BOOK_TICKET ON SHOW.SHOW_ID = BOOK_TICKET.SHOW_ID "
				+ "JOIN TICKET ON BOOK_TICKET.BOOKING_ID = TICKET.BOOKING_ID " + "WHERE TICKET.TICKET_ID = ?";

		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketID);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					startTime = resultSet.getString("START_TIME");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return startTime;
	}

	public String getCinemaDetailsByTicketID(int ticketID) {
		String cinemaDetails = null;
		String query = "SELECT  CINEMA.CITY, CINEMA.STREET " + "FROM CINEMA "
				+ "JOIN ROOM ON CINEMA.CINEMA_ID = ROOM.CINEMA_ID " + "JOIN SHOW ON ROOM.ROOM_ID = SHOW.ROOM_ID "
				+ "JOIN BOOK_TICKET ON SHOW.SHOW_ID = BOOK_TICKET.SHOW_ID "
				+ "JOIN TICKET ON BOOK_TICKET.BOOKING_ID = TICKET.BOOKING_ID " + "WHERE TICKET.TICKET_ID = ?";

		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketID);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					
					String cinemaCity = resultSet.getString("CITY");
					String cinemaStreet = resultSet.getString("STREET");

					// Kết hợp thông tin để trả về một chuỗi
					cinemaDetails =   cinemaStreet+" , "+ cinemaCity;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cinemaDetails;
	}

	public String getNameCinemaByTicketID(int ticketID) {
		String cinemaName=null; 
		String query = "SELECT CINEMA.NAME, CINEMA.CITY, CINEMA.STREET " +
                 "FROM CINEMA " +
                 "JOIN ROOM ON CINEMA.CINEMA_ID = ROOM.CINEMA_ID " +
                 "JOIN SHOW ON ROOM.ROOM_ID = SHOW.ROOM_ID " +
                 "JOIN BOOK_TICKET ON SHOW.SHOW_ID = BOOK_TICKET.SHOW_ID " +
                 "JOIN TICKET ON BOOK_TICKET.BOOKING_ID = TICKET.BOOKING_ID " +
                 "WHERE TICKET.TICKET_ID = ?";
		 try (Connection connection = DatabaseUtil.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			 preparedStatement.setInt(1, ticketID);
			 ResultSet resultSet = preparedStatement.executeQuery();
			 if(resultSet.next()) {
				 cinemaName=resultSet.getString(1);
			 }
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
		 return cinemaName;
	}
	
	public String getRoomNameByTicketID(int ticketID) {
		String roomName = null;
		String query = "SELECT ROOM.NAME " + "FROM ROOM " + "JOIN SHOW ON ROOM.ROOM_ID = SHOW.ROOM_ID "
				+ "JOIN BOOK_TICKET ON SHOW.SHOW_ID = BOOK_TICKET.SHOW_ID "
				+ "JOIN TICKET ON BOOK_TICKET.BOOKING_ID = TICKET.BOOKING_ID " + "WHERE TICKET.TICKET_ID = ?";

		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketID);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					roomName = resultSet.getString("NAME");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roomName;
	}

	public int getSeatNumberByTicketID(int ticketID) {
		int seatNum =-1;
		String query = "SELECT SEAT.SEAT_NUMBER " + "FROM SEAT "
				+ "JOIN TICKET ON SEAT.SEAT_ID = TICKET.SEAT_ID " + "WHERE TICKET.TICKET_ID = ?";

		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketID);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					
					 seatNum = resultSet.getInt("SEAT_NUMBER");

				
				}
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seatNum;
	}
	public String getSeatRowByTicketID(int ticketId) {
		String seatRow = null;
		String query = "SELECT SEAT.ROW " + "FROM SEAT "
				+ "JOIN TICKET ON SEAT.SEAT_ID = TICKET.SEAT_ID " + "WHERE TICKET.TICKET_ID = ?";
		try (Connection connection = DatabaseUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Gán giá trị cho tham số trong câu truy vấn
			preparedStatement.setInt(1, ticketId);

			// Thực thi truy vấn và lấy kết quả
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					
					 seatRow = resultSet.getString("ROW");

					// Kết hợp thông tin về hàng và số ghế
					
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seatRow;

	}

	public static void main(String[] args) throws SQLException {
		TicketDAO dao = new TicketDAO();
		List<Ticket> lt = dao.getAllTickets();
		for (Ticket tk : lt)
			System.out.println(tk.toString());

	}
}
