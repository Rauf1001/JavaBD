<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.company.crm.models.Client" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Управление клиентами</title>
    <link rel="icon" href="data:,">
    <style>
        body { font-family: 'Arial', sans-serif; background: linear-gradient(to right, #74ebd5, #ACB6E5); margin: 0; padding: 0; min-height: 100vh; }
        .page { max-width: 1300px; margin: 20px auto; padding: 10px; }
        .container { background: #fff; border-radius: 12px; padding: 20px; margin-bottom: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h2, h3 { text-align: center; color: #333; margin-top: 0; }
        .section { margin-bottom: 20px; border-bottom: 1px dashed #eee; padding-bottom: 20px; }
        .section:last-child { border-bottom: none; }
        .form-row { display: flex; flex-wrap: wrap; gap: 10px; justify-content: center; margin-bottom: 15px; align-items: center; }
        .form-row input, .form-row select { padding: 8px; border-radius: 6px; border: 1px solid #ccc; outline: none; }
        .btn { padding: 8px 15px; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; transition: opacity 0.2s; }
        .btn.primary { background: #4CAF50; color: white; }
        .btn.secondary { background: #2196F3; color: white; }

        .table-container { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; overflow-x: auto; }
        .client-table { margin: 0 auto; display: table; border-collapse: collapse; width: 100%; }
        .client-table th, .client-table td { border: 1px solid #eee; padding: 10px; text-align: center; color: #444; vertical-align: middle; font-size: 14px; }
        .client-table th { background-color: #f8f9fa; color: #333; font-weight: bold; }

        .icon-btn { font-size: 18px; border: none; background: none; cursor: pointer; margin: 0 5px; text-decoration: none; display: inline-block; transition: transform 0.2s; }
        .icon-btn.edit { color: #2196F3; }
        .icon-btn.save { color: #4CAF50; }
        .icon-btn.delete { color: #F44336; }
        .icon-btn.cancel { color: #999; }

        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { display: inline-block; margin: 0 5px; padding: 5px 10px; text-decoration: none; color: #2196F3; font-weight: bold; }
        .pagination span { font-weight: bold; color: #333; }
        input.inline-edit { width: 90%; padding: 5px; border: 1px solid #2196F3; border-radius: 4px; }
        .back-btn { position: fixed; right: 20px; bottom: 20px; background: #2c3e50; color: white; padding: 12px 25px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.3); z-index: 1000; }

    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/admin" class="back-btn">Назад</a>

<div class="page">
    <div class="container">
        <h2>Управление клиентами</h2>

        <%-- СЕКЦИЯ ДОБАВЛЕНИЯ --%>
        <section class="section">
            <h3>➕ Добавить клиента</h3>
            <form method="post" action="clients" class="form-row">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="searchType" value="${searchType}">
                <input type="hidden" name="searchValue" value="${searchValue}">
                <input name="name" placeholder="ФИО" required>
                <input name="email" type="email" placeholder="Email" required>
                <input name="phone" placeholder="Телефон" required>
                <input name="passport" placeholder="Паспорт" required style="width: 120px;">
                <input name="birth_date" type="date">
                <button class="btn primary" type="submit">Добавить</button>
            </form>
        </section>

        <%-- СЕКЦИЯ ПОИСКА (КАК У STAFF) --%>
        <section class="section">
            <h3>🔍 Поиск</h3>
            <form method="get" action="clients" class="form-row">
                <select name="searchType">
                    <option value="name" ${searchType == 'name' ? 'selected' : ''}>ФИО</option>
                    <option value="phone" ${searchType == 'phone' ? 'selected' : ''}>Телефон</option>
                    <option value="id" ${searchType == 'id' ? 'selected' : ''}>ID</option>
                </select>
                <input name="searchValue" value="${searchValue}" placeholder="Введите значение..." autocomplete="off">
                <button class="btn secondary" type="submit">Найти</button>
                <a href="clients" class="btn" style="background:#eee; color:#333; text-decoration:none;">Сброс</a>
            </form>
        </section>
    </div>

    <div class="table-container">
        <table class="client-table">
            <thead>
                <tr>
                    <th>ID</th><th>ФИО</th><th>Email</th><th>Телефон</th><th>Паспорт</th><th>Дата Рождения</th><th>Действия</th>
                </tr>
            </thead>
            <tbody>
            <%
                String editId = request.getParameter("editId");
                String sType = (String) request.getAttribute("searchType");
                String sValue = (String) request.getAttribute("searchValue");
                if (sValue == null) sValue = "";
                String commonParams = "&searchType=" + (sType != null ? sType : "") + "&searchValue=" + sValue + "&page=" + request.getAttribute("currentPage");

                List<Client> clients = (List<Client>) request.getAttribute("clients");
                if (clients != null && !clients.isEmpty()) {
                    for (Client c : clients) {
                        boolean edit = editId != null && editId.equals(String.valueOf(c.getId()));
            %>
                <tr>
                    <td><%=c.getId()%></td>
                    <% if(edit) { %>
                        <form method="post" action="clients" id="editForm<%=c.getId()%>">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="<%=c.getId()%>">
                            <input type="hidden" name="page" value="${currentPage}">
                            <input type="hidden" name="searchType" value="<%=sType%>">
                            <input type="hidden" name="searchValue" value="<%=sValue%>">

                            <td><input type="text" name="name" class="inline-edit" value="<%=c.getName()%>" required></td>
                            <td><input type="email" name="email" class="inline-edit" value="<%=c.getEmail()%>" required></td>
                            <td><input type="text" name="phone" class="inline-edit" value="<%=c.getPhone_number()%>" required></td>
                            <td><input type="text" name="passport" class="inline-edit" value="<%=c.getPassport_data()%>" required></td>
                            <td><input type="date" name="birth_date" class="inline-edit" value="<%=c.getBirth_date()%>"></td>
                            <td>
                                <button class="icon-btn save" type="submit">💾</button>
                                <a class="icon-btn cancel" href="clients?<%=commonParams%>">✖</a>
                            </td>
                        </form>
                    <% } else { %>
                        <td><%=c.getName()%></td>
                        <td><%=c.getEmail()%></td>
                        <td><%=c.getPhone_number()%></td>
                        <td><%=c.getPassport_data()%></td>
                        <td><%=c.getBirth_date() != null ? c.getBirth_date() : "—"%></td>
                        <td>
                            <div style="display: flex; justify-content: center;">
                                <a class="icon-btn edit" href="clients?editId=<%=c.getId()%><%=commonParams%>">✏</a>
                                <form method="post" action="clients" style="margin:0;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%=c.getId()%>">
                                    <input type="hidden" name="page" value="${currentPage}">
                                    <input type="hidden" name="searchType" value="<%=sType%>">
                                    <input type="hidden" name="searchValue" value="<%=sValue%>">
                                    <button class="icon-btn delete" onclick="return confirm('Удалить?')">🗑</button>
                                </form>
                            </div>
                        </td>
                    <% } %>
                </tr>
            <% } } else { %>
                <tr><td colspan="7" style="padding: 30px; color: #777;">Нет данных по вашему запросу</td></tr>
            <% } %>
            </tbody>
        </table>

        <div class="pagination">
            <%
                int current = (request.getAttribute("currentPage") != null) ? (int)request.getAttribute("currentPage") : 1;
                int total = (request.getAttribute("totalPages") != null) ? (int)request.getAttribute("totalPages") : 1;
                String pageSearch = "&searchType=" + (sType != null ? sType : "") + "&searchValue=" + sValue;
            %>
            <c:if test="${totalPages > 1}">
                <a href="?page=1<%=pageSearch%>">««</a>
                <a href="?page=<%=Math.max(1,current-1)%><%=pageSearch%>">«</a>
                <span><%=current%> / <%=total%></span>
                <a href="?page=<%=Math.min(total,current+1)%><%=pageSearch%>">»</a>
                <a href="?page=<%=total%><%=pageSearch%>">»»</a>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>