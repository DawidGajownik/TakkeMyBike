<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TakkeMyBike!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <!-- Logowanie -->
    <section id="login">
        <h2>Logowanie</h2>
        <form:form action="/login" modelAttribute="user" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <form:input path="email" name = "email" class="form-control" id="email" required="true"/><br>
                <c:if test="${msg.length()>0}">
                    <div class="alert alert-danger error-message">
                            ${msg}
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label for="password">Hasło:</label>
                <form:input path="password" type="password" class="form-control" id="password" required="true"/><br>
                <c:if test="${msgpsw.length()>0}">
                    <div class="alert alert-danger error-message">
                            ${msgpsw}
                    </div>
                </c:if>
            </div>
            <button type="submit" class="btn btn-primary">Zaloguj się</button><br><br><br>
            Nie masz konta?<br><br>
            <a href="/register" class="btn btn-primary">Zarejestruj się</a>
        </form:form>
    </section>
<%@ include file="elements/footer.jsp" %>