<%@ page import="java.util.List" %>
<%@ page import="org.springframework.ui.Model" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Rowerów</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Lista Rowerów</h2>
    <div class="row">
        <div class="col-12">
            <div class="list-group">
                <c:forEach var="bike" items="${bike}">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col-md-3">
                                <img src="${pageContext.request.contextPath}/images/rower.jpg" alt="Bike Image" class="img-fluid"/>
                            </div>
                            <div class="col-md-6">
                                <h4><c:out value="${bike.title}"/></h4>
                                <p><c:out value="${bike.description}"/></p>
                                <p><strong>Cena za dzień: </strong><c:out value="${bike.pricePerDay}"/> PLN</p>
                                <p><strong>Minimalny czas wynajmu: </strong><c:out value="${bike.minRentDays}"/> dni</p>
                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${bike.owner.id}'/>"><c:out value="${bike.owner.login}"/></a></p>
                                <a href="<c:url value='/bike/details/${bike.id}'/>" class="btn btn-primary">Szczegóły</a>
                                <div>
                                    <strong>Zarezerwowany w dniach:</strong>
                                    <ul>
                                        <c:forEach var="rent" items="${rents}">
                                            <c:if test="${rent.bike.id==bike.id && rent.status==2}">
                                                <li>Od: <c:out value="${rent.startDate}"/> Do: <c:out value="${rent.endDate}"/></li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <c:if test="${bike.owner.id==id}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <a href="<c:url value='/bike/manage/${bike.id}'/>" class="btn btn-success">Zarządzaj</a>
                                </div>
                            </c:if>
                            <c:if test="${bike.owner.id!=id}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <a href="<c:url value='/rent/bike/${bike.id}'/>" class="btn btn-success">Wypożycz</a>
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
