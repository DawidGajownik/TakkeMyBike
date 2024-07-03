<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dodaj Rower - TakeMyBike</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Dodaj Rower</h2>
    <form:form action="/bike/save" modelAttribute="bike" method="post" enctype="multipart/form-data">

        <form:hidden path="id" id="id"/>

        <div class="form-group">
            <label for="title">Marka, model:</label>
            <form:input path="title" class="form-control" id="title" required="true"/>
        </div>
        <div class="form-group">
            <label for="description">Opis:</label>
            <form:input path="description" class="form-control" id="description" required="true"/>
        </div>
        <div class="form-group">
            <label for="street">Ulica:</label>
            <form:input path="address.street" class="form-control" id="street" required="true"/>
        </div>
        <div class="form-group">
            <label for="houseNumber">Numer domu:</label>
            <form:input path="address.streetNumber" class="form-control" id="houseNumber" required="true"/>
        </div>
        <div class="form-group">
            <label for="apartmentNumber">Numer mieszkania:</label>
            <form:input path="address.apartmentNumber" class="form-control" id="apartmentNumber"/>
        </div>
        <div class="form-group">
            <label for="city">Miasto:</label>
            <form:input path="address.city" class="form-control" id="city" required="true"/>
        </div>
        <div class="form-group">
            <label for="postalCode">Kod pocztowy:</label>
            <form:input path="address.postalCode" class="form-control" id="postalCode"/>
        </div>
        <div class="form-group">
            <label for="pricePerDay">Cena za dzień:</label>
            <form:input path="pricePerDay" type="number" class="form-control" id="pricePerDay" required="true"/>
        </div>
        <div class="form-group">
            <label for="minRentDays">Minimalna długość wypożyczenia w dniach:</label>
            <form:input path="minRentDays" type="number" class="form-control" id="minRentDays" required="true"/>
        </div>
        <div class="form-group">
            <label for="image">Zdjęcie:</label>
            <form:input path="image" type="file" id="image" accept="image/*" class="form-control-file" required="false"/>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Dodaj</button>
    </form:form>
</div>
<%@ include file="elements/footer.jsp" %>