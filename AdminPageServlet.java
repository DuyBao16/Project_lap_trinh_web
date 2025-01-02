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
import database.TicketDAO;
import model.Account;
import model.Cinema;
import model.Movie;
import model.Ticket;

/**
 * Servlet implementation class AdminPageServlet
 */
@WebServlet("/adminPage")
public class AdminPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TicketDAO ticketDAO;
    private MovieDAO movieDAO;
    private CinemaDAO cinemaDAO;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPageServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        // Khởi tạo đối tượng TicketDAO và thiết lập kết nối trong phương thức init
        ticketDAO = new TicketDAO();
        movieDAO = new MovieDAO();
        cinemaDAO = new CinemaDAO();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        	
    		HttpSession session = request.getSession();
    		Account account = (Account) session.getAttribute("user");
           
    	// Lấy danh sách vé từ TicketDAO
            List<Ticket> listTicket = ticketDAO.getAllTickets();
            List<Cinema> listCinema = cinemaDAO.getAllCinemas();
            List<Movie> listMovie = movieDAO.getAllMovies();
            // Đưa dữ liệu vào request và chuyển tiếp đến trang adminPage.jsp
            
            String showAllMovies = request.getParameter("showAllMovies");
            String showAllCinemas = request.getParameter("showAllCinemas");
            String showAllTickets = request.getParameter("showAllTickets");

            if (!"true".equals(showAllMovies) && listMovie.size() > 5) {
                listMovie = listMovie.subList(0, 5);
            }
            if (!"true".equals(showAllCinemas) && listCinema.size() > 5) {
                listCinema = listCinema.subList(0, 5);
            }
            if (!"true".equals(showAllTickets) && listTicket.size() > 5) {
                listTicket = listTicket.subList(0, 5);
            }
            
            request.setAttribute("listTicket", listTicket);
            request.setAttribute("listCinema", listCinema);
            request.setAttribute("listMovie", listMovie);
            request.setAttribute("user", account);
            request.getRequestDispatcher("adminPage.jsp").forward(request, response);
      
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
