<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Список бронирований</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Клиент</th>
        <th>Заезд</th>
        <th>Выезд</th>
        <th>Гости</th>
    </tr>

    <c:forEach var="b" items="${bookings}">
        <tr>
            <td>${b.id}</td>
            <td>${b.idClient}</td>
            <td>${b.arrivalDate}</td>
            <td>${b.departureDate}</td>
            <td>${b.numberGuests}</td>
        </tr>
    </c:forEach>
</table>
