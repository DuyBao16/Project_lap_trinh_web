<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" />
<fmt:setLocale value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - LetsWatch</title>
    <!-- Import CSS và JS cho Slider -->
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.css"/>
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick-theme.min.css"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.8.1/slick.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        /* Header styling */
        .header {
            background-color: #007bff;
            color: white;
            padding: 15px 20px;
            text-align: center;
            position: relative;
        }

        /* User info and Logout button */
        .user-info {
            position: absolute;
            right: 20px;
            top: 15px;
            display: flex;
            align-items: center;
        }

        .user-info span {
            margin-right: 10px;
        }

        .user-info a {
            color: white;
            text-decoration: none;
        }

        /* Menu button */
        .menu-container {
            position: absolute;
            left: 20px;
            top: 15px;
        }

        /* Dropdown Menu */
        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .dropdown:hover .dropdown-content {
            display: block;
        }
        
         /* Slider container styling */
        .slider-container {
            width: 80%;
            margin: auto;
            position: relative;
            padding: 20px 0;
        }
        .movie-card {
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 10px;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        .movie-card img {
            width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .movie-info {
            margin-top: 10px;
        }
        .movie-info h4 {
            font-size: 18px;
            margin: 5px 0;
        }
        .movie-info p {
            font-size: 16px;
            color: #555;
        }
        .btn-container {
            margin-top: 10px;
        }
        .btn-container button {
            background-color: #ff6b6b;
            border: none;
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px;
        }
        .btn-container button:hover {
            background-color: #ff3b3b;
        }
        .lang-select {
            margin-top: 10px;
            font-size: 14px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
	
    <!-- Header with LetsWatch title -->
    <div class="header">
        <div class="menu-container">
            <!-- Menu Button with Dropdown -->
            <div class="dropdown">
                <button>Menu</button>
                <div class="dropdown-content">
                    <a href="accountInfo.jsp">Tài khoản</a>
                    <a href="updateAccount.jsp">Sửa thông tin</a>
                </div>
            </div>
        </div>

        <h2>LetsWatch</h2>

        <!-- User info and Logout -->
        <div class="user-info">
            <span>Chào, ${user.username}</span> | 
            <form action="HomeServlet" method="POST" style="display:inline;">
    			<input type="hidden" name="action" value="logout">
    			<button type="submit" style="background:none;border:none;color:white;cursor:pointer;" name="logout">
        			Đăng xuất
    			</button>
			</form>
			<form method="GET" action="changeLanguage">
				<label for="language"> <fmt:message key="language" />
				</label> <select id="language" name="lang" onchange="this.form.submit()">
					<option value="vi" ${sessionScope.lang == 'vi' ? 'selected' : ''}>Tiếng Việt</option>
					<option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>
				</select>
			</form>
        </div>
    </div>
    
    <!-- Slider -->
    <div class="slider-container">
        <div class="slider">
        	<c:forEach begin="1" end="5">
                <div class="movie-card">
                    <img src="file:///D:/WebProgramming/Project/src/main/webapp/uploads/bogia.jpg" alt="bogia" />
                    <div class="movie-info">
                        <h4>Bố già</h4>
                        <p>Giá: 120000 VND</p>
                    </div>
                    <div class="btn-container">
                        <form action="BookTicketServlet" method="GET">
                            <button type="submit" name="book" value="1">Đặt vé</button>
                        </form>
                        <form action="MovieDetailServlet" method="GET">
                            <button type="submit" name="info" value="1">Xem thông tin</button>
                        </form>
                    </div>
                </div>
        	</c:forEach>   
        </div>
    </div>
    	
    <script>
    $(document).ready(function() {
        $('.slider').slick({
            slidesToShow: 4,
            slidesToScroll: 1,
            arrows: true,
            infinite: true,
            prevArrow: '<button type="button" class="slick-prev">&larr;</button>',
            nextArrow: '<button type="button" class="slick-next">&rarr;</button>',
            responsive: [
                {
                    breakpoint: 768,
                    settings: {
                        slidesToShow: 2
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: 1
                    }
                }
            ]
        });
    });
   	</script>
</body>
</html>
