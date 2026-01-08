<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление номерами</title>
    <style>
        body { font-family: 'Arial', sans-serif; background: linear-gradient(to right, #74ebd5, #ACB6E5); margin: 0; padding: 0; min-height: 100vh; }
        .page { max-width: 1200px; margin: 20px auto; padding: 10px; }
        .container { background: #fff; border-radius: 12px; padding: 20px; margin-bottom: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2, h3 { text-align: center; color: #333; margin-top: 0; }
        .section { margin-bottom: 30px; }
        .form-row { display: flex; flex-wrap: wrap; gap: 10px; justify-content: center; margin-bottom: 15px; }
        .form-row input, .form-row select { padding: 10px; border-radius: 6px; border: 1px solid #ccc; outline: none; }
        .btn { padding: 10px 20px; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; transition: opacity 0.2s; }
        .btn.primary { background: #4CAF50; color: white; }
        .btn.secondary { background: #2196F3; color: white; }
        .table-container { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; overflow-x: auto; }
        .data-table { margin: 0 auto; border-collapse: collapse; width: 100%; max-width: 1100px; }
        .data-table th, .data-table td { border: 1px solid #eee; padding: 12px 15px; text-align: center; color: #444; }
        .data-table th { background-color: #f8f9fa; font-weight: bold; }
        .data-table tr:hover { background-color: #f1f1f1; }
        .icon-btn { font-size: 18px; border: none; background: none; cursor: pointer; margin: 0 5px; text-decoration: none; display: inline-block; }
        .icon-btn.edit { color: #2196F3; }
        .icon-btn.save { color: #4CAF50; }
        .icon-btn.delete { color: #F44336; }
        .icon-btn.cancel { color: #999; }
        .status-active { color: #28a745; font-weight: bold; }
        .status-inactive { color: #dc3545; font-weight: bold; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { display: inline-block; margin: 0 5px; padding: 5px 10px; text-decoration: none; color: #2196F3; font-weight: bold; }
        .back-btn { position: fixed; right: 20px; bottom: 20px; background: #2c3e50; color: white; padding: 12px 25px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.3); z-index: 1000; }
    </style>
</head>
<body>

<a href="${pageContext.request.contextPath}/admin" class="back-btn">Назад</a>

<div class="page">
    <div class="container">
        <h2>Управление номерами</h2>

        <section class="section">
            <h3>Добавить номер</h3>
            <form method="post" action="${pageContext.request.contextPath}/livingrooms" class="form-row">
                <input type="hidden" name="action" value="add">
                <input name="room_number" placeholder="Номер комнаты" required>
                <input name="location" placeholder="Расположение" required>
                <select name="status">
                    <option value="1">Активна</option>
                    <option value="0">Неактивна</option>
                </select>
                <button class="btn primary" type="submit">➕ Добавить</button>
            </form>
        </section>

        <section class="section">
            <h3>Поиск</h3>
            <form method="get" action="${pageContext.request.contextPath}/livingrooms" class="form-row">
                <select name="searchType">
                    <option value="room_number" ${searchType == 'room_number' ? 'selected' : ''}>По номеру</option>
                    <option value="id" ${searchType == 'id' ? 'selected' : ''}>По ID</option>
                    <option value="location" ${searchType == 'location' ? 'selected' : ''}>По локации</option>
                </select>
                <input name="searchValue" value="${searchValue}" placeholder="Введите значение">
                <button class="btn secondary" type="submit">🔍 Найти</button>
                <c:if test="${not empty searchValue}">
                    <a href="livingrooms" class="btn" style="background:#eee; color:#333; text-decoration:none;">Сброс</a>
                </c:if>
            </form>
        </section>
    </div>

    <div class="table-container">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th><th>Номер</th><th>Расположение</th><th>Статус</th><th>Действия</th>
                </tr>
            </thead>
            <tbody>
            <c:set var="editId" value="${param.editId}" />
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <c:choose>
                        <c:when test="${editId == room.id}">
                            <td>${room.id}</td>
                            <td><input type="text" name="room_number" form="editForm${room.id}" value="${room.room_number}" style="width:90%"></td>
                            <td><input type="text" name="location" form="editForm${room.id}" value="${room.location}" style="width:90%"></td>
                            <td>
                                <select name="status" form="editForm${room.id}">
                                    <option value="1" ${room.status == 1 ? 'selected' : ''}>Активна</option>
                                    <option value="0" ${room.status == 0 ? 'selected' : ''}>Неактивна</option>
                                </select>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/livingrooms" id="editForm${room.id}">
                                    <input type="hidden" name="id" value="${room.id}">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="searchType" value="${searchType}">
                                    <input type="hidden" name="searchValue" value="${searchValue}">
                                    <input type="hidden" name="page" value="${currentPage}">
                                    <button class="icon-btn save" type="submit">💾</button>
                                    <a class="icon-btn cancel" href="livingrooms?searchType=${searchType}&searchValue=${searchValue}&page=${currentPage}">✖</a>
                                </form>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>${room.id}</td>
                            <td>${room.room_number}</td>
                            <td>${room.location}</td>
                            <td>
                                <span class="${room.status == 1 ? 'status-active' : 'status-inactive'}">
                                    ${room.status == 1 ? 'Активна' : 'Неактивна'}
                                </span>
                            </td>
                            <td>
                                <div style="display: flex; justify-content: center;">
                                    <a class="icon-btn edit" href="livingrooms?editId=${room.id}&searchType=${searchType}&searchValue=${searchValue}&page=${currentPage}">✏</a>
                                    <form method="post" action="${pageContext.request.contextPath}/livingrooms" style="margin:0;">
                                        <input type="hidden" name="id" value="${room.id}">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="searchType" value="${searchType}">
                                        <input type="hidden" name="searchValue" value="${searchValue}">
                                        <button class="icon-btn delete" onclick="return confirm('Удалить?')">🗑</button>
                                    </form>
                                </div>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="?page=${i}&searchType=${searchType}&searchValue=${searchValue}"
                   style="${i == currentPage ? 'color:black; pointer-events:none;' : ''}">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>