<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Бронирование</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            height: 100vh;

            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
            min-width: 300px;
        }

        h2 {
            margin-bottom: 25px;
            color: #333;
        }

        .actions {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .actions a {
            display: block;
            padding: 10px 15px;
            text-decoration: none;
            background-color: #4CAF50;
            color: #ffffff;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.2s ease;
        }

        .actions a:hover {
            background-color: #45a049;
        }
    </style>
</head>

<body>

<div class="container">
    <h2>${message}</h2>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/booking">Новое бронирование</a>
        <a href="${pageContext.request.contextPath}/bookings">Посмотреть бронирования</a>
    </div>
</div>

</body>
</html>
