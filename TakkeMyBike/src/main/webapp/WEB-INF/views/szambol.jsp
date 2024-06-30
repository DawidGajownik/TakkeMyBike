<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TakeMyBike!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">TakeMyBike!</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/login">Logowanie</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/register">Rejestracja</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/addBike">Dodaj Rower</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/browseBikes">Przeglądaj Rowery</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/myReservations">Moje Rezerwacje</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-5">
    <!-- Logowanie -->
    <section id="login">
        <h2>Logowanie</h2>
        <form>
            <div class="form-group">
                <label for="loginEmail">Email:</label>
                <input type="email" class="form-control" id="loginEmail" required>
            </div>
            <div class="form-group">
                <label for="loginPassword">Hasło:</label>
                <input type="password" class="form-control" id="loginPassword" required>
            </div>
            <button type="submit" class="btn btn-primary">Zaloguj się</button>
        </form>
    </section>

    <!-- Rejestracja -->
    <section id="register" class="mt-5">
        <h2>Rejestracja</h2>
        <form>
            <div class="form-group">
                <label for="registerName">Imię:</label>
                <input type="text" class="form-control" id="registerName" required>
            </div>
            <div class="form-group">
                <label for="registerEmail">Email:</label>
                <input type="email" class="form-control" id="registerEmail" required>
            </div>
            <div class="form-group">
                <label for="registerPassword">Hasło:</label>
                <input type="password" class="form-control" id="registerPassword" required>
            </div>
            <button type="submit" class="btn btn-primary">Zarejestruj się</button>
        </form>
    </section>

    <!-- Dodaj Rower -->
    <section id="addBike" class="mt-5">
        <h2>Dodaj Rower</h2>
        <form>
            <div class="form-group">
                <label for="bikeBrand">Marka:</label>
                <input type="text" class="form-control" id="bikeBrand" required>
            </div>
            <div class="form-group">
                <label for="bikeModel">Model:</label>
                <input type="text" class="form-control" id="bikeModel" required>
            </div>
            <div class="form-group">
                <label for="bikeType">Typ:</label>
                <select class="form-control" id="bikeType">
                    <option>Górski</option>
                    <option>Miejski</option>
                    <option>Szosa</option>
                    <option>Elektryczny</option>
                </select>
            </div>
            <div class="form-group">
                <label for="bikePrice">Cena za dzień:</label>
                <input type="number" class="form-control" id="bikePrice" required>
            </div>
            <button type="submit" class="btn btn-primary">Dodaj Rower</button>
        </form>
    </section>

    <!-- Przeglądaj Rowery -->
    <section id="browseBikes" class="mt-5">
        <h2>Przeglądaj Rowery</h2>
        <div class="row">
            <!-- Przykładowy rower -->
            <div class="col-md-4">
                <div class="card mb-4">
                    <img src="rower1.jpg" class="card-img-top" alt="Rower 1">
                    <div class="card-body">
                        <h5 class="card-title">Rower Górski</h5>
                        <p class="card-text">Marka: XYZ, Model: 123</p>
                        <p class="card-text">Cena: 50 PLN/dzień</p>
                        <a href="#" class="btn btn-primary">Wypożycz</a>
                    </div>
                </div>
            </div>
            <!-- Kolejne rowery mogą być dodane w ten sam sposób -->
        </div>
    </section>

    <!-- Moje Rezerwacje -->
    <section id="myReservations" class="mt-5">
        <h2>Moje Rezerwacje</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Rower</th>
                <th scope="col">Data rozpoczęcia</th>
                <th scope="col">Data zakończenia</th>
                <th scope="col">Status</th>
            </tr>
            </thead>
            <tbody>
            <!-- Przykładowa rezerwacja -->
            <tr>
                <td>Rower Górski</td>
                <td>2024-07-01</td>
                <td>2024-07-07</td>
                <td>Zakończona</td>
            </tr>
            <!-- Kolejne rezerwacje mogą być dodane w ten sam sposób -->
            </tbody>
        </table>
    </section>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
