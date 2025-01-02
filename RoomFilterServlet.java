package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.RoomDAO;
import database.SeatDAO;
import model.Room;

/**
 * Servlet implementation class RoomFilterServlet
 */
@WebServlet("/roomFilter")
public class RoomFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomFilterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieId = Integer.parseInt(request.getParameter("movieID"));
		RoomDAO roomDAO = new RoomDAO();
		SeatDAO seatDAO = new SeatDAO();
		List<Room> listRoom =null;
		listRoom = roomDAO.getRoomsByMovieID(movieId);
		Map<Integer, Integer> seatAvaiable = new HashMap<>();
		for(Room room : listRoom) {
			int roomId = room.getRoomID();
			int seat = seatDAO.getAvailableSeatsCount(roomId);
			seatAvaiable.put(roomId, seat);
		}
		request.setAttribute("listRoom", listRoom);
		request.setAttribute("seatAvaiable",seatAvaiable );
		request.getRequestDispatcher("roomDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
