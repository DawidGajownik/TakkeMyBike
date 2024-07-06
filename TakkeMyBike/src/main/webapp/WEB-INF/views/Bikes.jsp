<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Rowerów</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Dodaj Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .filter-form {
            margin-bottom: 20px;
        }
        .sort-buttons {
            display: flex;
            justify-content: flex-end; /* Zmienione z flex-start na flex-end */
            margin-bottom: 20px;
            align-items: center; /* Wyśrodkowanie ikon */
        }
        .sort-buttons button {
            margin-right: 5px;
            position: relative;
            padding-right: 5px; /* Dla miejsca na ikony */
        }
        .sort-buttons .fa {
            position: absolute;
            right: 5px;
            top: 50%;
            transform: translateY(-50%);
        }
        .custom-container {
            padding-top: 50px;
            padding-left: 50px; /* Adjust left padding */
            padding-right: 50px; /* Adjust right padding */
        }
        .custom-filter-form {
            padding-top: 80px;
        }
        .btn-narrow {
            padding: 0.25rem 0.25rem; /* Zmniejsz padding przycisków */
            font-size: 0.875rem; /* Opcjonalnie zmniejsz rozmiar czcionki */
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<!-- Div na całą szerokość ekranu -->
<div class="container-fluid mt-6 custom-container"> <!-- Changed to container-fluid for full width -->

    <div class="row">
        <!-- Formularz filtrowania -->
        <div class="col-md-2 custom-filter-form">
            <form method="get" action="" class="filter-form">
                <div class="form-group">
                    <label for="search">Szukaj po nazwie/opisie:</label>
                    <input type="text" class="form-control" id="search" name="search" value="${param.search}">
                </div>
                <div class="form-group">
                    <label for="minPrice">Cena minimalna:</label>
                    <input type="number" class="form-control" id="minPrice" name="minPrice" value="${param.minPrice}">
                </div>
                <div class="form-group">
                    <label for="maxPrice">Cena maksymalna:</label>
                    <input type="number" class="form-control" id="maxPrice" name="maxPrice" value="${param.maxPrice}">
                </div>
                <div class="form-group">
                    <label for="owner">Właściciel:</label>
                    <input type="text" class="form-control" id="owner" name="owner" value="${param.owner}">
                </div>
                <div class="form-group">
                    <label for="address">Adres:</label>
                    <input type="text" class="form-control" id="address" name="address" value="${param.address}">
                </div>
                <div class="form-group">
                    <label for="maxDistance">Maksymalna odległość (km):</label>
                    <input type="number" class="form-control" id="maxDistance" name="maxDistance" value="${param.maxDistance}">
                </div>
                <div class="form-group">
                    <label for="minRentDays">Czas wynajmu (dni):</label>
                    <input type="number" class="form-control" id="minRentDays" name="minRentDays" value="${param.minRentDays}">
                </div>
                <div class="form-group">
                    <label for="startDate">Data rozpoczęcia:</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate}">
                </div>
                <div class="form-group">
                    <label for="endDate">Data zakończenia:</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate}">
                </div>
                <button type="submit" class="btn btn-primary">Filtruj</button>
            </form>

            <!-- Opcje sortowania -->

        </div>

        <!-- Lista rowerów -->

        <div class="col-md-10">
            <h2>${PageStatus}</h2>
            <div class="sort-buttons">
                <form method="get" action="">
                    <input type="hidden" name="search" value="${param.search}">
                    <input type="hidden" name="minPrice" value="${param.minPrice}">
                    <input type="hidden" name="maxPrice" value="${param.maxPrice}">
                    <input type="hidden" name="owner" value="${param.owner}">
                    <input type="hidden" name="address" value="${param.address}">
                    <input type="hidden" name="maxDistance" value="${param.maxDistance}">
                    <input type="hidden" name="minRentDays" value="${param.minRentDays}">
                    <input type="hidden" name="startDate" value="${param.startDate}">
                    <input type="hidden" name="endDate" value="${param.endDate}">

                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="priceAsc">Cena <i class="fas fa-chevron-up"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="priceDesc">Cena <i class="fas fa-chevron-down"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="distanceAsc">Odległość <i class="fas fa-chevron-up"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="distanceDesc">Odległość <i class="fas fa-chevron-down"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="titleAsc">Nazwa <i class="fas fa-chevron-up"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="titleDesc">Nazwa <i class="fas fa-chevron-down"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="ownerAsc">Właściciel <i class="fas fa-chevron-up"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="ownerDesc">Właściciel <i class="fas fa-chevron-down"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="minRentDaysAsc">Min czas wynajmu <i class="fas fa-chevron-up"></i></button>
                    <button type="submit" class="btn btn-secondary btn-narrow" name="sort" value="minRentDaysDesc">Min czas wynajmu <i class="fas fa-chevron-down"></i></button>
                </form>
            </div>
            <div class="list-group">
                <c:forEach var="bike" items="${bike}">
                    <div class="list-group-item">
                        <div class="row">
                            <div class="col-md-3">
                                <c:if test="${not empty bike.getKey().base64Image}">
                                    <a href="/bike/details/${bike.getKey().id}"><img src="data:image/jpeg;base64,${bike.getKey().base64Image}" alt="Obraz roweru"  style="max-width: 100%; height: auto;" /></a>
                                </c:if>
                            </div>
                            <div class="col-md-6">
                                <h4><a href="/bike/details/${bike.getKey().id}">${bike.getKey().title}</a></h4>
                                <p><c:out value="${bike.getKey().description}"/></p>
                                <p><strong>Cena za dzień: </strong><c:out value="${bike.getKey().pricePerDay}"/> PLN</p>
                                <p><strong>Minimalny czas wynajmu: </strong><c:out value="${bike.getKey().minRentDays}"/> dni</p>
                                <p><strong>Właściciel: </strong><a href="<c:url value='/user/${bike.getKey().owner.id}'/>"><c:out value="${bike.getKey().owner.login}"/></a></p>
                                <c:if test="${bike.getValue()!=null}">
                                    <p><strong>Od Ciebie: </strong>${bike.getValue()}km</p>
                                </c:if>
                                <a href="<c:url value='/bike/details/${bike.getKey().id}'/>" class="btn btn-primary">Szczegóły</a>
                                <div>
                                    <strong>Zarezerwowany w dniach:</strong>
                                    <ul>
                                        <c:forEach var="rent" items="${rents}">
                                            <c:if test="${rent.bike.id == bike.getKey().id && rent.status == 2}">
                                                <li>Od: <c:out value="${rent.startDate}"/> Do: <c:out value="${rent.endDate}"/></li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <c:if test="${bike.getKey().owner.id == id}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <a href="<c:url value='/bike/manage/${bike.getKey().id}'/>" class="btn btn-success">Zarządzaj</a>
                                </div>
                            </c:if>
                            <c:if test="${bike.getKey().owner.id != id}">
                                <div class="col-md-3 d-flex align-items-center justify-content-end">
                                    <a href="<c:url value='/rent/bike/${bike.getKey().id}'/>" class="btn btn-success">Wypożycz</a>
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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
