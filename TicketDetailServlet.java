package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.TicketDAO;

/**
 * Servlet implementation class TicketDetailServlet
 */
@WebServlet("/ticketDetail")
public class TicketDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int ticketId = Integer.parseInt(request.getParameter("id"));
		TicketDAO ticket = new TicketDAO();
		double price = ticket.getPriceByTicketId(ticketId);
		String movieName = ticket.getMovieNameByTicketID(ticketId);
		String cinemaName = ticket.getNameCinemaByTicketID(ticketId);
		String addressCinema = ticket.getCinemaDetailsByTicketID(ticketId);
		String roomName =ticket.getRoomNameByTicketID(ticketId);
		int seatNum = ticket.getSeatNumberByTicketID(ticketId);
		String seatRow =ticket.getSeatRowByTicketID(ticketId);
		
		request.setAttribute("movieName", movieName);
		request.setAttribute("addressCinema", addressCinema);
		request.setAttribute("roomName", roomName);
		request.setAttribute("seatNumber", seatNum);
		request.setAttribute("seatRow", seatRow);
		request.setAttribute("price", price);
		request.setAttribute("cinemaName", cinemaName);
		
		request.getRequestDispatcher("ticketDetail.jsp").forward(request, response);


		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
