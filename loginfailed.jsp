<!DOCTYPE html>
<html lang="vi">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body, h1, p, form, table {
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            display: flex;
            background-color: #ffffff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 8px;
            width: 600px;
        }

        /* Pháº§n chá»©a logo */
        .logo-section {
            margin-right: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .logo-section img {
            width: 150px;
            height: auto;
        }

        .form-section {
            flex-grow: 1;
        }

        .form-section label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-section input[type="text"],
        .form-section input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .form-section input[type="submit"] {
            width: 100px;
            padding: 10px;
            margin-right: 10px;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
        }

        .form-section input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="logo-section">
            <img src="logo.png" alt="Logo">
        </div>

        <div class="form-section">
            <form action="LoginServlet" method="GET">
                <h2>Vui lòng đăng nhập</h2>
                
                <c:if test="${param.error == 'true'}">
                    <p style="color: red;">Tên đăng nhập hoặc mật khẩu không đúng!</p>
                </c:if>

                <label for="username">Tên đăng nhập:</label>
                <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập">

                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" placeholder="Nhập mật khẩu">

                <div>
                    <input type="submit" value="Đăng nhập" name="signIn">
                    <input type="submit" value="Đăng ký" name="signUp">
                </div>
            </form>
        </div>
    </div>
</body>
</html>
