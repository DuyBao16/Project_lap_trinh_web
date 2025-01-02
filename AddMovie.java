package servlet;

import java.io.File;
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
 * Servlet implementation class AddMovie
 */
@WebServlet("/addMovie")
@MultipartConfig
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovie() {
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
		    // Dùng File.separator để tương thích với hệ điều hành
		    String uploadPath = getServletContext().getRealPath("uploads/")  + posterFileName;
		    
		    // In ra đường dẫn để kiểm tra
		    System.out.println("Upload Path for poster: " + uploadPath);

		    // Lưu tệp vào thư mục uploads
		    posterPart.write(uploadPath);
		    posterURL = "uploads/" + posterFileName;  // Lưu đường dẫn ảnh
		}

		// Xử lý trailer
		Part trailerPart = request.getPart("trailerFile");
		String trailerURL = null;
		if (trailerPart != null && trailerPart.getSize() > 0) {
		    String trailerFileName = trailerPart.getSubmittedFileName();
		    // Dùng File.separator để tương thích với hệ điều hành
		    String uploadPath = getServletContext().getRealPath("uploads/")  + trailerFileName;
		    
		    // In ra đường dẫn để kiểm tra
		    System.out.println("Upload Path for trailer: " + uploadPath);

		    // Lưu tệp vào thư mục uploads
		    trailerPart.write(uploadPath);
		    trailerURL = "uploads/" + trailerFileName;  // Lưu đường dẫn video
		}

        Movie movie = new Movie(movieName, genre, duration, description, releaseDate, rating, posterURL, trailerURL);
        
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.addMovie(movie);
        
        request.getRequestDispatcher("adminPage").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
