package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Data;
import model.Account;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/sensitive/SignupServlet")
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
		String accountNumber = request.getParameter("accountNumber");
		String pin = request.getParameter("pin");

		// Thêm thông tin vào cơ sở dữ liệu
		if (username == null || password == null || fullname == null) {
			request.getRequestDispatcher("signup.jsp?error=Khong duoc dien thieu thong tin").forward(request, response);
		}
		
		Account newAcc = new Account(username, password, fullname, email, phone, "user");
		newAcc.setRole("user"); newAcc.setAccountNumber(accountNumber); newAcc.setPinNumber(pin); newAcc.setBalance(100000);
		Data.getAccounts().add(newAcc);
		response.sendRedirect("signupSuccess.jsp");
	}
}
