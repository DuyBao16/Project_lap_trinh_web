package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.Data;
import model.Account;

/**
 * Servlet implementation class LoginSecurity
 */
@WebServlet("/sensitive/j_security_check")
public class LoginSecurity extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginSecurity() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || password == null) {
			request.getRequestDispatcher("login-error.html").forward(request, response);
		}
		
		for (Account user : Data.getAccounts()) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password) && "user".equals(user.getRole())){
				session.setAttribute("user", user);
				response.sendRedirect("home.jsp");
				return;
			}
		}
		
	}

}
