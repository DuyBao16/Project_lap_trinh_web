<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin tài khoản</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        /* Container for account info */
        .account-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        /* Header styling */
        .account-header {
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
            border-bottom: 1px solid #ddd;
        }

        table td:first-child {
            font-weight: bold;
            width: 150px;
        }

        table td:last-child {
            text-align: left;
        }

        /* Button styling */
        .home-button {
            display: block;
            width: 200px;
            margin: 30px auto 0;
            padding: 10px;
            background-color: #007bff;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .home-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="account-container">
        <!-- Tiêu đề thông tin tài khoản -->
        <h2 class="account-header">Thông tin tài khoản của ${user.username}</h2>
        <form action="UpdateAccountServlet" method="POST">

        <!-- Bảng hiển thị thông tin tài khoản -->
        <table>
            <tr>
                <td>Tên đăng nhập:</td>
                <td><b>${user.username}</b></td>
            </tr>
            <tr>
                <td>Mật khẩu:</td>
                <td>${user.password}</td>
            </tr>
            <tr>
                <td>Họ tên:</td>
                <td>${user.fullName}</td>
            </tr>
            <tr>
                <td>Email:</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td>Số điện thoại:</td>
                <td>${user.phoneNumber}</td>
            </tr>
            <tr>
                <td>Số dư tài khoản:</td>
                <td>${user.balance}</td>
            </tr>
        </table>
        </form>

        <!-- Nút về trang chủ -->
        <a href="home.jsp" class="home-button">Về trang chủ</a>
    </div>
</body>
</html>
