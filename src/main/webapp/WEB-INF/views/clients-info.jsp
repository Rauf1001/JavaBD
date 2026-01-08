<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Реестр бронирований</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #74ebd5 0%, #acb6e5 100%); margin: 0; padding: 20px; min-height: 100vh; }
        .page-container { max-width: 1250px; margin: 0 auto; background: white; padding: 30px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { color: #2c3e50; text-align: center; margin-bottom: 30px; }
        .data-table { width: 100%; border-collapse: collapse; background: #fff; }
        .data-table th { background-color: #2c3e50; color: white; padding: 15px; border: 1px solid #ddd; }
        .data-table td { padding: 12px; border: 1px solid #eee; text-align: center; color: #444; }
        .data-table tr:hover { background-color: #f8f9fa; }
        .btn-back { display: inline-block; margin-bottom: 20px; padding: 10px 20px; background: #2c3e50; color: white; text-decoration: none; border-radius: 5px; transition: background 0.3s; }
        .btn-back:hover { background: #1a252f; }
        .edit-input { padding: 5px; border: 1px solid #3498db; border-radius: 4px; width: 130px; }
        .icon-btn { cursor: pointer; text-decoration: none; font-size: 1.2em; border: none; background: none; }
        .pagination { margin-top: 25px; text-align: center; display: flex; justify-content: center; gap: 10px; }
        .pagination a { padding: 8px 15px; background: #fff; border: 1px solid #ddd; text-decoration: none; color: #2c3e50; border-radius: 5px; }
        .pagination a.active { background: #2c3e50; color: white; border-color: #2c3e50; }
        .price-text { font-weight: bold; color: #2e7d32; }
    </style>
</head>
<body>

<div class="page-container">
    <a href="${pageContext.request.contextPath}/booking" class="btn-back">⬅ Назад к бронированию</a>

    <h2>📊 Реестр всех бронирований (Страница ${currentPage} из ${totalPages})</h2>

    <table class="data-table">
        <thead>
            <tr>
                <th>ID</th>
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
            <c:forEach var="b" items="${bookings}">
                <tr>
                    <td>${b.id}</td>
                    <td><strong><c:out value="${clientMap[b.idClient]}" default="--"/></strong></td>
                    <td>№<c:out value="${roomMap[b.idLivingRoom]}" default="--"/></td>
                    <td><c:out value="${staffMap[b.idStaff]}" default="Не назначен"/></td>

                    <c:choose>
                        <c:when test="${param.editId == b.id}">
                            <form method="post" action="clients-info">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" value="${b.id}">
                                <input type="hidden" name="page" value="${currentPage}">
                                <td><input type="date" name="arrival" value="${b.arrivalDate}" class="edit-input"></td>
                                <td><input type="date" name="departure" value="${b.departureDate}" class="edit-input"></td>
                                <td><input type="number" name="guests" value="${b.numberGuests}" style="width:50px" class="edit-input"></td>
                                <td class="price-text">${b.price} ₽</td>
                                <td>
                                    <button type="submit" class="icon-btn" title="Сохранить">💾</button>
                                    <a href="clients-info?page=${currentPage}" class="icon-btn" title="Отмена">✖</a>
                                </td>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <td>${b.arrivalDate}</td>
                            <td>${b.departureDate}</td>
                            <td>${b.numberGuests}</td>
                            <td class="price-text">${b.price} ₽</td>
                            <td>
                                <div style="display:flex; gap:10px; justify-content:center;">
                                    <a href="?editId=${b.id}&page=${currentPage}" class="icon-btn" title="Редактировать">✏️</a>
                                    <form method="post" action="clients-info" onsubmit="return confirm('Удалить бронь?')">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${b.id}">
                                        <input type="hidden" name="page" value="${currentPage}">
                                        <button type="submit" class="icon-btn" style="color:red" title="Удалить">🗑️</button>
                                    </form>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty bookings}">
        <div style="text-align:center; padding: 40px; color: #777;">Бронирований пока нет.</div>
    </c:if>

    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="p">
            <a href="?page=${p}" class="${p == currentPage ? 'active' : ''}">${p}</a>
        </c:forEach>
    </div>
</div>

</body>
</html>