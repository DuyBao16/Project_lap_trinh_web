package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDAO;
import model.Account;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sensitive/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AccountDAO accountDAO = new AccountDAO();
	 
	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Account user = accountDAO.getAccountByUsername(username);

        if (user != null && user.getPassword().equals(password) && "user".equals(user.getRole())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.sendRedirect("home.jsp");
        } else if (user != null && user.getPassword().equals(password) && "admin".equals(user.getRole())) {
        	HttpSession session = request.getSession(true);
            session.setAttribute("admin", user);
            response.sendRedirect("adminPage.jsp");
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.html").forward(request, response);
        }	
    }
}
