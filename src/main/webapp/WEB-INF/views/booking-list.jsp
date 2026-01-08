<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.company.crm.models.Booking" %>
<%@ page import="com.company.crm.models.Client" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Бронирования</title>
    <link rel="icon" href="data:,">
    <style>
        body { font-family: 'Arial', sans-serif; background: linear-gradient(to right, #74ebd5, #ACB6E5); margin: 0; padding: 0; min-height: 100vh; }
        .page { max-width: 1400px; margin: 20px auto; padding: 10px; } /* Шире, так как колонок больше */
        .container { background: #fff; border-radius: 12px; padding: 20px; margin-bottom: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2, h3 { text-align: center; color: #333; margin-top: 0; }
        .section { margin-bottom: 30px; }
        .form-row { display: flex; flex-wrap: wrap; gap: 10px; justify-content: center; margin-bottom: 15px; align-items: center; }
        .form-row input, .form-row select { padding: 8px; border-radius: 6px; border: 1px solid #ccc; outline: none; }
        .btn { padding: 8px 15px; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; transition: opacity 0.2s; }
        .btn.primary { background: #4CAF50; color: white; }
        .btn.secondary { background: #2196F3; color: white; }
        .table-container { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; overflow-x: auto; }
        .staff-table { margin: 0 auto; display: table; border-collapse: collapse; width: 100%; min-width: 1000px; }
        .staff-table th, .staff-table td { border: 1px solid #eee; padding: 8px 10px; text-align: center; color: #444; vertical-align: middle; font-size: 14px; }
        .staff-table th { background-color: #f8f9fa; color: #333; font-weight: bold; }
        .icon-btn { font-size: 18px; border: none; background: none; cursor: pointer; margin: 0 5px; text-decoration: none; transition: transform 0.2s; display: inline-block;}
        .icon-btn.edit { color: #2196F3; }
        .icon-btn.save { color: #4CAF50; }
        .icon-btn.delete { color: #F44336; }
        .icon-btn.cancel { color: #999; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { display: inline-block; margin: 0 5px; padding: 5px 10px; text-decoration: none; color: #2196F3; font-weight: bold; }

        /* Стили для статусов */
        .status-ok { color: green; font-weight: bold; }
        .status-cancel { color: red; font-weight: bold; }
        .back-btn { position: fixed; right: 20px; bottom: 20px; background: #2c3e50; color: white; padding: 12px 25px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.3); z-index: 1000; }

    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/admin" class="back-btn">Назад</a>

<div class="page">
    <div class="container">
        <h2>Управление бронированиями</h2>

        <section class="section">
            <h3>Создать бронь</h3>
            <form method="post" action="bookings" class="form-row">
                <input type="hidden" name="action" value="add">

                <%-- Выпадающий список клиентов --%>
                <select name="clientId" required style="width: 200px;">
                    <option value="" disabled selected>Выберите клиента</option>
                    <c:forEach var="c" items="${clientList}">
                        <option value="${c.id}">${c.name} (${c.phone})</option>
                    </c:forEach>
                </select>

                <input name="roomId" type="number" placeholder="Комната №" required style="width: 100px;">
                <input name="staffId" type="number" placeholder="ID Сотр." required style="width: 80px;">

                <span style="font-size: 12px; color: #555;">Заезд:</span>
                <input name="arrival" type="date" required>

                <span style="font-size: 12px; color: #555;">Выезд:</span>
                <input name="departure" type="date" required>

                <input name="guests" type="number" placeholder="Гостей" required style="width: 70px;">
                <input name="price" type="number" step="0.01" placeholder="Цена" required style="width: 100px;">

                <button class="btn primary" type="submit">➕ Создать</button>
            </form>
        </section>

        <section class="section">
            <h3>Поиск</h3>
            <form method="get" action="bookings" class="form-row">
                <select name="searchType">
                    <option value="id" ${searchType == 'id' ? 'selected' : ''}>ID Брони</option>
                    <option value="room" ${searchType == 'room' ? 'selected' : ''}>№ Комнаты</option>
                </select>
                <input name="searchValue" value="${searchValue}" placeholder="Введите значение" autocomplete="off">
                <button class="btn secondary" type="submit">🔍 Найти</button>
                <a href="bookings" class="btn" style="background:#eee; color:#333; text-decoration:none;">Сброс</a>
            </form>
        </section>
    </div>

    <div class="table-container">
        <table class="staff-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Клиент</th>
                    <th>Комната</th>
                    <th>Сотрудник</th>
                    <th>Заезд</th>
                    <th>Выезд</th>
                    <th>Гостей</th>
                    <th>Цена</th>
                    <th>Статус</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
            <%
                String editId = request.getParameter("editId");
                String sType = (String) request.getAttribute("searchType");
                String sValue = (String) request.getAttribute("searchValue");
                if (sValue == null) sValue = "";

                // Map для быстрого отображения имен клиентов по ID
                Map<Integer, Client> clientMap = (Map<Integer, Client>) request.getAttribute("clientMap");
                List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");

                if (bookings != null && !bookings.isEmpty()) {
                    for (Booking b : bookings) {
                        boolean edit = editId != null && editId.equals(String.valueOf(b.getId()));
                        Client client = clientMap.get(b.getIdClient());
                        String clientName = (client != null) ? client.getName() : "Неизвестно (" + b.getIdClient() + ")";
            %>
                <tr>
                    <td><%=b.getId()%></td>

                    <% if(edit){ %>
                        <%-- РЕЖИМ РЕДАКТИРОВАНИЯ --%>
                        <form method="post" action="bookings" id="editForm<%=b.getId()%>">
                            <input type="hidden" name="id" value="<%=b.getId()%>">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="searchType" value="<%=sType%>">
                            <input type="hidden" name="searchValue" value="<%=sValue%>">
                            <input type="hidden" name="page" value="${currentPage}">

                            <td>
                                <select name="clientId" form="editForm<%=b.getId()%>" style="width: 100%">
                                    <c:forEach var="c" items="${clientList}">
                                        <option value="${c.id}" <%= (client != null && client.getId() == (int)((Client)pageContext.getAttribute("c")).getId()) ? "selected" : "" %>>
                                            ${c.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td><input type="number" name="roomId" value="<%=b.getIdLivingRoom()%>" style="width: 60px;"></td>
                            <td><input type="number" name="staffId" value="<%=b.getIdStaff()%>" style="width: 50px;"></td>
                            <td><input type="date" name="arrival" value="<%=b.getArrivalDate()%>"></td>
                            <td><input type="date" name="departure" value="<%=b.getDepartureDate()%>"></td>
                            <td><input type="number" name="guests" value="<%=b.getNumberGuests()%>" style="width: 40px;"></td>
                            <td><input type="number" step="0.01" name="price" value="<%=b.getPrice()%>" style="width: 70px;"></td>
                            <td>
                                <select name="status">
                                    <option value="true" <%= b.isStatus() ? "selected" : "" %>>Активно</option>
                                    <option value="false" <%= !b.isStatus() ? "selected" : "" %>>Отмена</option>
                                </select>
                            </td>
                            <td>
                                <button class="icon-btn save" type="submit">💾</button>
                                <a class="icon-btn cancel" href="bookings?searchType=<%=sType%>&searchValue=<%=sValue%>&page=${currentPage}">✖</a>
                            </td>
                        </form>
                    <% } else { %>
                        <%-- РЕЖИМ ПРОСМОТРА --%>
                        <td><%=clientName%></td>
                        <td><%=b.getIdLivingRoom()%></td>
                        <td>ID: <%=b.getIdStaff()%></td>
                        <td><%=b.getArrivalDate()%></td>
                        <td><%=b.getDepartureDate()%></td>
                        <td><%=b.getNumberGuests()%></td>
                        <td><%=b.getPrice()%></td>
                        <td>
                            <%-- Было b.getStatus(), стало b.isStatus() --%>
                            <% if(b.isStatus()) { %>
                                <span class="status-ok">Активно</span>
                            <% } else { %>
                                <span class="status-cancel">Отмена</span>
                            <% } %>
                        </td>
                        <td>
                            <div style="display: flex; justify-content: center;">
                                <a class="icon-btn edit" href="bookings?editId=<%=b.getId()%>&searchType=<%=sType%>&searchValue=<%=sValue%>&page=${currentPage}">✏</a>
                                <form method="post" action="bookings" style="margin:0;">
                                    <input type="hidden" name="id" value="<%=b.getId()%>">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="searchType" value="<%=sType%>">
                                    <input type="hidden" name="searchValue" value="<%=sValue%>">
                                    <button class="icon-btn delete" onclick="return confirm('Удалить бронь #<%=b.getId()%>?')">🗑</button>
                                </form>
                            </div>
                        </td>
                    <% } %>
                </tr>
            <% } } else { %>
                <tr><td colspan="10" style="padding: 30px; color: #777;">Нет бронирований</td></tr>
            <% } %>
            </tbody>
        </table>

        <div class="pagination">
            <%
                int current = (request.getAttribute("currentPage") != null) ? (int)request.getAttribute("currentPage") : 1;
                int total = (request.getAttribute("totalPages") != null) ? (int)request.getAttribute("totalPages") : 1;
                String searchParams = "&searchType=" + (sType != null ? sType : "") + "&searchValue=" + sValue;
            %>
            <a href="?page=1<%=searchParams%>">««</a>
            <a href="?page=<%=Math.max(1,current-1)%><%=searchParams%>">«</a>
            <span><%=current%> / <%=total%></span>
            <a href="?page=<%=Math.min(total,current+1)%><%=searchParams%>">»</a>
            <a href="?page=<%=total%><%=searchParams%>">»»</a>
        </div>
    </div>
</div>

</body>
</html>