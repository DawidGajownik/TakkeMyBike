# Platforma Wypożyczania Rowerów przez Osoby Prywatne

## Opis projektu

Celem projektu jest stworzenie aplikacji, która umożliwia osobom prywatnym wypożyczanie swoich rowerów innym użytkownikom. Aplikacja pozwala na rejestrację rowerów, przeglądanie dostępnych rowerów w okolicy, wypożyczanie rowerów, a także zarządzanie rezerwacjami i płatnościami.

## Kluczowe funkcjonalności

### Rejestracja i logowanie użytkowników:

- Użytkownicy mogą się rejestrować, logować i wylogowywać.
- Hasła są bezpiecznie przechowywane z użyciem szyfrowania.

### Dodawanie rowerów:

- Użytkownicy mogą dodawać swoje rowery do bazy, podając szczegóły takie jak marka, model, typ roweru, stan techniczny, zdjęcia, itp.
- Możliwość ustalenia ceny.

### Przeglądanie i wyszukiwanie rowerów:

- Użytkownicy mogą przeglądać dostępne rowery.
- Możliwość filtrowania wyników według typu roweru, cenyitp.

### Wypożyczanie rowerów:

- Użytkownicy mogą rezerwować rowery na określony czas.

### Zarządzanie rezerwacjami:

- Użytkownicy mogą przeglądać swoje aktywne i zakończone rezerwacje.
- Właściciele rowerów mogą przeglądać rezerwacje swoich rowerów i akceptować lub odrzucać żądania wypożyczeń.

## Technologie

- Java: główny język programowania.
- Hibernate: ORM do zarządzania bazą danych.
- MySQL: baza danych do przechowywania informacji.

## Dodatkowe funkcjonalności (opcjonalnie)

- Oceny i recenzje: użytkownicy mogą oceniać rowery i dodawać recenzje po zakończeniu wypożyczenia.
- Geolokalizacja: wyświetlanie lokalizacji rowerów na mapie (np. Google Maps API).
- Chat: wbudowany system wiadomości do komunikacji między właścicielem a wypożyczającym.

## Struktura projektu

### Backend:

- Konfiguracja projektu Spring.
- Encje dla użytkowników, rowerów, rezerwacji, płatności.
- Repozytoria i serwisy do zarządzania danymi.
- Kontrolery do obsługi żądań HTTP.

### Frontend:

- Widoki JSP
- Integracja z Google Maps API lub Leaflet.js do wyświetlania lokalizacji rowerów.

### Baza danych:

- Projektowanie schematu bazy danych.
- Tworzenie tabel i relacji za pomocą Hibernate.

## Etapy realizacji

### Projektowanie:

- Projektowanie interfejsu użytkownika.
- Projektowanie bazy danych.

### Implementacja:

- Tworzenie backendu w Spring Boot.
- Implementacja frontendu.
- Integracja z bazą danych.


## Autor

Dawid Gajownik - [Twój GitHub](https://github.com/DawidGajownik)
