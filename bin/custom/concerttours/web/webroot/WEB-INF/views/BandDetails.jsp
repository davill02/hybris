<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<title>Band Details</title>
<body>
<h1>Band Details</h1>
Band Details for ${band.name}
<p>${band.description}</p>
<p>Music type:</p>
<ul>
    <c:forEach var="musicType" items="${band.genres}">
        <li>${musicType}</li>
    </c:forEach>
</ul>
<p>Tour History:</p>
<ul>
    <c:forEach var="tour" items="${band.tours}">
        <li><a href="../tour/${tour.id}">${tour.tourName}</a>(number of concerts: ${tour.numberOfConcerts})</li>
    </c:forEach>
</ul>
<a href="../bands">Back to Band List</a>
</body>
</html>