package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import database.Data;
import model.Account;
import model.Cinema;
import model.Movie;
import model.Room;
import model.Seat;
import model.Ticket;


@WebServlet("/sensitive/BookTicketServlet")
public class BookTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        Account user = (session != null) ? (Account) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("data-login.html");
            return;
        }

        try {
            // Lấy danh sách rạp chiếu phim
            List<Cinema> cinemas = Data.getCinemas();
            
            List<Room> rooms = Data.getRooms();
            Movie m = (Movie) session.getAttribute("book");

            // Lưu danh sách vào request để truyền tới JSP
            request.setAttribute("cinemas", cinemas);
            request.setAttribute("rooms", rooms);
            request.setAttribute("movie", m);

            // Forward đến trang bookTicket.jsp
            request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi tải dữ liệu đặt vé: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Account user = (session != null) ? (Account) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("data-login.html");
            return;
        }

        String s = request.getParameter("quantity");
        if (s == null) s = "1";
        int quantity = Integer.parseInt(s);
        double price = 120000;

        Ticket ticket = new Ticket(Ticket.tID++, Seat.sID++, price * quantity);
        Data.tickets.add(ticket);

        response.sendRedirect("Payment.jsp");
    }
}
