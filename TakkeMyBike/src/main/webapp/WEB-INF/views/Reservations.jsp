<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Moje Rezerwacje</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Moje Rezerwacje</h2>
    <div class="row">
        <div class="col-12">
            <div class="list-group">
                <c:forEach var="reservation" items="${reservations}">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col-md-3">
                                <img src="${pageContext.request.contextPath}/images/rower.jpg" alt="Bike Image" class="img-fluid"/>
                            </div>
                            <div class="col-md-6">
                                <h4><c:out value="${reservation.bike.title}"/></h4>
                                <p><c:out value="${reservation.bike.description}"/></p>
                                <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                <p><strong>Cena całkowita: </strong>
                                    <c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN
                                </p>
                                <c:choose>
                                    <c:when test="${reservation.status == 2}">
                                        <p class="text-success"><strong>Rezerwacja potwierdzona</strong></p>
                                    </c:when>
                                    <c:when test="${reservation.status == 1}">
                                        <p class="text-danger"><strong>Rezerwacja odrzucona</strong></p>
                                    </c:when>
                                    <c:when test="${reservation.status == 3}">
                                        <p class="text-danger"><strong>Rezerwacja anulowana przez klienta</strong></p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="text-warning"><strong>Rezerwacja oczekująca na potwierdzenie</strong></p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${owner==false && (reservation.status ==0 || reservation.status==2)}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <form action="<c:url value='/rent/cancel/${reservation.id}'/>" method="post">
                                        <button type="submit" class="btn btn-danger">Odwołaj</button>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${owner==true && reservation.status == 0}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <form action="<c:url value='/rent/accept/${reservation.id}'/>" method="post">
                                        <button type="submit" class="btn btn-success">Zaakceptuj</button>
                                    </form>
                                    <form action="<c:url value='/rent/deny/${reservation.id}'/>" method="post">
                                        <button type="submit" class="btn btn-danger">Odrzuć</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%@ include file="elements/footer.jsp" %>
</body>
</html>
