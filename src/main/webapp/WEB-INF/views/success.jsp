<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Мои Бронирования</title>
    <style>
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #74ebd5 0%, #acb6e5 100%); margin: 0; padding: 20px; min-height: 100vh; }
        .page-container { max-width: 1200px; margin: 0 auto; background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { color: #2c3e50; text-align: center; margin-bottom: 30px; }
        .data-table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        .data-table th { background-color: #f8f9fa; color: #333; padding: 15px; border: 1px solid #ddd; }
        .data-table td { padding: 12px; border: 1px solid #eee; text-align: center; }
        .data-table tr:hover { background-color: #f1f1f1; }
        .btn-back { display: inline-block; margin-bottom: 20px; padding: 10px 20px; background: #2c3e50; color: white; text-decoration: none; border-radius: 5px; }
        .status-badge { padding: 5px 10px; border-radius: 4px; font-size: 12px; background: #e8f5e9; color: #2e7d32; }
        .empty-msg { padding: 50px; text-align: center; color: #666; font-style: italic; }
        .icon-btn { cursor: pointer; text-decoration: none; font-size: 1.2em; border: none; background: none; }
    </style>
</head>
<body>

<div class="page-container">
    <a href="${pageContext.request.contextPath}/booking" class="btn-back">⬅ Вернуться к бронированию</a>
    <h2>📊 Мои данные и бронирования</h2>

    <table class="data-table">
        <thead>
            <tr>
                <th>Клиент</th>
                <th>Номер</th>
                <th>Сотрудник</th>
                <th>Заезд</th>
                <th>Выезд</th>
                <th>Гостей</th>
                <th>Цена</th>
                <th>Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="editId" value="${param.editId}" />
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td><c:out value="${clientMap[b.idClient]}" default="--"/></td>
                    <td>№<c:out value="${roomMap[b.idLivingRoom]}" default="--"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${not empty staffMap[b.idStaff]}">${staffMap[b.idStaff]}</c:when>
                            <c:otherwise><span style="color: #999;">Не назначен</span></c:otherwise>
                        </c:choose>
                    </td>

                    <c:choose>
                        <c:when test="${editId == b.id}">
                            <form method="post" action="clients-info">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="${b.id}">
                                <td><input type="date" name="arrival" value="${b.arrivalDate}"></td>
                                <td><input type="date" name="departure" value="${b.departureDate}"></td>
                                <td><input type="number" name="guests" value="${b.numberGuests}" style="width: 40px"></td>
                                <td>${b.price}</td>
                                <td>
                                    <button type="submit" class="icon-btn">💾</button>
                                    <a href="clients-info" class="icon-btn">❌</a>
                                </td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td>${b.arrivalDate}</td>
                            <td>${b.departureDate}</td>
                            <td>${b.numberGuests}</td>
                            <td><b>${b.price} ₽</b></td>
                            <td>
                                <a href="?editId=${b.id}" class="icon-btn" title="Редактировать">✏️</a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty bookings}">
        <div class="empty-msg">
            Бронирований по вашему запросу не найдено.<br>
            Убедитесь, что вы только что совершили бронирование.
        </div>
    </c:if>
</div>

</body>
</html>