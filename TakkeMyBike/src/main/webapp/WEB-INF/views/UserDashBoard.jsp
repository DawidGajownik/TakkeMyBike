<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profil Użytkownika - TakeMyBike!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <h2>Profil Użytkownika</h2>
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Witaj, ${user.firstName} ${user.lastName}</h5>
            <p class="card-text">Email: ${user.email}</p>
            <p class="card-text">Numer telefonu: ${user.phone}</p>
            <a href="/bikes/my" class="btn btn-primary">Moje Rowery</a>
            <a href="/bikes/add" class="btn btn-secondary">Dodaj Rower</a>
        </div>
    </div>
</div>

<footer class="bg-light text-center py-4 mt-5">
    <p>&copy; 2024 Wypożyczalnia Rowerów. Wszelkie prawa zastrzeżone.</p>
</footer>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
