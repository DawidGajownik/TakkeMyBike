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
    <style>
        .jumbotron {
            background-image: url("${pageContext.request.contextPath}/images/newbckgrnd.png"); /* Add your image path here */
            background-size: cover;
            color: white;
            text-align: center;
            height: 60vh;
        }
        .how-it-works {
            text-align: center;
        }
        .how-it-works h2 {
            margin-bottom: 20px;
        }
        .how-it-works .step {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="jumbotron jumbotron-fluid" id="home">
    <div class="container">
        <h1 class="display-4">TakkeMyBike!</h1>
        <p class="lead">Łatwe i szybkie wypożyczanie rowerów od osób prywatnych.</p>
        <c:if test="${
            id==null}">
            <a href="/register" class="btn btn-primary btn-lg">Zarejestruj się</a>
        </c:if>
        <c:if test="${id!=null}">
            <a href="/bike" class="btn btn-primary btn-lg">Szukaj roweru</a>
        </c:if>

    </div>
</div>

<div class="container">
    <section id="how-it-works" class="how-it-works my-5">
        <h2>Jak to działa</h2>
        <div class="row">
            <div class="col-md-4 step">
                <h3>Krok 1</h3>
                <p>Zarejestruj się i zaloguj na naszej platformie.</p>
            </div>
            <div class="col-md-4 step">
                <h3>Krok 2</h3>
                <p>Przeglądaj dostępne rowery i wybierz ten, który Ci odpowiada.</p>
            </div>
            <div class="col-md-4 step">
                <h3>Krok 3</h3>
                <p>Zarezerwuj rower i ciesz się jazdą!</p>
            </div>
        </div>
    </section>

    <section id="popular-bikes" class="my-5">
        <h2>Popularne Rowery</h2>
        <div class="row">
            <!-- Przykładowy rower -->
            <c:forEach var="bike" items="${bike}">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <c:if test="${not empty bike.base64Image}">
                            <img src="data:image/jpeg;base64,${bike.base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" />
                        </c:if>
                        <div class="card-body">
                            <h5 class="card-title">${bike.title}</h5>
                            <p class="card-text">${bike.description}</p>
                            <p class="card-text">Cena: ${bike.pricePerDay} PLN/dzień</p>
                            <a href="#" class="btn btn-primary">Wypożycz</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
</div>

<%@ include file="elements/footer.jsp" %>