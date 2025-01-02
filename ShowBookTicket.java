package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.CinemaDAO;
import database.MovieDAO;
import database.RoomDAO;
import database.SeatDAO;
import database.ShowDAO;
import model.Account;
import model.Cinema;
import model.Room;
import model.Seat;
import model.Show;

/**
 * Servlet implementation class BookTicketServlet
 */
@WebServlet("/showBookTicket")
public class ShowBookTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowBookTicket() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieID = Integer.parseInt(request.getParameter("id"));
		MovieDAO movieDAO = new MovieDAO();
		String movieName = movieDAO.getMovieNameByID(movieID);
		request.setAttribute("movieName", movieName);
		
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("user");
		String fullname = account.getFullName();
		String phone = account.getPhoneNumber();
		request.setAttribute("fullname", fullname);
		request.setAttribute("phone", phone);
		
		CinemaDAO cinemaDAO = new CinemaDAO();
		List<Cinema> listCinema = cinemaDAO.getCinemasByMovieId(movieID);
		request.setAttribute("listCinema", listCinema);
		
		RoomDAO roomDAO = new RoomDAO();
		List<Room> listRoom = roomDAO.getRoomsByMovieID(movieID);
		request.setAttribute("listRoom", listRoom);
		
		int roomID = Integer.parseInt(request.getParameter("roomID"));
		
		ShowDAO showDAO = new ShowDAO();
		List<Show> listShow = showDAO.getShowsByMovieAndRoom(movieID, roomID);
		request.setAttribute("listShow", listShow);
		
		SeatDAO seatDAO = new SeatDAO();
		List<Seat> listSeatAvailable = seatDAO.getAvailableSeats(roomID);
		List<Seat> listSeatBooked = seatDAO.getBookedSeats(roomID);
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
