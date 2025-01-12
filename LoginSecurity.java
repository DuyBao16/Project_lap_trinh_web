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
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		AccountDAO userDAO = new AccountDAO();
		try {
			if (!userDAO.checkLogin(username)) {
				response.sendRedirect("signup.jsp");
			}
			AccountBean user = userDAO.findAccountByUsername(username);
			if (user != null && user.getPassword().equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				if(userDAO.isAdmin(user.getUsername()))  {
					response.sendRedirect(request.getContextPath() + "/adminPage");
				} else {
					response.sendRedirect("homePage.jsp");
				}
			} else {
				response.sendRedirect("logig-error.html");
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
