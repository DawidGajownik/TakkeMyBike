<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:if test="${mydashboard!=null}">
            Zarządzaj wynajmem
        </c:if>
        <c:if test="${mydashboard==null}">
            Moje Rezerwacje
        </c:if></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .lower-div {
            position: absolute;
            bottom: 0;
            width: 100%; /* zajmuje całą szerokość kontenera */
        }
        .wide-div {
            width: 100%;
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2><c:if test="${mydashboard!=null}">
        Zarządzaj wynajmem
    </c:if>
        <c:if test="${mydashboard==null}">
            Moje Rezerwacje
        </c:if></h2>

    <!-- Zakładki -->
    <ul class="nav nav-tabs" id="reservationTabs" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="all-tab" data-toggle="tab" href="#all" role="tab" aria-controls="all" aria-selected="true">Wszystkie</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="confirmed-tab" data-toggle="tab" href="#confirmed" role="tab" aria-controls="confirmed" aria-selected="false">Potwierdzone</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="ongoing-tab" data-toggle="tab" href="#ongoing" role="tab" aria-controls="ongoing" aria-selected="false">Trwające</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="pending-tab" data-toggle="tab" href="#pending" role="tab" aria-controls="pending" aria-selected="false">Oczekujące</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="rejected-tab" data-toggle="tab" href="#rejected" role="tab" aria-controls="rejected" aria-selected="false">Odrzucone</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="cancelled-tab" data-toggle="tab" href="#cancelled" role="tab" aria-controls="cancelled" aria-selected="false">Anulowane przez klienta</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="archived-tab" data-toggle="tab" href="#archived" role="tab" aria-controls="archived" aria-selected="false">Archiwalne</a>
        </li>
    </ul>

    <div class="tab-content" id="reservationTabsContent">
        <!-- Wszystkie -->
        <div class="tab-pane fade show active" id="all" role="tabpanel" aria-labelledby="all-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations}">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col-md-3">
                                <c:if test="${not empty reservation.getKey().bike.base64Image}">
                                    <a href="/bike/details/${reservation.getKey().bike.id}"><img src="data:image/jpeg;base64,${reservation.getKey().bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                </c:if>
                            </div>
                            <div class="col-md-5">
                                <h4><a href="/bike/details/${reservation.getKey().bike.id}">${reservation.getKey().bike.title}</a></h4>
                                <p><c:out value="${reservation.getKey().bike.description}"/></p>
                                <p><strong>Data od: </strong><c:out value="${reservation.getKey().startDate}"/></p>
                                <p><strong>Data do: </strong><c:out value="${reservation.getKey().endDate}"/></p>
                                <p><strong>Cena za dzień: </strong><c:out value="${reservation.getKey().bike.pricePerDay}"/> PLN</p>
                                <p><strong>Cena całkowita: </strong><c:out value="${reservation.getKey().bike.pricePerDay * reservation.getKey().duration}"/> PLN</p>
                            </div>
                            <div class="col-md-4">
                                <div class="text-center mt-3">
                                    <c:choose>
                                        <c:when test="${reservation.getKey().bike.owner.id != id}">
                                            <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.getKey().bike.owner.id}'/>"><c:out value="${reservation.getKey().bike.owner.login}"/></a></p>
                                            <a href="/message/user/${reservation.getKey().bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            <c:if test="${reservation.getValue() == null && reservation.getKey().status == 2 && reservation.getKey().endDate.isBefore(now)}">
                                                <p><a href="<c:url value='/rate/${reservation.getKey().id}'/>">Oceń</a></p>
                                            </c:if>
                                            <c:if test="${reservation.getValue() != null}">
                                                <div class="mt-2">
                                                    <p><strong>Twoja ocena: ${reservation.getValue().rating}/5</strong></p>
                                                    <p style="font-size: smaller; font-style: italic; color: gray;">
                                                            ${reservation.getValue().comment}
                                                    </p>
                                                </div>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${reservation.getKey().bike.owner.id == id}">
                                            <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.getKey().user.id}'/>"><c:out value="${reservation.getKey().user.login}"/></a></p>
                                            <a href="/message/user/${reservation.getKey().user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            <c:if test="${reservation.getValue() == null && reservation.getKey().status == 2 && reservation.getKey().endDate.isBefore(now)}">
                                                <p><a href="<c:url value='/rate/${reservation.getKey().id}'/>">Oceń</a></p>
                                            </c:if>
                                            <c:if test="${reservation.getValue() != null}">
                                                <div class="mt-2">
                                                    <p><strong>Twoja ocena: ${reservation.getValue().rating}/5</strong></p>
                                                    <p style="font-size: smaller; font-style: italic; color: gray;">
                                                            ${reservation.getValue().comment}
                                                    </p>
                                                </div>
                                            </c:if>
                                        </c:when>
                                    </c:choose>

                                    <c:if test="${reservation.getKey().status == 0 && reservation.getKey().bike.owner.id != id}">
                                        <form action="<c:url value='/rent/cancel/${reservation.getKey().id}'/>" method="post">
                                            <button type="submit" class="btn btn-danger mt-2">Anuluj</button>
                                        </form>
                                    </c:if>
                                    <div class="d-flex justify-content-center">
                                        <c:if test="${reservation.getKey().status == 0 && reservation.getKey().bike.owner.id == id}">
                                            <form action="<c:url value='/rent/accept/${reservation.getKey().id}'/>" method="post" class="mr-2">
                                                <button type="submit" class="btn btn-success mt-2">Zaakceptuj</button>
                                            </form>
                                            <form action="<c:url value='/rent/deny/${reservation.getKey().id}'/>" method="post">
                                                <button type="submit" class="btn btn-danger mt-2">Odrzuć</button>
                                            </form>
                                        </c:if>
                                    </div>

                                    <div class="lower-div">
                                        <c:choose>
                                            <c:when test="${reservation.getKey().status == 2 && reservation.getKey().startDate.isAfter(now)}">
                                                <p class="text-success"><strong>Rezerwacja potwierdzona</strong></p>
                                            </c:when>
                                            <c:when test="${reservation.getKey().status == 1}">
                                                <p class="text-danger"><strong>Rezerwacja odrzucona</strong></p>
                                            </c:when>
                                            <c:when test="${reservation.getKey().status == 3}">
                                                <p class="text-danger"><strong>Rezerwacja anulowana przez klienta</strong></p>
                                            </c:when>
                                            <c:when test="${reservation.getKey().status == 2 && !reservation.getKey().startDate.isAfter(now) && !reservation.getKey().endDate.isBefore(now)}">
                                                <p class="text-warning"><strong>Trwa...</strong></p>
                                            </c:when>
                                            <c:when test="${reservation.getKey().status == 2 && reservation.getKey().endDate.isBefore(now)}">
                                                <p class="text-muted"><strong>Rezerwacja zakończona</strong></p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="text-warning"><strong>Rezerwacja oczekująca na potwierdzenie</strong></p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Potwierdzone -->
        <div class="tab-pane fade" id="confirmed" role="tabpanel" aria-labelledby="confirmed-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations.keySet()}">
                    <c:if test="${reservation.status == 2 && reservation.startDate.isAfter(now)}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.bike.base64Image}">
                                        <a href="/bike/details/${reservation.bike.id}"><img src="data:image/jpeg;base64,${reservation.bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>
                                </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.bike.id}">${reservation.bike.title}</a></h4>
                                    <p><c:out value="${reservation.bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.bike.owner.id!=id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.bike.owner.id}'/>"><c:out value="${reservation.bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                            <c:when test="${reservation.bike.owner.id==id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.user.id}'/>"><c:out value="${reservation.user.login}"/></a></p>
                                                <a href="/message/user/${reservation.user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                        </c:choose>
                                        <div class="lower-div">
                                            <p class="text-success"><strong>Rezerwacja potwierdzona</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <!-- Twające -->
        <div class="tab-pane fade" id="ongoing" role="tabpanel" aria-labelledby="ongoing-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations.keySet()}">
                    <c:if test="${reservation.status == 2 && !reservation.startDate.isAfter(now) && !reservation.endDate.isBefore(now)}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.bike.base64Image}">
                                        <a href="/bike/details/${reservation.bike.id}"><img src="data:image/jpeg;base64,${reservation.bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>
                                </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.bike.id}">${reservation.bike.title}</a></h4>
                                    <p><c:out value="${reservation.bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.bike.owner.id!= id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.bike.owner.id}'/>"><c:out value="${reservation.bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                            <c:when test="${reservation.bike.owner.id== id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.user.id}'/>"><c:out value="${reservation.user.login}"/></a></p>
                                                <a href="/message/user/${reservation.user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                        </c:choose>
                                        <div class="lower-div">
                                            <p class="text-warning"><strong>Trwa...</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <!-- Oczekujące -->
        <div class="tab-pane fade" id="pending" role="tabpanel" aria-labelledby="pending-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations.keySet()}">
                    <c:if test="${reservation.status == 0}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.bike.base64Image}">
                                        <a href="/bike/details/${reservation.bike.id}"><img src="data:image/jpeg;base64,${reservation.bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>                                    </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.bike.id}">${reservation.bike.title}</a></h4>
                                    <p><c:out value="${reservation.bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.bike.owner.id!= id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.bike.owner.id}'/>"><c:out value="${reservation.bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                            <c:when test="${reservation.bike.owner.id== id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.user.id}'/>"><c:out value="${reservation.user.login}"/></a></p>
                                                <a href="/message/user/${reservation.user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                        </c:choose>
                                        <c:if test="${reservation.status == 0 && reservation.bike.owner.id != id}">
                                            <form action="<c:url value='/rent/cancel/${reservation.id}'/>" method="post">
                                                <button type="submit" class="btn btn-danger mt-2">Anuluj</button>
                                            </form>
                                        </c:if>
                                        <div class="d-flex justify-content-center">
                                            <c:if test="${reservation.status == 0 && reservation.bike.owner.id == id}">
                                                <form action="<c:url value='/rent/accept/${reservation.id}'/>" method="post" class="mr-2">
                                                    <button type="submit" class="btn btn-success mt-2">Zaakceptuj</button>
                                                </form>
                                                <form action="<c:url value='/rent/deny/${reservation.id}'/>" method="post">
                                                    <button type="submit" class="btn btn-danger mt-2">Odrzuć</button>
                                                </form>
                                            </c:if>
                                        </div>
                                        <div class="lower-div">
                                            <p class="text-warning"><strong>Rezerwacja oczekująca na potwierdzenie</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <!-- Odrzucone -->
        <div class="tab-pane fade" id="rejected" role="tabpanel" aria-labelledby="rejected-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations.keySet()}">
                    <c:if test="${reservation.status == 1}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.bike.base64Image}">
                                        <a href="/bike/details/${reservation.bike.id}"><img src="data:image/jpeg;base64,${reservation.bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>                                  </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.bike.id}">${reservation.bike.title}</a></h4>
                                    <p><c:out value="${reservation.bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.bike.owner.id!= id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.bike.owner.id}'/>"><c:out value="${reservation.bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                            <c:when test="${reservation.bike.owner.id== id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.user.id}'/>"><c:out value="${reservation.user.login}"/></a></p>
                                                <a href="/message/user/${reservation.user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                        </c:choose>
                                        <div class="lower-div">
                                            <p class="text-danger"><strong>Rezerwacja odrzucona</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <!-- Anulowane przez klienta -->
        <div class="tab-pane fade" id="cancelled" role="tabpanel" aria-labelledby="cancelled-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations.keySet()}">
                    <c:if test="${reservation.status == 3}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.bike.base64Image}">
                                        <a href="/bike/details/${reservation.bike.id}"><img src="data:image/jpeg;base64,${reservation.bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>
                                </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.bike.id}">${reservation.bike.title}</a></h4>
                                    <p><c:out value="${reservation.bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.bike.pricePerDay * reservation.duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.bike.owner.id!= id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.bike.owner.id}'/>"><c:out value="${reservation.bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                            <c:when test="${reservation.bike.owner.id== id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.user.id}'/>"><c:out value="${reservation.user.login}"/></a></p>
                                                <a href="/message/user/${reservation.user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                            </c:when>
                                        </c:choose>
                                        <div class="lower-div">
                                            <p class="text-danger"><strong>Rezerwacja anulowana</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!-- Archiwalne -->
        <div class="tab-pane fade" id="archived" role="tabpanel" aria-labelledby="archived-tab">
            <div class="list-group mt-3">
                <c:forEach var="reservation" items="${reservations}">
                    <c:if test="${reservation.getKey().status == 2 && reservation.getKey().endDate.isBefore(now)}">
                        <div class="list-group-item">
                            <div class="row">
                                <div class="col-md-3">
                                    <c:if test="${not empty reservation.getKey().bike.base64Image}">
                                        <a href="/bike/details/${reservation.getKey().bike.id}"><img src="data:image/jpeg;base64,${reservation.getKey().bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                    </c:if>
                                </div>
                                <div class="col-md-5">
                                    <h4><a href="/bike/details/${reservation.getKey().bike.id}">${reservation.getKey().bike.title}</a></h4>
                                    <p><c:out value="${reservation.getKey().bike.description}"/></p>
                                    <p><strong>Data od: </strong><c:out value="${reservation.getKey().startDate}"/></p>
                                    <p><strong>Data do: </strong><c:out value="${reservation.getKey().endDate}"/></p>
                                    <p><strong>Cena za dzień: </strong><c:out value="${reservation.getKey().bike.pricePerDay}"/> PLN</p>
                                    <p><strong>Cena całkowita: </strong><c:out value="${reservation.getKey().bike.pricePerDay * reservation.getKey().duration}"/> PLN</p>
                                </div>
                                <div class="col-md-4">
                                    <div class="text-center mt-3">
                                        <c:choose>
                                            <c:when test="${reservation.getKey().bike.owner.id != id}">
                                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${reservation.getKey().bike.owner.id}'/>"><c:out value="${reservation.getKey().bike.owner.login}"/></a></p>
                                                <a href="/message/user/${reservation.getKey().bike.owner.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                                <c:if test="${reservation.getValue() == null}">
                                                    <p><a href="<c:url value='/rate/${reservation.getKey().id}'/>">Oceń</a></p>
                                                </c:if>
                                                <c:if test="${reservation.getValue() != null}">
                                                    <div class="mt-2">
                                                        <p><strong>Twoja ocena: ${reservation.getValue().rating}/5</strong></p>
                                                        <p style="font-size: smaller; font-style: italic; color: gray;">
                                                                ${reservation.getValue().comment}
                                                        </p>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${reservation.getKey().bike.owner.id == id}">
                                                <p><strong>Wynajmujący: </strong><a href="<c:url value='/user/${reservation.getKey().user.id}'/>"><c:out value="${reservation.getKey().user.login}"/></a></p>
                                                <a href="/message/user/${reservation.getKey().user.id}"><img width="50px" height="50px" title="Napisz wiadomość" src="${pageContext.request.contextPath}/images/message.png" alt="Send Message" class="img-fluid"/></a>
                                                <c:if test="${reservation.getValue() == null}">
                                                    <p><a href="<c:url value='/rate/${reservation.getKey().id}'/>">Oceń</a></p>
                                                </c:if>
                                                <c:if test="${reservation.getValue() != null}">
                                                    <div class="mt-2">
                                                        <p><strong>Twoja ocena: ${reservation.getValue().rating}/5</strong></p>
                                                        <p style="font-size: smaller; font-style: italic; color: gray;">
                                                                ${reservation.getValue().comment}
                                                        </p>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                        <div class="lower-div">
                                            <p class="text-muted"><strong>Rezerwacja zakończona</strong></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>

    </div>
</div>

<%@ include file="elements/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
