# Wypożyczalnia rowerów - Aplikacja webowa

## Opis projektu

Projekt aplikacji webowej do wypożyczania rowerów, oparty na technologiach Spring MVC i Hibernate.

## Funkcjonalności

- Dodawanie nowych rowerów do wypożyczalni z informacjami takimi jak marka, model, opis, cena za dzień oraz minimalna długość wypożyczenia.
- Przesyłanie zdjęcia roweru wraz z informacjami.
- Rejestracja użytkowników oraz możliwość logowania.

## Technologie użyte

- Java
- Spring MVC
- Hibernate
- HTML/CSS
- Bootstrap
- Maven

## Uruchomienie projektu

### Wymagania

- Java 11+
- Apache Maven
- MySQL

### Konfiguracja bazy danych

1. Utwórz bazę danych MySQL o nazwie `bike_rental`.
2. Zaktualizuj plik `application.properties` zgodnie z ustawieniami Twojej bazy danych:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bike_rental?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_password
