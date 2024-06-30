# Platforma Wypożyczania Rowerów przez Osoby Prywatne

## Opis projektu

Celem projektu jest stworzenie aplikacji, która umożliwia osobom prywatnym wypożyczanie swoich rowerów innym użytkownikom. Aplikacja pozwala na rejestrację rowerów, przeglądanie dostępnych rowerów w okolicy, wypożyczanie rowerów, a także zarządzanie rezerwacjami i płatnościami.

## Kluczowe funkcjonalności

### Rejestracja i logowanie użytkowników:

- Użytkownicy mogą się rejestrować, logować i wylogowywać.
- Hasła są bezpiecznie przechowywane z użyciem szyfrowania.

### Dodawanie rowerów:

- Użytkownicy mogą dodawać swoje rowery do bazy, podając szczegóły takie jak marka, model, typ roweru, stan techniczny, zdjęcia, i podawać adres pod którym są dostępne
- Możliwość ustalenia ceny i minimalnego czasu wynajmu.

### Przeglądanie i wyszukiwanie rowerów:

- Użytkownicy mogą przeglądać dostępne rowery.
- Uzytkownicy mogą przeglądać swoje rowery lub konkretnego użytkownika.
- Możliwość filtrowania wyników według typu roweru, ceny, odległości od podanego adresu itp.

### Zarządzanie rezerwacjami:
- Użytkownicy mogą rezerwować rowery na określony czas.
- Użytkownicy mogą anulować rezerwację.
- Użytkownicy mogą przeglądać swoje aktywne i zakończone rezerwacje.
- Właściciele rowerów mogą przeglądać rezerwacje swoich rowerów i akceptować lub odrzucać żądania wypożyczeń.

## Technologie

- Java: główny język programowania.
- Spring MVC: 
- Hibernate: ORM do zarządzania bazą danych.
- MySQL: baza danych do przechowywania informacji.

## Dodatkowe funkcjonalności (opcjonalnie)

- Oceny i recenzje: użytkownicy mogą oceniać rowery i dodawać recenzje po zakończeniu wypożyczenia.
- Geolokalizacja: wyświetlanie lokalizacji rowerów na mapie (np. Google Maps API).
- Chat: wbudowany system wiadomości do komunikacji między właścicielem a wypożyczającym.

## Struktura projektu

### Backend:

- Konfiguracja projektu Spring.
- Encje dla użytkowników, rowerów, rezerwacji, wiadomości, adresów.
- Repozytoria i serwisy do zarządzania danymi.
- Kontrolery do obsługi żądań HTTP.

### Frontend:

- Widoki JSP
- Integracja z Google Maps API do wyświetlania lokalizacji rowerów.

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

Dawid Gajownik - [Mój GitHub](https://github.com/DawidGajownik)
