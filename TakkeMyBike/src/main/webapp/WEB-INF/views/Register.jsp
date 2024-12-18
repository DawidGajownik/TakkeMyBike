<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> <c:if test="${id!=null}">
        Zmiana danych
    </c:if>
        <c:if test="${id==null}">
            Rejestracja - TakkeMyBike!
        </c:if> </title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2><c:if test="${id!=null}">
        Zmiana danych
    </c:if>
        <c:if test="${id==null}">
            Rejestracja
        </c:if></h2>
    <form:form action="/register" modelAttribute="user" method="post">

        <form:hidden path="id" id="id"/>

        <div class="form-group">
            <label for="login">Nazwa użytkownika:</label>
            <form:input path="login" class="form-control" id="login" required="true"/>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <form:input path="email" type="email" class="form-control" id="email" required="true"/>
        </div>
        <c:if test="${wrngpsw.length()>0}">
            <div class="alert alert-danger error-message">
                    ${wrngpsw}
            </div>
        </c:if>
        <c:if test="${id==null}">
            <div class="form-group">
                <label for="password">Hasło:</label>
                <form:input path="password" type="password" class="form-control" id="password" required="true"/>
            </div>
        </c:if>
        <c:if test="${id!=null}">
            <div class="form-group">
                <label for="oldPassword">Stare hasło:</label>
                <input type="password" class="form-control" id="oldPassword" name="oldPassword">
            </div>
            <div class="form-group">
                <label for="newPassword">Nowe hasło:</label>
                <input type="password" class="form-control" id="newPassword" name="newPassword">
            </div>
        </c:if>
        <div class="form-group">
            <label for="confirmPassword">Potwierdź hasło:</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
        </div>
        <div class="form-group">
            <label for="firstName">Imię:</label>
            <form:input path="firstName" class="form-control" id="firstName" required="true"/>
        </div>
        <div class="form-group">
            <label for="lastName">Nazwisko:</label>
            <form:input path="lastName" class="form-control" id="lastName" required="true"/>
        </div>
        <div class="form-group">
            <label for="telephoneNumber">Numer telefonu:</label>
            <form:input path="telephoneNumber" type="tel" class="form-control" id="telephoneNumber" required="true"/>
        </div>
        <div class="form-group">
            <label for="description">Opis:</label>
            <form:textarea path="description" class="form-control" id="description"/>
        </div>
        <div class="form-check">
            <form:checkbox path="rentToOthers" class="form-check-input" id="rentToOthers"/>
            <label class="form-check-label" for="rentToOthers">Chcę wypożyczać rowery innym osobom</label>
        </div>
        <div class="form-check">
            <form:checkbox path="rentFromOthers" class="form-check-input" id="rentFromOthers"/>
            <label class="form-check-label" for="rentFromOthers">Chcę wypożyczać rowery od innych osób</label>
        </div>
        <button type="submit" class="btn btn-primary mt-3">
            <c:if test="${id!=null}">
                Zmień dane
            </c:if>
            <c:if test="${id==null}">
                Zarejestruj się
            </c:if></button>
    </form:form>
</div>

<%@ include file="elements/footer.jsp" %>