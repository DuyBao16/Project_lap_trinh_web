<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="model.Movie" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt vé xem phim</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }

        header {
            background-color: #343a40;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        header h1 {
            margin: 0;
            font-size: 24px;
        }

        header a {
            color: white;
            text-decoration: none;
            margin-left: 15px;
        }

        .booking-container {
            max-width: 900px;
            margin: 40px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .movie-info {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .movie-poster {
            width: 200px;
            border-radius: 8px;
        }

        .movie-info h2 {
            margin: 0;
            font-size: 22px;
        }

        table {
            width: 100%;
            margin: 20px 0;
        }

        table td {
            padding: 10px;
            font-size: 16px;
        }

        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        footer {
            text-align: center;
            margin: 20px 0;
        }

        footer a {
            color: #007bff;
            text-decoration: none;
        }

        footer a:hover {
            text-decoration: underline;
        }
        
        .quantity-control {
    	display: inline-flex;
        align-items: center;
        }
        
    	.quantity-control button {
        width: 30px;
        height: 30px;
        font-size: 20px;
        cursor: pointer;
        }
        
        .quantity-control input[type="number"] {
        width: 50px;
        text-align: center;
        font-size: 16px;
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header>
        <h1>LetsWatch</h1>
        <div>
            Chào, ${user.username}
            | <a href="login.html">Đăng xuất</a>
        </div>
    </header>

    <!-- Booking Section -->
    <div class="booking-container">
        <div class="movie-info">
            <img src="${movie.posterURL}" alt="Poster của phim ${movie.movieName}" class="movie-poster">
            <div>
                <h2>${movie.movieName}</h2>
                <p>Giá vé cơ bản: <b>${movie.price} VND</b></p>
            </div>
        </div>

        <form action="BookTicketServlet" method="GET">
        <table>
        <tr>
            <td><label for="cinema">Chọn rạp:</label></td>
            <td><select name="cinema" id="cinema">
                <option value="">-- Chọn rạp --</option>
                <c:forEach var="cinema" items="${cinemas}">
                    <option value="${cinema}" ${cinema == param.cinema ? 'selected' : ''}>
                        ${cinema}
                    </option>
                </c:forEach>
            </select></td>
        </tr>
        <tr>
            <td><label for="room">Chọn phòng:</label></td>
            <td><select name="room" id="room">
                <option value="">-- Chọn phòng --</option>
                <c:forEach var="room" items="${rooms}">
                    <option value="${room}">${room}</option>
                </c:forEach>
            </select></td>
        </tr>
        
        <tr>
           	<td>Loại vé:</td>
           	<td>
            	<select name="ticketType">
                   	<option value="normal">Vé thường</option>
                	<option value="vip">Vé VIP</option>
           		</select>
       		</td>
        </tr>
        
        <tr>
           	<td>Số lượng vé:</td>
        	<td>
        		<input type="number" name="quantity" min="1" value="1">
        	</td>
       	</tr>
       	</table>
        </form>
        <form action="BookTicketServlet" method="POST">
            <button type="submit">Xác nhận đặt vé</button>
        </form>
    </div>

    <!-- Footer -->
    <footer>
        <a href="home.jsp">Quay lại trang chủ</a>
    </footer>
    
    <script>
        function increase() {
            const quantityInput = document.getElementById('quantity');
            quantityInput.value = parseInt(quantityInput.value) + 1;
        }

        function decrease() {
            const quantityInput = document.getElementById('quantity');
            if (parseInt(quantityInput.value) > 1) {
                quantityInput.value = parseInt(quantityInput.value) - 1;
            }
        }
    </script>
</body>
</html>
