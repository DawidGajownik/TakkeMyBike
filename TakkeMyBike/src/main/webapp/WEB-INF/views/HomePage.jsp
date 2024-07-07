<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        .carousel-item.active,
        .carousel-item-next,
        .carousel-item-prev {
            display: flex;
        }
        .carousel-item {
            display: none;
        }
        .carousel-item-next,
        .carousel-item-prev {
            display: flex;
        }
        .card {
            flex: 1;
            margin: 0 10px;
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="jumbotron jumbotron-fluid" id="home">
    <div class="container">
        <h1 class="display-4">TakkeMyBike!</h1>
        <p class="lead">Łatwe i szybkie wypożyczanie rowerów od osób prywatnych.</p>
        <c:if test="${id == null}">
            <a href="/register" class="btn btn-primary btn-lg">Zarejestruj się</a>
        </c:if>
        <c:if test="${id != null}">
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
        <div id="bikeCarousel" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                <c:forEach var="bike" items="${bike}" varStatus="status">
                    <c:if test="${status.index % 3 == 0}">
                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                    </c:if>
                    <div class="card mb-4">
                        <c:if test="${not empty bike.base64Image}">
                            <a href="/bike/details/${bike.id}">
                                <img src="data:image/jpeg;base64,${bike.base64Image}" alt="Obraz roweru" class="d-block w-100" style="height: 100%; object-fit: cover;" />
                            </a>
                        </c:if>
                        <div class="card-body">
                            <h5><a href="/bike/details/${bike.id}">${bike.title}</a></h5>
                            <p class="card-text">${bike.description}</p>
                            <p class="card-text">Cena: ${bike.pricePerDay} PLN/dzień</p>
                            <c:if test="${bike.owner.id == id}">
                                <a href="/bike/manage/${bike.id}" class="btn btn-primary">Zarządzaj</a>

                            </c:if>
                            <c:if test="${bike.owner.id != id}">
                                <a href="/rent/bike/${bike.id}" class="btn btn-primary">Wypożycz</a>
                            </c:if>
                        </div>
                    </div>
                    <c:if test="${(status.index + 1) % 3 == 0 || status.last}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <a class="carousel-control-prev" href="#bikeCarousel" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Poprzedni</span>
            </a>
            <a class="carousel-control-next" href="#bikeCarousel" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Następny</span>
            </a>
        </div>
    </section>
</div>
<%@ include file="elements/footer.jsp" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
