<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ошибка сервера (500)</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding: 50px; background: #fff; }
        h1 { color: #c0392b; font-size: 50px; }
        .error-box { text-align: left; background: #f9f9f9; padding: 15px; border: 1px solid #ddd; display: inline-block; max-width: 80%; overflow-x: auto; }
    </style>
</head>
<body>
    <h1>500</h1>
    <h2>Произошла внутренняя ошибка сервера</h2>
    <p>Мы уже работаем над исправлением проблемы.</p>

    <%-- Показывает тех. инфо только если нужно для отладки --%>
    <div class="error-box">
        <strong>Тип ошибки:</strong> ${pageContext.exception}
    </div>
    <br><br>
    <a href="${pageContext.request.contextPath}/booking" style="color: #2c3e50;">Вернуться к бронированию</a>
</body>
</html>