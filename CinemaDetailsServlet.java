package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import database.CinemaDAO;
import database.MovieDAO;
import model.Cinema;
import model.Movie;

/**
 * Servlet implementation class CinemaDetailsServlet
 */
@WebServlet("/cinemaDetails")
public class CinemaDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CinemaDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int cinemaId = Integer.parseInt(request.getParameter("id"));
		MovieDAO movieDAO = new MovieDAO();
		CinemaDAO cinemaDAO = new CinemaDAO();
		Cinema cinema = cinemaDAO.findCinemaByID(cinemaId);
		List<Movie> listMovie = movieDAO.findMovieByCinemaId(cinemaId);
		request.setAttribute("listMovie", listMovie);
		request.setAttribute("cinema", cinema);
		request.getRequestDispatcher("cinemaDetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
