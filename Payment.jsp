<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 60%;
            margin: 0 auto;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px;
            border-radius: 8px;
            margin-top: 50px;
        }

        header {
            text-align: center;
            margin-bottom: 20px;
        }

        header h2 {
            color: #333;
        }

        table {
            width: 100%;
            margin-bottom: 20px;
            border-spacing: 0 10px;
        }

        td {
            padding: 10px;
        }

        td:first-child {
            text-align: right;
            font-weight: bold;
            color: #555;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
            display: block;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .footer {
            margin-top: 20px;
            text-align: center;
        }

        .footer a {
            color: #007bff;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h2>Thanh toán</h2>
        </header>
        <form action="PaymentServlet" method="POST">
            <table>
                <tr>
                    <td>Số tài khoản:</td>
                    <td><input type="text" name="accountNumber" placeholder="Nhập số tài khoản" required></td>
                </tr>
                <tr>
                    <td>Mã PIN:</td>
                    <td><input type="password" name="pin" placeholder="Nhập mã PIN" required></td>
                </tr>
            </table>
            <input type="submit" value="Xác nhận thanh toán">
        </form>
        <div class="footer">
            <a href="home.jsp">Quay lại Trang chủ</a>
        </div>
    </div>
</body>
</html>
