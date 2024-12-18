<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wypożycz Rower</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .custom-container {
            padding-top: 50px;
            padding-left: 50px; /* Adjust left padding */
            padding-right: 50px; /* Adjust right padding */
        }
        .custom-filter-form {
            padding-top: 80px;
        }
        .carousel-container {
            position: relative;
            max-width: 100%;
            height: 400px; /* Set a fixed height for the container */
            margin: auto;
            overflow: hidden;
            background-color: #f3f3f3; /* Optional: background color to visualize the container */
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .carousel-image {
            display: none;
            max-width: 100%;
            max-height: 100%;
            object-fit: contain; /* Ensure images fit within the container without distortion */
        }

        .prev, .next {
            cursor: pointer;
            position: absolute;
            top: 50%;
            color: white;
            font-weight: bold;
            font-size: 18px;
            transition: 0.6s ease;
            border-radius: 3px;
            user-select: none;
            background-color: rgba(0, 0, 0, 0.5); /* Background for better visibility */
            padding: 10px 20px;
        }

        .next {
            right: 20px; /* Distance from the right edge of the container */
        }

        .prev {
            left: 20px; /* Distance from the left edge of the container */
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Wypożycz Rower</h2>
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty bike.images}">
                <div class="carousel-container">
                    <c:forEach var="image" items="${bike.images}">
                        <img class="carousel-image" src="data:image/jpeg;base64,${image.base64Image}" alt="Obraz roweru" />
                    </c:forEach>
                    <a class="prev" onclick="changeSlide(-1)">&#10094;</a>
                    <a class="next" onclick="changeSlide(1)">&#10095;</a>
                </div>
            </c:if>
        </div>
        <div class="col-md-8">
            <h4><c:out value="${bike.title}"/></h4>
            <p><c:out value="${bike.description}"/></p>
            <p><strong>Cena za dzień: </strong><c:out value="${bike.pricePerDay}"/> PLN</p>
            <p><strong>Minimalny czas wynajmu: </strong><c:out value="${bike.minRentDays}"/> dni</p>
            <c:if test="${bike.address != null}">
                <p><strong>Lokalizacja:</strong> <c:out value="${bike.address.street}"/>, <c:out value="${bike.address.city}"/>, <c:out value="${bike.address.postalCode}"/>, <c:out value="${bike.address.country}"/></p>
            </c:if>
        </div>
    </div>
    <form:form action="/rent" modelAttribute="rent" method="post">
        <div class="form-group">
            <label for="startDate">Data od:</label>
            <form:input path="startDate" type="date" class="form-control" id="startDate" required="true" min="${now}"/>
        </div>
        <div class="form-group">
            <label for="endDate">Data do:</label>
            <form:input path="endDate" type="date" class="form-control" id="endDate" required="true" disabled="true"/>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </c:if>

        <input type="hidden" name="bikeId" value="${bike.id}"/>
        <button type="submit" class="btn btn-success mt-3">Wypożycz</button>
    </form:form>
    <div class="mt-3">
        <h4>Niedostępny:</h4>
        <ul>
            <c:forEach var="rent" items="${rents}">
                <c:if test="${rent.status==2}">
                    <li>Od: <c:out value="${rent.startDate}"/> Do: <c:out value="${rent.endDate}"/></li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var startDateInput = document.getElementById('startDate');
        var endDateInput = document.getElementById('endDate');

        startDateInput.addEventListener('change', function() {
            var startDate = startDateInput.value;
            if (startDate) {
                endDateInput.removeAttribute('disabled');
                endDateInput.setAttribute('min', startDate);
            } else {
                endDateInput.setAttribute('disabled', 'true');
            }
        });
    });
</script>
<script>
    let slideIndex = 0;

    function showSlides() {
        let i;
        const slides = document.querySelectorAll(".carousel-image");
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slideIndex++;
        if (slideIndex > slides.length) {
            slideIndex = 1;
        }
        slides[slideIndex - 1].style.display = "block";
        setTimeout(showSlides, 3000); // Change image every 3 seconds
    }

    function changeSlide(n) {
        const slides = document.querySelectorAll(".carousel-image");
        slides[slideIndex - 1].style.display = "none";
        slideIndex += n;
        if (slideIndex > slides.length) {
            slideIndex = 1;
        } else if (slideIndex < 1) {
            slideIndex = slides.length;
        }
        slides[slideIndex - 1].style.display = "block";
    }

    document.addEventListener("DOMContentLoaded", () => {
        showSlides();
    });
</script>
<%@ include file="elements/footer.jsp" %>
</body>
</html>
