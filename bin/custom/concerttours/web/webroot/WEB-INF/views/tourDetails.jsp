<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html>
    <head>
        <title>Tour Details</title>
    </head>
    <body>
        <h1>Tour Details</h1>
        <p>Tour Details for ${tour.name}</p>
        <p>${tour.description}</p>
        <p>Schedule:</p>
        <table>
            <tr><th>Venue</th><th></th><th>Date</th><th>Days left</th></tr>
            <c:forEach var="concert" items="${tour.concerts}">
                <tr><td>${concert.venue}</td><td>${concert.type}</td><td><fmt:formatDate pattern="dd MMM yyyy" value="${concert.date}" /></td><td>
                    <c:choose>
                        <c:when test="${empty concert.daysLeft}">
                            No info
                        </c:when>
                        <c:when test="${concert.daysLeft eq 0}">
                            Passed
                        </c:when>
                        <c:otherwise>
                            ${concert.daysLeft}
                        </c:otherwise>
                    </c:choose>
                </td></tr>
            </c:forEach>
        </table>
        <a href="../bands">Back to Band List</a>
    </body>
</html>