<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Бронирование номера</title>
    <link rel="icon" href="data:,">
    <style>
        body { font-family: 'Arial', sans-serif; background: linear-gradient(to right, #74ebd5, #ACB6E5); margin: 0; padding: 20px; min-height: 100vh; }
        .container { max-width: 600px; margin: 0 auto; background: #fff; border-radius: 12px; padding: 30px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2, h3 { text-align: center; color: #333; }
        form { display: flex; flex-direction: column; gap: 10px; }
        input, select { padding: 12px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; width: 100%; box-sizing: border-box; }
        label { font-weight: bold; margin-top: 10px; display: block; }
        button[type="submit"] { margin-top: 20px; padding: 15px; border: none; border-radius: 8px; background: #4CAF50; color: white; font-weight: bold; cursor: pointer; font-size: 16px; }
        button[type="submit"]:hover { background: #45a049; }
        /* Кнопка просмотра данных (бывшая админ) */
        .view-data-btn { position: fixed; right: 20px; bottom: 20px; background: #2c3e50; color: #fff; padding: 12px 18px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.3); z-index: 999; }
        .view-data-btn:hover { background: #1a252f; }
        /* Стиль для ошибки */
        .error-msg { background-color: #ffdddd; color: #d8000c; border: 1px solid #d8000c; padding: 10px; border-radius: 6px; margin-bottom: 15px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Бронирование номера</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/booking">
            <h3>Данные клиента</h3>
            <input name="name" placeholder="ФИО" autocomplete="name" required value="${param.name}"/>
            <input name="phone" placeholder="Телефон" type="tel" autocomplete="tel" required value="${param.phone}"/>
            <input name="email" placeholder="Email" type="email" autocomplete="email" value="${param.email}"/>
            <input name="passport" placeholder="Паспорт" autocomplete="off" required value="${param.passport}"/>
            <label>Дата рождения:</label>
            <input type="date" name="birthDate" required value="${param.birthDate}"/>

            <h3>Бронирование</h3>
            <label>Номер:</label>
            <select name="livingRoomId">
                <c:forEach var="room" items="${rooms}">
                    <option value="${room.id}" ${param.livingRoomId == room.id ? 'selected' : ''}>
                        ${room.room_number} (${room.location})
                    </option>
                </c:forEach>
            </select>

            <label>Дата заезда:</label>
            <input type="date" name="arrivalDate" required value="${param.arrivalDate}"/>
            <label>Дата выезда:</label>
            <input type="date" name="departureDate" required value="${param.departureDate}"/>
            <label>Количество гостей:</label>
            <input type="number" name="guests" min="1" value="${param.guests != null ? param.guests : 1}" />

            <button type="submit">✅ Забронировать</button>
        </form>
    </div>

    <a href="${pageContext.request.contextPath}/clients-info" class="view-data-btn">Просмотр Данных</a></body>
</html>