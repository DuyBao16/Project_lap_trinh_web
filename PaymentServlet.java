package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDAO;
import database.Data;
import model.Account;

/**
 * Servlet implementation class PaymentServlet
 */
@WebServlet("/sensitive/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private AccountDAO accountDAO = new AccountDAO();
    public PaymentServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("data-login.html");
            return;
        }

        String accountNumber = request.getParameter("accountNumber");
        String pin = request.getParameter("pin");
        double totalAmount = Data.getTickets().get(Data.getTickets().size() - 1).getPrice();

        try {
            if (accountDAO.validateTransaction(user, accountNumber, pin, totalAmount)) {
                accountDAO.updateBalance(user, user.getBalance() - Math.abs(totalAmount)); // Trừ số tiền
                response.sendRedirect("paymentSuccess.jsp");
            } else {
                throw new Exception("Giao dịch không hợp lệ!");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Thanh toán thất bại: " + e.getMessage());
            request.getRequestDispatcher("Payment.jsp").forward(request, response);
        }
    }
}
