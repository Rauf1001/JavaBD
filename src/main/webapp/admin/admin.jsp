<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="com.company.crm.models.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    if (session == null || session.getAttribute("isAdmin") == null ||
        !(Boolean) session.getAttribute("isAdmin")) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Панель администратора</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        header {
            background: #333;
            color: white;
            padding: 15px;
            text-align: center;
        }
        nav {
            display: flex;
            justify-content: center;
            background: #555;
        }
        nav button {
            padding: 10px 20px;
            margin: 0 5px;
            background: #777;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 3px;
        }
        nav button.active {
            background: #FF9800;
        }
        section {
            display: none;
            padding: 20px;
        }
        section.active {
            display: block;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 8px;
            text-align: center;
        }
        th {
            background: #f0f0f0;
        }
        .form-inline {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 15px;
        }
        .form-inline input, .form-inline select, .form-inline button {
            padding: 5px;
            border-radius: 3px;
            border: 1px solid #ccc;
        }
        .form-inline button {
            background: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        .pagination {
            text-align: center;
            margin: 20px 0;
        }
        .pagination form {
            display: inline-block;
        }
        .pagination input {
            padding: 5px 10px;
            margin: 0 2px;
        }
        .delete-btn {
            background: red;
            color: white;
            border: none;
            cursor: pointer;
            padding: 5px 10px;
            border-radius: 3px;
        }
    </style>
    <script>
        function showSection(sectionId, button) {
            const sections = document.querySelectorAll('section');
            sections.forEach(s => s.classList.remove('active'));
            document.getElementById(sectionId).classList.add('active');

            const buttons = document.querySelectorAll('nav button');
            buttons.forEach(b => b.classList.remove('active'));
            button.classList.add('active');
        }
    </script>
</head>
<body>

<header>
    <h1>Панель администратора</h1>
</header>

<nav>
    <button class="active" onclick="showSection('clientsSection', this)">Клиенты</button>
    <button onclick="showSection('bookingsSection', this)">Бронирования</button>
    <button onclick="showSection('groupApplicationsSection', this)">Групповые заявки</button>
    <button onclick="showSection('livingRoomsSection', this)">Жилые комнаты</button>
    <button onclick="showSection('staffSection', this)">Сотрудники</button>
</nav>

<!-- ================= CLIENTS ================= -->
<section id="clientsSection" class="active">
    <h2>Клиенты</h2>
    <form class="form-inline" action="AdminClientServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="text" name="name" placeholder="Имя" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="text" name="phone_number" placeholder="Телефон" required>
        <input type="text" name="passport_data" placeholder="Паспорт" required>
        <input type="date" name="birth_date" placeholder="Дата рождения" required>
        <button type="submit">Добавить</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Имя</th><th>Email</th><th>Телефон</th><th>Паспорт</th><th>Дата рождения</th><th>Действия</th>
        </tr>
        <%
            List<Client> clients = (List<Client>) request.getAttribute("clients");
            if(clients != null) {
                for(Client c : clients) {
        %>
        <tr>
            <form action="AdminClientServlet" method="post">
                <td><input type="hidden" name="id" value="<%= c.getId() %>"><%= c.getId() %></td>
                <td><input type="text" name="name" value="<%= c.getName() %>" required></td>
                <td><input type="email" name="email" value="<%= c.getEmail() %>" required></td>
                <td><input type="text" name="phone_number" value="<%= c.getPhone_number() %>" required></td>
                <td><input type="text" name="passport_data" value="<%= c.getPassport_data() %>" required></td>
                <td><input type="date" name="birth_date" value="<%= c.getBirth_date() %>" required></td>
                <td>
                    <button type="submit" name="action" value="update">Изменить</button>
                    <button type="submit" class="delete-btn" name="action" value="delete">Удалить</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        %>
    </table>

    <div class="pagination">
        <form action="AdminClientServlet" method="get">
            <label>Страница: <input type="number" name="page" value="<%= request.getAttribute("page") != null ? request.getAttribute("page") : 1 %>" min="1"></label>
            <button type="submit">Перейти</button>
        </form>
    </div>
</section>

<!-- ================= BOOKINGS ================= -->
<section id="bookingsSection">
    <h2>Бронирования</h2>
    <form class="form-inline" action="AdminBookingServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="number" name="idClient" placeholder="ID клиента" required>
        <input type="number" name="idLivingRoom" placeholder="ID комнаты" required>
        <input type="number" name="idStaff" placeholder="ID сотрудника" required>
        <input type="number" name="idGroupApplication" placeholder="ID групповой заявки" required>
        <input type="date" name="arrivalDate" placeholder="Дата заезда" required>
        <input type="date" name="departureDate" placeholder="Дата выезда" required>
        <input type="number" name="numberGuests" placeholder="Гостей" required>
        <input type="number" step="0.01" name="price" placeholder="Цена" required>
        <select name="status">
            <option value="false">Ожидание</option>
            <option value="true">Подтверждено</option>
        </select>
        <button type="submit">Добавить</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Client</th><th>Room</th><th>Staff</th><th>GroupApp</th><th>Arrival</th><th>Departure</th>
            <th>Guests</th><th>Status</th><th>Price</th><th>Действия</th>
        </tr>
        <%
            List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
            if(bookings != null) {
                for(Booking b : bookings) {
        %>
        <tr>
            <form action="AdminBookingServlet" method="post">
                <td><input type="hidden" name="id" value="<%= b.getId() %>"><%= b.getId() %></td>
                <td><input type="number" name="idClient" value="<%= b.getIdClient() %>" required></td>
                <td><input type="number" name="idLivingRoom" value="<%= b.getIdLivingRoom() %>" required></td>
                <td><input type="number" name="idStaff" value="<%= b.getIdStaff() %>" required></td>
                <td><input type="number" name="idGroupApplication" value="<%= b.getIdGroupApplication() %>" required></td>
                <td><input type="date" name="arrivalDate" value="<%= b.getArrivalDate() %>" required></td>
                <td><input type="date" name="departureDate" value="<%= b.getDepartureDate() %>" required></td>
                <td><input type="number" name="numberGuests" value="<%= b.getNumberGuests() %>" required></td>
                <td>
                    <select name="status">
                        <option value="false" <%= !b.isStatus() ? "selected" : "" %>>Ожидание</option>
                        <option value="true" <%= b.isStatus() ? "selected" : "" %>>Подтверждено</option>
                    </select>
                </td>
                <td><input type="number" step="0.01" name="price" value="<%= b.getPrice() %>" required></td>
                <td>
                    <button type="submit" name="action" value="update">Изменить</button>
                    <button type="submit" class="delete-btn" name="action" value="delete">Удалить</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div class="pagination">
        <form action="AdminBookingServlet" method="get">
            <label>Страница: <input type="number" name="page" value="<%= request.getAttribute("bookingPage") != null ? request.getAttribute("bookingPage") : 1 %>" min="1"></label>
            <button type="submit">Перейти</button>
        </form>
    </div>
</section>

<!-- ================= GROUP APPLICATIONS ================= -->
<section id="groupApplicationsSection">
    <h2>Групповые заявки</h2>
    <form class="form-inline" action="AdminGroupApplicationServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="number" name="idLivingRoom" placeholder="ID комнаты" required>
        <input type="date" name="arrivalDate" placeholder="Дата заезда" required>
        <input type="date" name="departureDate" placeholder="Дата выезда" required>
        <input type="number" step="0.01" name="price" placeholder="Цена" required>
        <input type="text" name="comment" placeholder="Комментарий">
        <select name="status">
            <option value="false">Ожидание</option>
            <option value="true">Подтверждено</option>
        </select>
        <button type="submit">Добавить</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Room</th><th>Arrival</th><th>Departure</th><th>Price</th><th>Status</th><th>Comment</th><th>Действия</th>
        </tr>
        <%
            List<GroupApplication> groupApplications = (List<GroupApplication>) request.getAttribute("groupApplications");
            if(groupApplications != null) {
                for(GroupApplication g : groupApplications) {
        %>
        <tr>
            <form action="AdminGroupApplicationServlet" method="post">
                <td><input type="hidden" name="id" value="<%= g.getId() %>"><%= g.getId() %></td>
                <td><input type="number" name="idLivingRoom" value="<%= g.getIdLivingRoom() %>" required></td>
                <td><input type="date" name="arrivalDate" value="<%= g.getArrivalDate() %>" required></td>
                <td><input type="date" name="departureDate" value="<%= g.getDepartureDate() %>" required></td>
                <td><input type="number" step="0.01" name="price" value="<%= g.getPrice() %>" required></td>
                <td>
                    <select name="status">
                        <option value="false" <%= !g.isStatus() ? "selected" : "" %>>Ожидание</option>
                        <option value="true" <%= g.isStatus() ? "selected" : "" %>>Подтверждено</option>
                    </select>
                </td>
                <td><input type="text" name="comment" value="<%= g.getComment() %>"></td>
                <td>
                    <button type="submit" name="action" value="update">Изменить</button>
                    <button type="submit" class="delete-btn" name="action" value="delete">Удалить</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div class="pagination">
        <form action="AdminGroupApplicationServlet" method="get">
            <label>Страница: <input type="number" name="page" value="<%= request.getAttribute("groupPage") != null ? request.getAttribute("groupPage") : 1 %>" min="1"></label>
            <button type="submit">Перейти</button>
        </form>
    </div>
</section>

<!-- ================= LIVING ROOMS ================= -->
<section id="livingRoomsSection">
    <h2>Жилые комнаты</h2>
    <form class="form-inline" action="AdminLivingRoomServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="text" name="room_number" placeholder="Номер комнаты" required>
        <input type="text" name="location" placeholder="Местоположение" required>
        <select name="status">
            <option value="0">Свободна</option>
            <option value="1">Занята</option>
        </select>
        <button type="submit">Добавить</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Номер</th><th>Местоположение</th><th>Статус</th><th>Действия</th>
        </tr>
        <%
            List<LivingRoom> livingRooms = (List<LivingRoom>) request.getAttribute("livingRooms");
            if(livingRooms != null) {
                for(LivingRoom lr : livingRooms) {
        %>
        <tr>
            <form action="AdminLivingRoomServlet" method="post">
                <td><input type="hidden" name="id" value="<%= lr.getId() %>"><%= lr.getId() %></td>
                <td><input type="text" name="room_number" value="<%= lr.getRoom_number() %>" required></td>
                <td><input type="text" name="location" value="<%= lr.getLocation() %>" required></td>
                <td>
                    <select name="status">
                        <option value="0" <%= lr.getStatus() == 0 ? "selected" : "" %>>Свободна</option>
                        <option value="1" <%= lr.getStatus() == 1 ? "selected" : "" %>>Занята</option>
                    </select>
                </td>
                <td>
                    <button type="submit" name="action" value="update">Изменить</button>
                    <button type="submit" class="delete-btn" name="action" value="delete">Удалить</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div class="pagination">
        <form action="AdminLivingRoomServlet" method="get">
            <label>Страница: <input type="number" name="page" value="<%= request.getAttribute("livingPage") != null ? request.getAttribute("livingPage") : 1 %>" min="1"></label>
            <button type="submit">Перейти</button>
        </form>
    </div>
</section>

<!-- ================= STAFF ================= -->
<section id="staffSection">
    <h2>Сотрудники</h2>
    <form class="form-inline" action="AdminStaffServlet" method="post">
        <input type="hidden" name="action" value="add">
        <input type="text" name="name" placeholder="Имя" required>
        <input type="text" name="position" placeholder="Должность" required>
        <button type="submit">Добавить</button>
    </form>

    <table>
        <tr>
            <th>ID</th><th>Имя</th><th>Действия</th>
        </tr>
        <%
            List<Staff> staffList = (List<Staff>) request.getAttribute("staff");
            if(staffList != null) {
                for(Staff s : staffList) {
        %>
        <tr>
            <form action="AdminStaffServlet" method="post">
                <td><input type="hidden" name="id" value="<%= s.getId() %>"><%= s.getId() %></td>
                <td><input type="text" name="name" value="<%= s.getName() %>" required></td>
                <td>
                    <button type="submit" name="action" value="update">Изменить</button>
                    <button type="submit" class="delete-btn" name="action" value="delete">Удалить</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        %>
    </table>
    <div class="pagination">
        <form action="AdminStaffServlet" method="get">
            <label>Страница: <input type="number" name="page" value="<%= request.getAttribute("staffPage") != null ? request.getAttribute("staffPage") : 1 %>" min="1"></label>
            <button type="submit">Перейти</button>
        </form>
    </div>
</section>

</body>
</html>
