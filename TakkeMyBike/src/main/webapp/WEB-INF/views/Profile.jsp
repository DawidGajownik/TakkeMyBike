<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil Użytkownika</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Profil Użytkownika</h2>
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Informacje o Użytkowniku</h5>
                    <p class="card-text"><strong>Imię:</strong> <c:out value="${user.firstName}"/></p>
                    <p class="card-text"><strong>Nazwisko:</strong> <c:out value="${user.lastName}"/></p>
                    <p class="card-text"><strong>Email:</strong> <c:out value="${user.email}"/></p>
                    <p class="card-text"><strong>Numer Telefonu:</strong> <c:out value="${user.telephoneNumber}"/></p>
                    <a href="<c:url value='/bike/user/${user.id}'/>" class="btn btn-primary">Lista Rowerów</a>
                    <a href="<c:url value='/message/user/${user.id}'/>" class="btn btn-secondary">Wyślij Wiadomość</a>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Opis Użytkownika</h5>
                    <p class="card-text"><c:out value="${user.description}"/></p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Oceny i Komentarze</h5>
                    <p class="card-text">
                        <strong>Średnia ocen: ${avgRating}/5</strong>
                    </p>
                    <div class="list-group">
                        <c:forEach var="rating" items="${ratings}">
                            <div class="list-group-item">
                                <p>
                                    Ocena: ${rating.rating}/5 - ${rating.rater.login}
                                </p>
                                <p style="font-size: smaller; font-style: italic; color: gray;">
                                        ${rating.comment}
                                </p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="elements/footer.jsp" %>
</body>
</html>
