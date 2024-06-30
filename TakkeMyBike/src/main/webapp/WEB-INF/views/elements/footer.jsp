<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form"
           uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    footer {
        background-color: #f8f9fa;
        text-align: center;
        padding: 10px 0;
    }
    .alert-custom-position {
        position: fixed;
        bottom: 20px;
        right: 20px;
        z-index: 1000;
        min-width: 200px;
    }
</style>
<c:if test="${not empty msg}">
    <div class="alert alert-success alert-custom-position" role="alert">
            ${msg}
    </div>
</c:if>
<footer class="bg-light text-center py-4 mt-5">
    <p>&copy; 2024 TakkeMyBike. Wszelkie prawa zastrzeżone.</p>
</footer>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>