<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Страница не найдена (404)</title>
    <style>
        body { font-family: sans-serif; text-align: center; padding: 50px; background: #f4f7f6; }
        h1 { color: #e74c3c; font-size: 50px; }
        p { color: #666; }
        .btn { display: inline-block; margin-top: 20px; padding: 10px 20px; background: #2c3e50; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
    <h1>404</h1>
    <h2>Упс! Страница не найдена</h2>
    <p>К сожалению, запрашиваемая вами страница не существует или была перемещена.</p>
    <a href="${pageContext.request.contextPath}/booking" class="btn">Вернуться на главную</a>
</body>
</html>