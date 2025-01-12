package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDAO;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/sensitive/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PaymentServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.html?error=Vui lòng đăng nhập để tiếp tục.");
            return;
        }
        
		String accountNumber = request.getParameter("accountNumber");
        String pin = request.getParameter("pin");
        String[] bookingDetails = (String[]) session.getAttribute("bookingDetails");
        double amount = Double.parseDouble(bookingDetails[4]);

        AccountDAO accountDAO = new AccountDAO();

        boolean isPaymentSuccessful = false;
		try {
			isPaymentSuccessful = accountDAO.processPayment(accountNumber, pin, amount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (isPaymentSuccessful) {
		    // Chuyển hướng tới trang thanh toán thành công
		    response.sendRedirect("paymentSuccess.jsp");
		} else {
		    // Quay lại trang đặt vé nếu thanh toán không thành công
		    request.setAttribute("errorMessage", "Thanh toán thất bại. Vui lòng kiểm tra thông tin và số dư.");
		    request.getRequestDispatcher("bookTicket.jsp").forward(request, response);
		}
        
	}

}
