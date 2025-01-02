package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import database.MovieDAO;
import model.Movie;

/**
 * Servlet implementation class EditMovieServlet
 */
@WebServlet("/editMovie")
@MultipartConfig
public class EditMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 request.setCharacterEncoding("UTF-8");
	     response.setContentType("text/html;charset=UTF-8");
	     
	     MovieDAO movieDAO = new MovieDAO();
		
		int movieId = Integer.parseInt(request.getParameter("movieId"));
		
		Movie oldMovie = movieDAO.findMovieByMovieID(movieId);
		String movieName = request.getParameter("movieName");
		String genre = request.getParameter("genre");
		int duration = Integer.parseInt(request.getParameter("duration"));
		String description = request.getParameter("description");
		
		LocalDate releaseDate = LocalDate.parse(request.getParameter("releaseDate"));
		Double rating = Double.parseDouble(request.getParameter("rating"));
		 // Xử lý ảnh poster
        Part posterPart = request.getPart("posterFile");
        String posterURL = null;
        if (posterPart != null && posterPart.getSize() > 0) {
            String posterFileName = posterPart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("uploads/") + posterFileName;
            posterPart.write(uploadPath);
            posterURL = "uploads/" + posterFileName;  // Lưu đường dẫn ảnh
        }else posterURL =oldMovie.getPosterURL();

        // Xử lý trailer 
        Part trailerPart = request.getPart("trailerFile");
        String trailerURL = null;
        if (trailerPart != null && trailerPart.getSize() > 0) {
            String trailerFileName = trailerPart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("uploads/") + trailerFileName;
            trailerPart.write(uploadPath);
            trailerURL = "uploads/" + trailerFileName;  // Lưu đường dẫn video
        }else trailerURL = oldMovie.getTrailerURL();
        Movie movie = new Movie(movieId, movieName, genre, duration, description, releaseDate, rating, posterURL, trailerURL);
        
        
        movieDAO.updateMovieByID(movie);
        
        response.sendRedirect("movieDetails?id=" + movieId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
