<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.company.crm.models.Staff" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Персонал</title>
    <link rel="icon" href="data:,">
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
        .staff-table { margin: 0 auto; display: table; border-collapse: collapse; width: 100%; max-width: 1100px; }
        .staff-table th, .staff-table td { border: 1px solid #eee; padding: 12px 15px; text-align: center; color: #444; vertical-align: middle; }
        .staff-table th { background-color: #f8f9fa; color: #333; font-weight: bold; }
        .icon-btn { font-size: 18px; border: none; background: none; cursor: pointer; margin: 0 5px; text-decoration: none; transition: transform 0.2s; display: inline-block;}
        .icon-btn.edit { color: #2196F3; }
        .icon-btn.save { color: #4CAF50; }
        .icon-btn.delete { color: #F44336; }
        .icon-btn.cancel { color: #999; }
        .pagination { margin-top: 20px; text-align: center; }
        .pagination a { display: inline-block; margin: 0 5px; padding: 5px 10px; text-decoration: none; color: #2196F3; font-weight: bold; }
        .pagination span { font-weight: bold; color: #333; }
        .back-btn { position: fixed; right: 20px; bottom: 20px; background: #2c3e50; color: white; padding: 12px 25px; border-radius: 50px; text-decoration: none; font-weight: bold; box-shadow: 0 4px 10px rgba(0,0,0,0.3); z-index: 1000; }

    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/admin" class="back-btn">Назад</a>

<div class="page">
    <div class="container">
        <h2>Персонал</h2>

        <section class="section">
            <h3>Добавить сотрудника</h3>
            <form method="post" action="staff" class="form-row">
                <input type="hidden" name="action" value="add">
                <input name="name" placeholder="ФИО" required>
                <input name="passport" placeholder="Паспорт" required>
                <input name="phone" placeholder="Телефон" required>
                <input name="book" placeholder="Трудовая" required>
                <input name="exp" type="number" placeholder="Стаж" required style="width: 80px;">
                <button class="btn primary" type="submit">➕ Добавить</button>
            </form>
        </section>

        <section class="section">
            <h3>Поиск</h3>
            <form method="get" action="staff" class="form-row">
                <select name="searchType">
                    <option value="id" ${searchType == 'id' ? 'selected' : ''}>ID</option>
                    <option value="name" ${searchType == 'name' ? 'selected' : ''}>ФИО</option>
                    <option value="book" ${searchType == 'book' ? 'selected' : ''}>Трудовая</option>
                </select>
                <input name="searchValue" value="${searchValue}" placeholder="Введите значение" autocomplete="off">
                <button class="btn secondary" type="submit">🔍 Найти</button>
                <a href="staff" class="btn" style="background:#eee; color:#333; text-decoration:none;">Сброс</a>
            </form>
        </section>
    </div>

    <div class="table-container">
        <table class="staff-table">
            <thead>
                <tr>
                    <th>ID</th><th>ФИО</th><th>Паспорт</th><th>Телефон</th><th>Трудовая</th><th>Стаж</th><th>Действия</th>
                </tr>
            </thead>
            <tbody>
            <%
                String editId = request.getParameter("editId");
                String sType = (String) request.getAttribute("searchType");
                String sValue = (String) request.getAttribute("searchValue");
                if (sValue == null) sValue = "";

                List<Staff> staffs = (List<Staff>) request.getAttribute("staffs");
                if (staffs != null && !staffs.isEmpty()) {
                    for (Staff s : staffs) {
                        boolean edit = editId != null && editId.equals(String.valueOf(s.getId()));
            %>
                <tr>
                    <td><%=s.getId()%></td>
                    <% if(edit){ %>
                        <td><input type="text" name="name" form="editForm<%=s.getId()%>" value="<%=s.getName()%>" style="width:100%"></td>
                        <td><input type="text" name="passport" form="editForm<%=s.getId()%>" value="<%=s.getPassport_data()%>" style="width:100%"></td>
                        <td><input type="text" name="phone" form="editForm<%=s.getId()%>" value="<%=s.getPhone_number()%>" style="width:100%"></td>
                        <td><input type="text" name="book" form="editForm<%=s.getId()%>" value="<%=s.getStaff_book_number()%>" style="width:100%"></td>
                        <td><input type="number" name="exp" form="editForm<%=s.getId()%>" value="<%=s.getWork_experience()%>" style="width:60px"></td>
                        <td>
                            <form method="post" action="staff" id="editForm<%=s.getId()%>">
                                <input type="hidden" name="id" value="<%=s.getId()%>">
                                <input type="hidden" name="action" value="update">

                                <input type="hidden" name="searchType" value="<%=sType != null ? sType : ""%>">
                                <input type="hidden" name="searchValue" value="<%=sValue%>">
                                <input type="hidden" name="page" value="${currentPage}">

                                <button class="icon-btn save" type="submit">💾</button>
                                <a class="icon-btn cancel" href="staff?searchType=<%=sType%>&searchValue=<%=sValue%>&page=${currentPage}">✖</a>
                            </form>
                        </td>
                    <% } else { %>
                        <td><%=s.getName()%></td>
                        <td><%=s.getPassport_data()%></td>
                        <td><%=s.getPhone_number()%></td>
                        <td><%=s.getStaff_book_number()%></td>
                        <td><%=s.getWork_experience()%></td>
                        <td>
                            <div style="display: flex; justify-content: center;">
                                <a class="icon-btn edit" href="staff?editId=<%=s.getId()%>&searchType=<%=sType%>&searchValue=<%=sValue%>&page=${currentPage}">✏</a>
                                <form method="post" action="staff" style="margin:0;">
                                    <input type="hidden" name="id" value="<%=s.getId()%>">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="searchType" value="<%=sType%>">
                                    <input type="hidden" name="searchValue" value="<%=sValue%>">
                                    <input type="hidden" name="page" value="${currentPage}">
                                    <button class="icon-btn delete" onclick="return confirm('Удалить?')">🗑</button>
                                </form>
                            </div>
                        </td>
                    <% } %>
                </tr>
            <% } } else { %>
                <tr><td colspan="7" style="padding: 30px; color: #777;">Нет данных</td></tr>
            <% } %>
            </tbody>
        </table>

        <div class="pagination">
            <%
                int current = (request.getAttribute("currentPage") != null) ? (int)request.getAttribute("currentPage") : 1;
                int total = (request.getAttribute("totalPages") != null) ? (int)request.getAttribute("totalPages") : 1;
                String searchParams = "&searchType=" + (sType != null ? sType : "") + "&searchValue=" + sValue;
            %>
            <c:if test="${totalPages > 1}">
                <a href="?page=1<%=searchParams%>">««</a>
                <a href="?page=<%=Math.max(1,current-1)%><%=searchParams%>">«</a>
                <span><%=current%> / <%=total%></span>
                <a href="?page=<%=Math.min(total,current+1)%><%=searchParams%>">»</a>
                <a href="?page=<%=total%><%=searchParams%>">»»</a>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>