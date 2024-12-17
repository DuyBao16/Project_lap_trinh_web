package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public static void createTables()  {
        
    	
        	try (
        	Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {
        	
            // Tạo bảng Account
            String accountTable = "CREATE TABLE IF NOT EXISTS ACCOUNT (" +
                     
                    "USERNAME VARCHAR(100) NOT NULL PRIMARY KEY," +
                    "PASSWORD VARCHAR(100) NOT NULL," +
                    "FULLNAME VARCHAR(255) NOT NULL," +
                    "EMAIL VARCHAR(255)," +
                    "PHONE_NUMBER VARCHAR(15)," +
                    "ROLE VARCHAR(10) "+
                    ")";
            statement.executeUpdate(accountTable);

            // Tạo bảng BookTicket 
            String bookTicketTable = "CREATE TABLE IF NOT EXISTS BOOK_TICKET (" +
                    "BOOKING_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "USERNAME VARCHAR(100) NOT NULL," +
                    "SHOW_ID INTEGER NOT NULL," +
                    "BOOK_DATE DATE NOT NULL," +
                    "TOTAL_AMOUNT NUMERIC NOT NULL," +
                    "STATUS VARCHAR(20) NOT NULL," +
                    "FOREIGN KEY (USERNAME) REFERENCES ACCOUNT(USERNAME)" +
                    ")";
            statement.executeUpdate(bookTicketTable);

            // Tạo bảng Bill 
            String billTable = "CREATE TABLE IF NOT EXISTS BILL (" +
                    "BILL_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "BOOKING_ID INTEGER NOT NULL," +
                    "BILL_DATE DATE NOT NULL," +
                    "PAY_METHOD VARCHAR(255) NOT NULL," +
                    "STATUS VARCHAR(255)," +
                    "FOREIGN KEY (BOOKING_ID) REFERENCES BOOK_TICKET(BOOKING_ID)" +
                    ")";
            statement.executeUpdate(billTable);

            // Tạo bảng Cinema
            String cinemaTable = "CREATE TABLE IF NOT EXISTS CINEMA (" +
                    "CINEMA_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "NAME VARCHAR(50) NOT NULL," +
                    "CITY VARCHAR(50) NOT NULL," +
                    "STREET VARCHAR(50) NOT NULL," +
                    "CONTACT_INFO VARCHAR(255) NOT NULL" +
                    ")";
            statement.executeUpdate(cinemaTable);

            // Tạo bảng Movie
            String movieTable = "CREATE TABLE IF NOT EXISTS MOVIE (" +
                    "MOVIE_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "MOVIE_NAME VARCHAR(255) NOT NULL," +
                    "GENRE VARCHAR(100)," +
                    "DURATION INTEGER NOT NULL," +
                    "DESCRIPTION VARCHAR(1000) NOT NULL," +
                    "RELEASE_DATE DATE," +
                    "RATING NUMERIC," +
                    "POSTER_URL VARCHAR(500) NOT NULL," +
                    "TRAILER_URL VARCHAR(500) NOT NULL" +
                    ")";
            statement.executeUpdate(movieTable);

            // Tạo bảng Room
            String roomTable = "CREATE TABLE IF NOT EXISTS ROOM (" +
                    "ROOM_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "CINEMA_ID INTEGER NOT NULL," +
                    "NAME VARCHAR(255) NOT NULL," +
                    "SEAT_LAYOUT VARCHAR(255) NOT NULL," +
                    "FOREIGN KEY (CINEMA_ID) REFERENCES CINEMA(CINEMA_ID)" +
                    ")";
            statement.executeUpdate(roomTable);

            // Tạo bảng Seat
            String seatTable = "CREATE TABLE IF NOT EXISTS SEAT (" +
                    "SEAT_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "ROOM_ID INTEGER NOT NULL," +
                    "ROW VARCHAR(50) NOT NULL," +
                    "SEAT_NUMBER INTEGER NOT NULL," +
                    "TYPE VARCHAR(50)," +
                    "STATUS VARCHAR(20),"+
                    "FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ROOM_ID)" +
                    ")";
            statement.executeUpdate(seatTable);

            // Tạo bảng Show
            String showTable = "CREATE TABLE IF NOT EXISTS SHOW (" +
                    "SHOW_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "MOVIE_ID INTEGER NOT NULL," +
                    "ROOM_ID INTEGER NOT NULL," +
                    "START_TIME TIMESTAMP NOT NULL," +
                    "END_TIME TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE(MOVIE_ID)," +
                    "FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ROOM_ID)" +
                    ")";
            statement.executeUpdate(showTable);

            // Tạo bảng Ticket
            String ticketTable = "CREATE TABLE IF NOT EXISTS TICKET (" +
                    "TICKET_ID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                    "BOOKING_ID INTEGER NOT NULL," +
                    "SEAT_ID INTEGER NOT NULL," +
                    "PRICE NUMERIC NOT NULL," +
                    "FOREIGN KEY (BOOKING_ID) REFERENCES BOOK_TICKET(BOOKING_ID)," +
                    "FOREIGN KEY (SEAT_ID) REFERENCES SEAT(SEAT_ID)" +
                    ")";
            statement.executeUpdate(ticketTable);

            System.out.println("Tables have been created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    public static void deleteTables()  {
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement()) {

            // Xóa các bảng 
            statement.executeUpdate("DROP TABLE IF EXISTS TICKET CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS SHOW CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS SEAT CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS ROOM CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS MOVIE CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS CINEMA CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS BILL CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS BOOK_TICKET CASCADE");
            statement.executeUpdate("DROP TABLE IF EXISTS ACCOUNT CASCADE");
        }catch(SQLException e) {
            e.printStackTrace(); 
        }
    }
    
   
    public static void main(String[] args) {
        createTables();
    }
}
