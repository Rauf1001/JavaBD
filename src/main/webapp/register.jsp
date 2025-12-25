<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fff8e1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .register-box {
            background: white;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(0,0,0,0.15);
            width: 400px;
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
            margin-top: 12px;
            font-weight: bold;
            color: #555;
        }
        input[type=text], input[type=email], input[type=date] {
            padding: 12px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
            font-size: 14px;
        }
        button {
            margin-top: 25px;
            padding: 12px;
            background: #FF9800;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
            transition: 0.3s;
        }
        button:hover {
            background: #fb8c00;
        }
        .login-link {
            text-align: center;
            margin-top: 18px;
        }
        .login-link a {
            color: #FF9800;
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
<div class="register-box">
    <h2>Регистрация</h2>
    <form action="register" method="post">
        <label for="name">Имя</label>
        <input type="text" name="name" id="name" required>

        <label for="email">Email</label>
        <input type="email" name="email" id="email" required>

        <label for="phone">Телефон</label>
        <input type="text" name="phone_number" id="phone" required>

        <label for="passport">Паспорт</label>
        <input type="text" name="passport_data" id="passport" required>

        <label for="birthdate">Дата рождения</label>
        <input type="date" name="birth_date" id="birthdate" required>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <div class="login-link">
        <p>Уже есть аккаунт? <a href="login.jsp">Войти</a></p>
    </div>

    <div class="message">
        <% String error = (String) request.getAttribute("error");
           if(error != null){ out.print(error); } %>
    </div>
</div>
</body>
</html>
