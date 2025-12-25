<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная - Отель</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        header {
            background: #4CAF50;
            color: white;
            padding: 20px;
            text-align: center;
        }
        nav a {
            margin: 0 15px;
            text-decoration: none;
            color: white;
            font-weight: bold;
            font-size: 16px;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .hero {
            text-align: center;
        }
        .hero h1 {
            color: #333;
        }
        .hero p {
            color: #666;
            font-size: 1.1em;
            margin: 15px 0;
        }
        .buttons {
            margin-top: 25px;
        }
        .buttons a {
            text-decoration: none;
            background: #4CAF50;
            color: white;
            padding: 12px 25px;
            border-radius: 5px;
            margin: 0 10px;
            font-weight: bold;
            transition: 0.3s;
        }
        .buttons a:hover {
            background: #45a049;
        }
    </style>
</head>
<body>
<header>
    <h1>Добро пожаловать в наш Отель</h1>
    <nav>
        <a href="login.jsp">Вход</a>
        <a href="register.jsp">Регистрация</a>
    </nav>
</header>

<div class="container">
    <div class="hero">
        <h1>Отдыхайте с комфортом</h1>
        <p>Бронируйте номера, подавайте групповые заявки и управляйте своим пребыванием онлайн.</p>
        <div class="buttons">
            <a href="login.jsp">Войти</a>
            <a href="register.jsp">Создать аккаунт</a>
        </div>
    </div>
</div>
</body>
</html>
