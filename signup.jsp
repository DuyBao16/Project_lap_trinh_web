<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .signup-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        table {
            width: 100%;
        }
        td {
            padding: 10px 0;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"] {
            width: 100%;
            padding: 8px;
            margin: 4px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="signup-container">
        <h2>Đăng ký tài khoản</h2>
        <form action="SignupServlet" method="GET">
            <table>
                <tr>
                    <td><label for="username">Tên đăng nhập: (*)</label></td>
                    <td><input type="text" name="username" id="username" required></td>
                </tr>
                <tr>
                    <td><label for="password">Mật khẩu: (*)</label></td>
                    <td><input type="password" name="password" id="password" required></td>
                </tr>
                <tr>
                    <td><label for="fullname">Họ tên: (*)</label></td>
                    <td><input type="text" name="fullname" id="fullname" required></td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td><input type="email" name="email" id="email"></td>
                </tr>
                <tr>
                    <td><label for="phone">Số điện thoại:</label></td>
                    <td><input type="text" name="phone" id="phone"></td>
                </tr>
                <tr>
                    <td><label for="phone">Số tài khoản:</label></td>
                    <td><input type="text" name="accountNumber" id="accountNumber"></td>
                </tr>
                <tr>
                    <td><label for="phone">Số PIN:</label></td>
                    <td><input type="text" name="pin" id="pin"></td>
                </tr>
            </table>
            <input type="submit" value="Đăng ký">
        </form>
    </div>
</body>
</html>
