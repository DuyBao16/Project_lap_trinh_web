package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Data;
import model.Movie;



/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/sensitive/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Lấy đối tượng session của người dùng
        HttpSession session = request.getSession(false); // false để không tạo mới session nếu chưa có

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (session == null || session.getAttribute("user") == null) {
            // Nếu chưa đăng nhập, chuyển hướng về trang login.html
            response.sendRedirect("login.html");
            return;
        }
        
        List<Movie> movies = Data.getMovies();
        session.setAttribute("movieList", movies);
        request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        if ("logout".equals(action)) {
            // Xử lý đăng xuất
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Xóa session hiện tại
            }
            // Chuyển hướng về trang login sau khi đăng xuất
            response.sendRedirect("login.html");
        }
	}

}
