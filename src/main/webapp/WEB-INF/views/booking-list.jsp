<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список бронирований</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/style.css">
    <style>
        /* Минимальная стилизация на случай, если CSS не подключился */
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            padding: 20px;
        }
        h2 {
            color: #333;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 15px;
            background-color: #fff;
        }
        th, td {
            border: 1px solid #aaa;
            padding: 8px 12px;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        a {
            display: inline-block;
            margin: 10px 0;
            padding: 6px 12px;
            text-decoration: none;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
        }
        a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<h2>Список бронирований</h2>

<table>
    <tr>
        <th>ФИО</th>
        <th>Телефон</th>
        <th>Email</th>
        <th>Паспорт</th>
        <th>Заезд</th>
        <th>Выезд</th>
        <th>Гости</th>
    </tr>

    <c:forEach var="r" items="${rows}">
        <tr>
            <td>${r.fullName}</td>
            <td>${r.phone}</td>
            <td>${r.email}</td>
            <td>${r.passport}</td>
            <td>${r.arrival}</td>
            <td>${r.departure}</td>
            <td>${r.guests}</td>
        </tr>
    </c:forEach>
</table>

<h2>${message}</h2>
<a href="${pageContext.request.contextPath}/booking">Новое бронирование</a>
<a href="${pageContext.request.contextPath}/bookings">Посмотреть бронирования</a>

</body>
</html>
