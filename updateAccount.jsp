<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật tài khoản</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        /* Container styling */
        .update-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        /* Header styling */
        .update-header {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        table td {
            padding: 10px;
        }

        table td:first-child {
            font-weight: bold;
            width: 150px;
        }

        table input[type="text"], table input[type="password"], table input[type="email"] {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        /* Button styling */
        .update-button {
            display: block;
            width: 200px;
            margin: 20px auto 0;
            padding: 10px;
            background-color: #28a745;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            border: none;
            transition: background-color 0.3s;
        }

        .update-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="update-container">
        <!-- Tiêu đề cập nhật thông tin tài khoản -->
        <h2 class="update-header">Cập nhật tài khoản</h2>

        <!-- Form cập nhật thông tin -->
        <form action="UpdateAccountServlet" method="POST">
            <table>
                <tr>
                    <td>Tên đăng nhập:</td>
                    <td>
                        <input type="text" name="username" value="${user.username}" readonly>
                    </td>
                </tr>
                <tr>
                    <td>Mật khẩu:</td>
                    <td>
                        <input type="password" name="password" value="${user.password}">
                    </td>
                </tr>
                <tr>
                    <td>Họ tên:</td>
                    <td>
                        <input type="text" name="fullName" value="${user.fullName}">
                    </td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td>
                        <input type="email" name="email" value="${user.email}">
                    </td>
                </tr>
                <tr>
                    <td>Số điện thoại:</td>
                    <td>
                        <input type="text" name="phone" value="${user.phone}">
                    </td>
                </tr>
            </table>
            
            <!-- Nút cập nhật thông tin -->
            <input type="submit" class="update-button" value="Cập nhật">
        </form>
    </div>
</body>
</html>
