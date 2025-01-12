package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.MovieBean;
import database.MovieDAO;

/**
 * Servlet implementation class MovieDetailServlet
 */
@WebServlet("/sensitive/MovieDetailServlet")
public class MovieDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MovieDetailServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("info");
		int movieID = Integer.parseInt(id);
		System.out.println(id);
        MovieDAO movieDAO = new MovieDAO();
        MovieBean movie = movieDAO.findMovieByMovieID(movieID);

        if (movie != null) {
            request.setAttribute("movie", movie);
            request.getRequestDispatcher("movieDetail.jsp").forward(request, response);
        } else {
            response.sendRedirect("adminPage.jsp"); 
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
