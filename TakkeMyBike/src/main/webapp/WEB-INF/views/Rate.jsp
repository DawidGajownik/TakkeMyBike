<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ocena Użytkownika</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .star-rating {
            direction: rtl;
            display: inline-block;
            padding: 20px;
        }
        .star-rating input[type="radio"] {
            display: none;
        }
        .star-rating label {
            color: #bbb;
            font-size: 24px;
            padding: 0;
            cursor: pointer;
        }
        .star-rating label:before {
            content: "\2605";
        }
        .star-rating input[type="radio"]:checked ~ label {
            color: #f2b600;
        }
        .star-rating label:hover,
        .star-rating label:hover ~ label {
            color: #f2b600;
        }
    </style>
</head>
<body>

<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h1>Ocena Użytkownika</h1>
    <div class="card">
        <div class="card-body">
            <h3 class="card-title">Oceń użytkownika ${rating.rated.login}</h3>
            <form:form modelAttribute="rating" action="/rate" method="post" id="ratingForm">
                <form:hidden path="id" id="id"/>
                <form:hidden path="rated.id" id="rated.id"/>
                <form:hidden path="rent.id" id="rent.id"/>
                <form:hidden path="rater.id" id="rater.id"/>
                <div class="star-rating">
                    <form:radiobutton path="rating" id="star-5" value="5" />
                    <label for="star-5" title="5 stars"></label>

                    <form:radiobutton path="rating" id="star-4" value="4" />
                    <label for="star-4" title="4 stars"></label>

                    <form:radiobutton path="rating" id="star-3" value="3" />
                    <label for="star-3" title="3 stars"></label>

                    <form:radiobutton path="rating" id="star-2" value="2" />
                    <label for="star-2" title="2 stars"></label>

                    <form:radiobutton path="rating" id="star-1" value="1" />
                    <label for="star-1" title="1 star"></label>
                </div>
                <div class="form-group mt-3">
                    <label for="comment">Komentarz:</label>
                    <form:textarea class="form-control" path="comment" id="comment" rows="3" />
                </div>
                <div class="text-center mt-3">
                    <button type="submit" class="btn btn-success">Oceń</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<%@ include file="elements/footer.jsp" %>

</body>
</html>
