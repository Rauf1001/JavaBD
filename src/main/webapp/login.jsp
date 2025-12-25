<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход в систему</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #e8f0fe;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-box {
            background: white;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 380px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
            margin-top: 20px;
        }
        label {
            margin-top: 15px;
            font-weight: bold;
            color: #555;
        }
        input[type=text], input[type=password] {
            padding: 12px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
        }
        button {
            margin-top: 25px;
            padding: 12px;
            background: #4CAF50;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
            transition: 0.3s;
        }
        button:hover {
            background: #45a049;
        }
        .register-link {
            text-align: center;
            margin-top: 18px;
        }
        .register-link a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
        }
        .message {
            color: red;
            text-align: center;
            margin-top: 12px;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="login-box">
    <h2>Вход</h2>
    <form action="login" method="post">
        <label for="username">Имя (логин)</label>
        <input type="text" name="username" id="username" required>

        <label for="password">Пароль</label>
        <input type="password" name="password" id="password" required>

        <button type="submit">Войти</button>
    </form>

    <div class="register-link">
        <p>Нет аккаунта? <a href="register.jsp">Зарегистрируйтесь</a></p>
    </div>

    <div class="message">
        <%
            String error = request.getParameter("error");
            if ("wrong".equals(error)) {
                out.print("Неверный логин или пароль");
            }
        %>

    </div>
</div>
</body>
</html>
