<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Клиенты</title></head>
<body>

<h2>Список клиентов</h2>

<table border="1">
    <tr>
        <th>ID</th><th>Имя</th><th>Email</th>
    </tr>

    <c:forEach var="c" items="${clients}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>${c.email}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
