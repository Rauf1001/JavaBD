<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Бронирование номера</h2>

<form method="post" action="${pageContext.request.contextPath}/booking">

    <h3>Данные клиента</h3>
    <input name="name" placeholder="ФИО" required />
    <input name="phone" placeholder="Телефон" required />
    <input name="email" placeholder="Email" />
    <input name="passport" placeholder="Паспорт" required />
    <input type="date" name="birthDate" required />

    <h3>Бронирование</h3>
    <label>Номер:</label>
    <select name="livingRoomId">
        <c:forEach var="room" items="${rooms}">
            <option value="${room.id}">
                ${room.room_number} (${room.location})
            </option>
        </c:forEach>
    </select>

    <input type="date" name="arrivalDate" required />
    <input type="date" name="departureDate" required />
    <input type="number" name="guests" min="1" value="1" />

    <button type="submit">Забронировать</button>
</form>
