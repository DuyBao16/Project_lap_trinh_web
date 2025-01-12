<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán thành công</title>
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
            margin: 50px auto;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            padding: 20px;
            border-radius: 8px;
            text-align: center;
        }

        header h2 {
            color: #333;
        }

        p {
            font-size: 16px;
            color: #555;
            margin: 20px 0;
        }

        .highlight {
            font-weight: bold;
            color: #007bff;
        }

        button {
            background-color: #007bff;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }

        button:hover {
            background-color: #0056b3;
        }

        .footer {
            margin-top: 20px;
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
            <h2>Thanh toán thành công</h2>
        </header>
        <p>Chúc mừng <span class="highlight">${SessionScope.user.username}</span>, bạn đã thanh toán thành công!</p>
        <p>Thời gian thanh toán: <span class="highlight"><%= new java.util.Date() %></span></p>
        <button onclick="window.location.href='home.jsp'">Về Trang chủ</button>
    </div>
</body>
</html>
