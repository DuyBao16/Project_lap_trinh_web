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

@WebServlet("/sensitive/UpdateAccountServlet")
public class UpdateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Phương thức xử lý yêu cầu POST từ form
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy thông tin từ form cập nhật
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Lấy đối tượng người dùng hiện tại từ session
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("user");

        if (currentUser != null) {
            // Cập nhật thông tin người dùng hiện tại
            currentUser.setUsername(username);
            currentUser.setPassword(password);
            currentUser.setFullName(fullName);
            currentUser.setEmail(email);
            currentUser.setPhoneNumber(phone);
            


            // Sử dụng UserDAO để cập nhật thông tin vào database
            AccountDAO userDAO = new AccountDAO();
            
            boolean isUpdated = userDAO.updateAccount(currentUser);

            if (isUpdated) {
                // Nếu cập nhật thành công, cập nhật thông tin trong session
                session.setAttribute("user", currentUser);

                // Chuyển hướng về trang thông tin tài khoản hoặc trang chủ
                response.sendRedirect("homepage.jsp");
            } else {
                // Nếu cập nhật thất bại, chuyển hướng về trang cập nhật với thông báo lỗi
                request.setAttribute("errorMessage", "Cập nhật thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("updateAccount.jsp").forward(request, response);
            }
        } else {
            // Nếu không có người dùng trong session, chuyển về trang đăng nhập
            response.sendRedirect("login.html");
        }
    }
}
