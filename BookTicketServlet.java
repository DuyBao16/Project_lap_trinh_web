package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import beans.AccountBean;
import beans.MovieBean;
import database.CinemaDAO;
import database.MovieDAO;
import database.RoomDAO;
import model.Movie;


@WebServlet("/sensitive/BookTicketServlet")
public class BookTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        AccountBean user = (session != null) ? (AccountBean) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("data-login.html");
            return;
        }

        Movie movie = (Movie) request.getAttribute("selectedMovie");
        if (movie == null) {
            response.sendRedirect("home.jsp");
            return;
        }
        
        // Lấy danh sách rạp
		CinemaDAO cinemaDAO = new CinemaDAO();
		List<String> cinemas = cinemaDAO.getCinemaNames();
		request.setAttribute("cinemas", cinemas);

		// Nếu có rạp được chọn, lấy danh sách phòng tương ứng
		String selectedCinema = request.getParameter("cinema");
		if (selectedCinema != null) {
		    RoomDAO roomDAO = new RoomDAO();
		    List<String> rooms = roomDAO.getRoomsByCinema(selectedCinema);
		    request.setAttribute("rooms", rooms);
		}

        // Chuyển đến trang bookTicket.jsp
        request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        AccountBean user = (session != null) ? (AccountBean) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("data-login.html");
            return;
        }

        // Lấy thông tin từ form
        String cinema = request.getParameter("cinema");
        String room = request.getParameter("room");
        String seatType = request.getParameter("seatType");
        String quantityStr = request.getParameter("quantity");
        String movieIdStr = request.getParameter("movieId");
        
        if (cinema == null || room == null || seatType == null || quantityStr == null || movieIdStr == null) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin đặt vé.");
            request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
            return;
        }
        
        try {
            int quantity = Integer.parseInt(quantityStr);

            if (quantity <= 0 || quantity > 20) {
                request.setAttribute("error", "Số lượng vé phải từ 1 đến 20.");
                request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
                return;
            }

            // Lưu thông tin đặt vé vào session tạm thời
            MovieDAO dao = new MovieDAO();
            MovieBean movie = dao.findMovieByMovieID(Integer.parseInt(movieIdStr));
            session.setAttribute("bookingDetails", new String[] {cinema, room, seatType, quantityStr, movieIdStr, "" + movie.getPrice()});

            // Chuyển hướng sang trang thanh toán
            response.sendRedirect("payment.jsp");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
        }
    }
}
