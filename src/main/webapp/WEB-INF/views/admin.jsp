<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Админ-панель</title>

    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f8; padding: 20px; }
        h1 { text-align: center; margin-bottom: 30px; }
        h2 { margin-top: 40px; color: #2c3e50; }
        table { width: 100%; border-collapse: collapse; background: #fff; margin-top: 10px; }
        th, td { border: 1px solid #ccc; padding: 8px 12px; text-align: left; }
        th { background: #34495e; color: #fff; }
        tr:nth-child(even) { background: #f2f2f2; }
        form { margin: 8px 0; }
        input, select { padding: 4px; margin: 2px; }

        .pagination {
            margin: 15px 0 30px;
            text-align: center;
        }
        .pagination a {
            padding: 6px 12px;
            margin: 0 5px;
            background: #34495e;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .pagination span {
            margin: 0 10px;
            font-weight: bold;
        }

        .back-btn {
            position: fixed;
            right: 20px;
            bottom: 20px;
            background: #2c3e50;
            color: white;
            padding: 12px 18px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>

<body>

<h1>Админ-панель</h1>

<!-- ================= PAGINATION SETTINGS ================= -->
<c:set var="limit" value="10"/>

<c:set var="clientsPage"  value="${empty param.clientsPage  ? 1 : param.clientsPage}" />
<c:set var="bookingsPage" value="${empty param.bookingsPage ? 1 : param.bookingsPage}" />
<c:set var="roomsPage"    value="${empty param.roomsPage    ? 1 : param.roomsPage}" />
<c:set var="staffPage"    value="${empty param.staffPage    ? 1 : param.staffPage}" />
<c:set var="groupsPage"   value="${empty param.groupsPage   ? 1 : param.groupsPage}" />

<c:set var="clientsStart"  value="${(clientsPage-1)*limit}" />
<c:set var="bookingsStart" value="${(bookingsPage-1)*limit}" />
<c:set var="roomsStart"    value="${(roomsPage-1)*limit}" />
<c:set var="staffStart"    value="${(staffPage-1)*limit}" />
<c:set var="groupsStart"   value="${(groupsPage-1)*limit}" />

<c:set var="clientsEnd"  value="${clientsStart + limit - 1}" />
<c:set var="bookingsEnd" value="${bookingsStart + limit - 1}" />
<c:set var="roomsEnd"    value="${roomsStart + limit - 1}" />
<c:set var="staffEnd"    value="${staffStart + limit - 1}" />
<c:set var="groupsEnd"   value="${groupsStart + limit - 1}" />

<!-- ================= CLIENTS ================= -->
<h2>Клиенты</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/client/add">
    <input name="name" placeholder="ФИО" required>
    <input name="phone" placeholder="Телефон" required>
    <input name="email" placeholder="Email" required>
    <input name="passport" placeholder="Паспорт" required>
    <button>Добавить</button>
</form>

<table>
<tr>
    <th>ID</th><th>ФИО</th><th>Телефон</th><th>Email</th><th>Паспорт</th><th>Действия</th>
</tr>

<c:forEach var="c" items="${clients}" begin="${clientsStart}" end="${clientsEnd}">
<tr>
    <td>${c.id}</td>
    <td>${c.name}</td>
    <td>${c.phone_number}</td>
    <td>${c.email}</td>
    <td>${c.passport_data}</td>
    <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/client/update" style="display:inline">
            <input type="hidden" name="id" value="${c.id}">
            <input name="name" value="${c.name}">
            <input name="phone" value="${c.phone_number}">
            <input name="email" value="${c.email}">
            <input name="passport" value="${c.passport_data}">
            <button>✔</button>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/admin/client/delete" style="display:inline">
            <input type="hidden" name="id" value="${c.id}">
            <button>✖</button>
        </form>
    </td>
</tr>
</c:forEach>
</table>

<div class="pagination">
    <c:if test="${clientsPage > 1}">
        <a href="?clientsPage=${clientsPage-1}">←</a>
    </c:if>
    <span>${clientsPage}</span>
    <c:if test="${clientsEnd + 1 < clients.size()}">
        <a href="?clientsPage=${clientsPage+1}">→</a>
    </c:if>
</div>

<!-- ================= BOOKINGS ================= -->
<h2>Бронирования</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/booking/add">
    <input name="idClient" placeholder="ID клиента">
    <input name="idLivingRoom" placeholder="ID комнаты">
    <input type="date" name="arrivalDate">
    <input type="date" name="departureDate">
    <input name="guests" placeholder="Гостей">
    <input name="price" placeholder="Цена">
    <button>Добавить</button>
</form>

<table>
<tr>
    <th>ID</th><th>Client</th><th>Room</th><th>Заезд</th><th>Выезд</th><th>Гостей</th><th>Цена</th><th>Действия</th>
</tr>

<c:forEach var="b" items="${bookings}" begin="${bookingsStart}" end="${bookingsEnd}">
<tr>
    <td>${b.id}</td>
    <td>${b.idClient}</td>
    <td>${b.idLivingRoom}</td>
    <td>${b.arrivalDate}</td>
    <td>${b.departureDate}</td>
    <td>${b.numberGuests}</td>
    <td>${b.price}</td>
    <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/booking/delete">
            <input type="hidden" name="id" value="${b.id}">
            <button>✖</button>
        </form>
    </td>
</tr>
</c:forEach>
</table>

<div class="pagination">
    <c:if test="${bookingsPage > 1}">
        <a href="?bookingsPage=${bookingsPage-1}">←</a>
    </c:if>
    <span>${bookingsPage}</span>
    <c:if test="${bookingsEnd + 1 < bookings.size()}">
        <a href="?bookingsPage=${bookingsPage+1}">→</a>
    </c:if>
</div>

<!-- ================= ROOMS ================= -->
<h2>Номера</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/room/add">
    <input name="room_number" placeholder="Номер">
    <input name="location" placeholder="Расположение">
    <input name="status" placeholder="Статус">
    <button>Добавить</button>
</form>

<table>
<tr>
    <th>ID</th><th>Номер</th><th>Расположение</th><th>Статус</th><th>Действия</th>
</tr>

<c:forEach var="r" items="${rooms}" begin="${roomsStart}" end="${roomsEnd}">
<tr>
    <td>${r.id}</td>
    <td>${r.room_number}</td>
    <td>${r.location}</td>
    <td>${r.status}</td>
    <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/room/delete">
            <input type="hidden" name="id" value="${r.id}">
            <button>✖</button>
        </form>
    </td>
</tr>
</c:forEach>
</table>

<div class="pagination">
    <c:if test="${roomsPage > 1}">
        <a href="?roomsPage=${roomsPage-1}">←</a>
    </c:if>
    <span>${roomsPage}</span>
    <c:if test="${roomsEnd + 1 < rooms.size()}">
        <a href="?roomsPage=${roomsPage+1}">→</a>
    </c:if>
</div>

<!-- ================= STAFF ================= -->
<!-- ================= STAFF ================= -->
<h2 id="staff">Персонал</h2>

<!-- ===== ПОИСК ПО ФИО ===== -->
<form method="get" action="${pageContext.request.contextPath}/admin#staff">
    <input type="text"
           name="staffSearch"
           placeholder="Поиск по ФИО"
           value="${staffSearch}">
    <button type="submit">Найти</button>

    <c:if test="${not empty staffSearch}">
        <a href="${pageContext.request.contextPath}/admin#staff">Сбросить</a>
    </c:if>
</form>

<!-- ===== ДОБАВЛЕНИЕ СОТРУДНИКА ===== -->
<form method="post" action="${pageContext.request.contextPath}/admin/staff/add#staff">
    <input name="name" placeholder="ФИО" required>
    <input name="passport_data" placeholder="Паспорт" required>
    <input name="phone_number" placeholder="Телефон" required>
    <input name="work_experience" type="number" placeholder="Опыт" required>
    <button type="submit">Добавить</button>
</form>

<table>
    <tr>
        <th>ID</th>
        <th>ФИО</th>
        <th>Телефон</th>
        <th>Паспорт</th>
        <th>Опыт</th>
        <th>Действия</th>
    </tr>

    <c:forEach var="s" items="${staff}" begin="${staffStart}" end="${staffEnd}">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.phone_number}</td>
            <td>${s.passport_data}</td>
            <td>${s.work_experience}</td>
            <td>
                <!-- УДАЛЕНИЕ -->
                <form method="post"
                      action="${pageContext.request.contextPath}/admin/staff/delete#staff"
                      style="display:inline">
                    <input type="hidden" name="id" value="${s.id}">
                    <button type="submit">✖</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- ===== ПАГИНАЦИЯ ===== -->
<div class="pagination">
    <c:if test="${staffPage > 1}">
        <a href="?staffPage=${staffPage-1}&staffSearch=${staffSearch}#staff">←</a>
    </c:if>

    <span>${staffPage}</span>

    <c:if test="${staffEnd + 1 < staff.size()}">
        <a href="?staffPage=${staffPage+1}&staffSearch=${staffSearch}#staff">→</a>
    </c:if>
</div>


<!-- ================= GROUPS ================= -->
<h2>Групповые заявки</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/group/add">
    <input name="idLivingRoom" placeholder="Комната">
    <input type="date" name="arrivalDate">
    <input type="date" name="departureDate">
    <input name="price" placeholder="Цена">
    <select name="status">
        <option value="true">Активно</option>
        <option value="false">Неактивно</option>
    </select>
    <input name="comment" placeholder="Комментарий">
    <button>Добавить</button>
</form>

<table>
<tr>
    <th>ID</th><th>Комната</th><th>Заезд</th><th>Выезд</th><th>Цена</th><th>Статус</th><th>Комментарий</th><th>Действия</th>
</tr>

<c:forEach var="g" items="${groups}" begin="${groupsStart}" end="${groupsEnd}">
<tr>
    <td>${g.id}</td>
    <td>${g.idLivingRoom}</td>
    <td>${g.arrivalDate}</td>
    <td>${g.departureDate}</td>
    <td>${g.price}</td>
    <td>${g.status}</td>
    <td>${g.comment}</td>
    <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/group/delete">
            <input type="hidden" name="id" value="${g.id}">
            <button>✖</button>
        </form>
    </td>
</tr>
</c:forEach>
</table>

<div class="pagination">
    <c:if test="${groupsPage > 1}">
        <a href="?groupsPage=${groupsPage-1}">←</a>
    </c:if>
    <span>${groupsPage}</span>
    <c:if test="${groupsEnd + 1 < groups.size()}">
        <a href="?groupsPage=${groupsPage+1}">→</a>
    </c:if>
</div>

<a href="${pageContext.request.contextPath}/booking" class="back-btn">Назад</a>

</body>
</html>
