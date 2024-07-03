<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wypożycz Rower</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Wypożycz Rower</h2>
    <div class="row">
        <div class="col-md-4">
            <c:if test="${not empty bike.base64Image}">
                <img src="data:image/jpeg;base64,${bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" />
            </c:if>
        </div>
        <div class="col-md-8">
            <h4><c:out value="${bike.title}"/></h4>
            <p><c:out value="${bike.description}"/></p>
            <p><strong>Cena za dzień: </strong><c:out value="${bike.pricePerDay}"/> PLN</p>
            <p><strong>Minimalny czas wynajmu: </strong><c:out value="${bike.minRentDays}"/> dni</p>
        </div>
    </div>
    <form:form action="/rent" modelAttribute="rent" method="post">
        <div class="form-group">
            <label for="startDate">Data od:</label>
            <form:input path="startDate" type="date" class="form-control" id="startDate" required="true"/>
        </div>
        <div class="form-group">
            <label for="endDate">Data do:</label>
            <form:input path="endDate" type="date" class="form-control" id="endDate" required="true"/>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </c:if>

        <input type="hidden" name="bikeId" value="${bike.id}"/>
        <button type="submit" class="btn btn-success mt-3">Wypożycz</button>
    </form:form>
    <div class="mt-3">
        <h4>Niedostępny:</h4>
        <ul>
            <c:forEach var="rent" items="${rents}">
                <c:if test="${rent.status==2}">
                    <li>Od: <c:out value="${rent.startDate}"/> Do: <c:out value="${rent.endDate}"/></li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>

<%@ include file="elements/footer.jsp" %>
</body>
</html>
