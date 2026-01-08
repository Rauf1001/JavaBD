<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход для администратора</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to right, #74ebd5, #ACB6E5);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .container {
            background: #fff;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
            width: 100%;
            max-width: 350px;
        }
        h2 { color: #333; margin-bottom: 20px; }
        input {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            border: none;
            border-radius: 6px;
            background: #4CAF50;
            color: white;
            font-weight: bold;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover { background: #45a049; }
        .error {
            color: #d8000c;
            background-color: #ffdddd;
            border: 1px solid #d8000c;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            font-size: 14px;
        }
        .back-link {
            display: block;
            margin-top: 15px;
            color: #666;
            text-decoration: none;
            font-size: 14px;
        }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Вход в систему</h2>

        <%-- Блок ошибки --%>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin" method="post">
            <input type="text" name="login" placeholder="Логин" required autocomplete="username">
            <input type="password" name="password" placeholder="Пароль" required autocomplete="current-password">
            <button type="submit">Войти</button>
        </form>

        <a href="${pageContext.request.contextPath}/" class="back-link">Вернуться назад</a>
    </div>
</body>
</html>