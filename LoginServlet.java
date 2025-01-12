package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.AccountBean;
import database.AccountDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sensitive/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		AccountDAO userDAO = new AccountDAO();
		try {			
			if (!userDAO.checkLogin(username)) {
				response.sendRedirect("login.html?error=Ten dang nhap khong ton tai");
			}
			
			AccountBean user = userDAO.findAccountByUsername(username);
			if (user != null && user.getPassword().equals(password)) {
				session.setAttribute("user", user);
				response.sendRedirect("home.jsp");
				if(userDAO.isAdmin(user.getUsername()))  {
					response.sendRedirect(request.getContextPath() + "/adminPage");
				}
			} else {
				response.sendRedirect("loginfailed.jsp?error=true");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
