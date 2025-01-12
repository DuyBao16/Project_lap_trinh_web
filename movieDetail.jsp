<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" />
<fmt:setLocale value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Movie Details</title>
<style>
body {
    font-family: Arial, sans-serif;
    line-height: 1.6;
    margin: 20px;
    background-color: #f4f4f9;
}

.movie-details {
    max-width: 600px;
    margin: auto;
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.movie-poster {
    width: 100%;
    height: auto;
    border-radius: 8px;
}

h1 {
    color: #333;
}

.movie-info {
    margin-top: 20px;
}

.btn-back,
.btn-edit,
.btn-delete {
    display: inline-block;
    margin-top: 20px;
    padding: 10px 20px;
    background: #007bff;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    transition: background 0.3s ease;
    text-align: center;
    cursor: pointer;
}

.btn-back:hover,
.btn-edit:hover,
.btn-delete:hover {
    background: #0056b3;
}

.btn-delete {
    background: #dc3545;
}

.btn-delete:hover {
    background: #a71d2a;
}

.buttons {
    display: flex;
    justify-content: space-between;
    gap: 10px;
    margin-top: 20px;
}
form {
    display: inline-block;
    margin: 0;
}
.btn-back-container {
    display: flex;
    justify-content: center; /* Căn giữa theo chiều ngang */
    margin-top: 20px; /* Khoảng cách phía trên */
}

</style>
</head>
<body>
<form action="MovieDetailServlet" method="GET">
	<div class="movie-details">
		<c:if test="${movie != null}">
			<!-- Movie Poster -->
			<img src="${movie.getPosterURL()}" alt="${movie.getMovieName()}" class="movie-poster">

			<!-- Movie Details -->
			<h1>${movie.movieName}</h1>
			<div class="movie-info">
				<p>
					<strong><fmt:message key="genre"/></strong> ${movie.genre}
				</p>
				<p>
					<strong><fmt:message key="duration"/></strong> ${movie.duration} minutes
				</p>
				<p>
					<strong><fmt:message key="description"/></strong> ${movie.description}
				</p>
				<p>
					<strong><fmt:message key="release_date"/></strong>
					<c:out value="${movie.releaseDate.day}" />
					/
					<c:out value="${movie.releaseDate.month}" />
					/
					<c:out value="${movie.releaseDate.year}" />
				</p>
				<c:if test="${movie.rating != null}">
					<p>
						<strong><fmt:message key="rating"/></strong> ${movie.rating}/5
					</p>
				</c:if>
				<c:if test="${not empty movie.trailerURL}">
					<p>
						<a href="${movie.trailerURL}" target="_blank"><fmt:message key="watch_trailer"/></a>
					</p>
				</c:if>
			</div>
		</c:if>

		<!-- Back Button -->
		<div class="btn-back-container">
			<a href="home.jsp" class="btn-back"><fmt:message key="back_to_homepage"/></a>
		</div>
	</div>
</form>
</body>
</html>
