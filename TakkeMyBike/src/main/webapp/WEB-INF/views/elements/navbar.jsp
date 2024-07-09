<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/" style="width: 10%">
        <img src="${pageContext.request.contextPath}/images/Logo.png" alt="TakkeMyBike!" width="100%" height="100%">
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/#how-it-works">Jak to działa</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/bike">Przeglądaj rowery</a>
            </li>
            <c:if test="${rentToOthers==true}">
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/bike/mine">Moje rowery</a>
                </li>
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/bike/add">Dodaj rower</a>
                </li>
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/rent/my-dashboard">Zarządzaj wynajmem</a>
                    <c:if test="${hasRentNotifications}">
                        <span class="notification-dot"></span>
                    </c:if>
                </li>
            </c:if>
            <c:if test="${rentFromOthers==true}">
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/rent">Moje rezerwacje</a>
                    <c:if test="${hasReservationNotifications}">
                        <span class="notification-dot"></span>
                    </c:if>
                </li>
            </c:if>
            <c:if test="${id==null}">
                <li class="nav-item">
                    <a class="nav-link" href="/login">Logowanie</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/register">Rejestracja</a>
                </li>
            </c:if>
            <c:if test="${id!=null}">
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/message">Wiadomości</a>
                    <c:if test="${hasMessageNotification}">
                        <span class="notification-dot"></span>
                    </c:if>
                </li>
                <li class="nav-item position-relative">
                    <a class="nav-link" href="/edit-profile/${id}">Zmień dane</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">Wyloguj</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>

<style>
    .notification-dot {
        position: absolute;
        top: -5px;
        right: -5px;
        width: 10px;
        height: 10px;
        background-color: red;
        border-radius: 50%;
    }

</style>