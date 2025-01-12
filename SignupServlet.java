package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.AccountBean;
import database.AccountDAO;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		// Thêm thông tin vào cơ sở dữ liệu
		AccountDAO userDAO = new AccountDAO();
		AccountBean account = new AccountBean(username, password, fullname, email, phone);
		boolean isSuccess;
		try {
			isSuccess = userDAO.addAccount(account);
			if (isSuccess) {
				// Chuyển hướng đến trang thành công
				response.sendRedirect("signupSuccess.jsp");
			} else {
				// Nếu lỗi, quay lại trang đăng ký với thông báo
				response.sendRedirect("signup.jsp?error=true");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
