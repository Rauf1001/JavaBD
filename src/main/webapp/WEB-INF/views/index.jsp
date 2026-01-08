<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Главная страница</title>
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
            border-radius: 16px;
            padding: 40px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            text-align: center;
            width: 100%;
            max-width: 400px;
        }

        h2 {
            color: #333;
            margin-bottom: 30px;
            font-size: 22px;
            font-weight: bold;
        }

        .buttons-container {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn {
            width: 100%;
            padding: 16px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
            color: white;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        /* Кнопка клиента */
        form[action*="login"] .btn {
            background: #2196F3;
        }

        form[action*="login"] .btn:hover {
            background: #1976D2;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(33, 150, 243, 0.3);
        }

        /* Кнопка администратора */
        form[action*="admin"] .btn {
            background: #4CAF50;
        }

        form[action*="admin"] .btn:hover {
            background: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(76, 175, 80, 0.3);
        }

        .btn:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Выберите роль для входа</h2>
        <div class="buttons-container">
            <form action="${pageContext.request.contextPath}/login" method="get">
                <button type="submit" class="btn">Войти как клиент</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin" method="get">
                <button type="submit" class="btn">Войти как администратор</button>
            </form>
        </div>
    </div>
</body>
</html>