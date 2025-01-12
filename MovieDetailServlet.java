package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Data;
import model.Movie;

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
		List<Movie> movie = Data.getMovies();
		for (Movie m : movie) {
			if (m.getMovieID() == movieID) {
				request.setAttribute("movie", m);
	            request.getRequestDispatcher("movieDetail.jsp").forward(request, response);
			}
		}

        response.sendRedirect("home.jsp"); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
