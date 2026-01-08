<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Админ панель</title>
    <link rel="icon" href="data:,">
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
            max-width: 400px;
        }

        h2 {
            color: #333;
            margin-bottom: 30px;
            font-size: 24px;
        }

        .admin-buttons {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn {
            width: 100%;
            padding: 15px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
            background: #2196F3; /* Единый цвет */
            color: white;
            transition: all 0.2s ease;
            box-sizing: border-box;
            display: block;
        }

        .btn:hover {
            background: #1976D2;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        /* Кнопка Назад в стиле примера (в углу) */
        .back-btn {
            position: fixed;
            right: 20px;
            bottom: 20px;
            background: #2c3e50;
            color: white;
            padding: 12px 25px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: bold;
            box-shadow: 0 4px 10px rgba(0,0,0,0.3);
            transition: background 0.2s;
            z-index: 1000;
        }

        .back-btn:hover {
            background: #1a252f;
        }
    </style>
</head>
<body>
    <a href="${pageContext.request.contextPath}/" class="back-btn">Назад</a>

    <div class="container">
        <h2>Админ панель</h2>
        <div class="admin-buttons">
            <form action="${pageContext.request.contextPath}/bookings" method="get">
                <button type="submit" class="btn">Booking</button>
            </form>
            <form action="${pageContext.request.contextPath}/clients" method="get">
                <button type="submit" class="btn">Client</button>
            </form>
            <form action="${pageContext.request.contextPath}/livingrooms" method="get">
                <button type="submit" class="btn">LivingRoom</button>
            </form>
            <form action="${pageContext.request.contextPath}/staff" method="get">
                <button type="submit" class="btn">Staff</button>
            </form>
        </div>
    </div>
</body>
</html>