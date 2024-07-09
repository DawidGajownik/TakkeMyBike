<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://example.com/functions" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wiadomości - TakkeMyBike!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .chat-container {
            display: flex;
            flex-direction: column;
            height: 50vh;
            border: 1px solid #ccc;
            padding: 10px;
            overflow-y: scroll;
        }
        .message {
            display: flex;
            flex-direction: column;
            margin: 10px 0;
        }
        .message.sent {
            align-items: flex-end;
        }
        .message.received {
            align-items: flex-start;
        }
        .message .content {
            padding: 10px;
            border-radius: 10px;
            max-width: 60%;
        }
        .message.sent .content {
            background-color: #d4f7dc;
        }
        .message.received .content {
            background-color: #f1f0f0;
        }
        .message .timestamp {
            font-size: 0.8em;
            color: #888;
        }
        .users-list {
            position: fixed;
            top: 15%;
            left: 5%;
            width: 15%;
            padding: 10px;
            overflow-y: auto;
        }
        .users-list .list-group-item {
            border-radius: 20px;
            margin-bottom: 5px;
            cursor: pointer;
        }
        .users-list .list-group-item:hover {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<%@ include file="elements/navbar.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-3">
            <div class="users-list">
                <h5>Lista rozmówców:</h5>
                <div class="list-group">
                    <c:forEach var="user" items="${interlocutors}">
                        <a href="<c:url value='/message/user/${user.id}'/>" class="list-group-item">
                            <c:out value="${user.login}"/>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
        <c:if test="${not empty message}">
            <div class="col-md-9">
                <h2>Wiadomości z <a href="/user/${message.receiver.id}"><c:out value="${message.receiver.login}"/></a></h2>
                <div class="chat-container" id="chat-container">
                    <c:forEach var="messages" items="${messages}">
                        <div class="message ${messages.sender.id == id ? 'sent' : 'received'}">
                            <div class="timestamp">${fn:formatLocalDateTime(messages.sendTime)}</div>
                            <div class="content"><c:out value="${messages.content}"/></div>
                        </div>
                    </c:forEach>
                </div>
                <form:form action="/message/send" modelAttribute="message" id="sendMessageForm" method="post">
                    <div class="form-group">
                        <input type="hidden" name="receiverId" value="${message.receiver.id}"/>
                        <form:textarea path="content" class="form-control" rows="3" placeholder="Napisz wiadomość..." required="true" id="content"/>
                    </div>
                    <button type="submit" class="btn btn-primary">Wyślij</button>
                </form:form>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="elements/footer.jsp" %>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('content').focus();
    });
    document.getElementById('chat-container').scrollTop = document.getElementById('chat-container').scrollHeight;
    document.getElementById('content').addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            document.getElementById('sendMessageForm').submit();
        }
    });
</script>
</body>
</html>
