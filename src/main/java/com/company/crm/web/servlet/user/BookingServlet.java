package com.company.crm.web.servlet.user;

import com.company.crm.models.*;
import com.company.crm.services.implement.*;
import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.dao.jdbc.*;
import com.company.crm.utils.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private ClientServiceImpl clientService;
    private BookingServiceImpl bookingService;
    private GroupApplicationServiceImpl groupApplicationService;
    private LivingRoomDao livingRoomDao;

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        groupApplicationService = new GroupApplicationServiceImpl(new GroupApplicationDaoJdbcImpl());
        livingRoomDao = new LivingRoomDaoJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        loadPage(req, resp);
    }

    private void loadPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<LivingRoom> rooms = livingRoomDao.getAll();
        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String clientName = req.getParameter("name");
            int roomId = Integer.parseInt(req.getParameter("livingRoomId"));
            LocalDate arrivalDate = LocalDate.parse(req.getParameter("arrivalDate"));
            LocalDate departureDate = LocalDate.parse(req.getParameter("departureDate"));

            if (departureDate.isBefore(arrivalDate) || departureDate.isEqual(arrivalDate)) {
                req.setAttribute("errorMessage", "Дата выезда должна быть позже даты заезда.");
                loadPage(req, resp);
                return;
            }

            if (!isRoomFree(roomId, arrivalDate, departureDate)) {
                req.setAttribute("errorMessage", "В выбранные даты этот номер уже занят.");
                loadPage(req, resp);
                return;
            }

            // Создаем клиента
            Client client = new Client(
                    clientName,
                    req.getParameter("email"),
                    req.getParameter("phone"),
                    req.getParameter("passport"),
                    LocalDate.parse(req.getParameter("birthDate"))
            );
            clientService.add(client);

            // Групповая заявка
            GroupApplication groupApp = new GroupApplication(roomId, arrivalDate, departureDate, BigDecimal.valueOf(3000), true, "");
            groupApplicationService.add(groupApp);

            // Бронирование
            Booking booking = new Booking(0, client.getId(), roomId, 1, groupApp.getId(), arrivalDate, departureDate,
                    Integer.parseInt(req.getParameter("guests")), LocalDateTime.now(), true, BigDecimal.valueOf(3000));
            bookingService.add(booking);

            // УСТАНОВКА КУКИ (после успешного бронирования)
            Cookie userCookie = new Cookie("user_fio", URLEncoder.encode(clientName, "UTF-8"));
            userCookie.setMaxAge(60 * 60 * 24 * 30); // 30 дней
            userCookie.setPath("/");
            resp.addCookie(userCookie);

            // Передаем объекты для success.jsp
            req.setAttribute("booking", booking);
            req.setAttribute("client", client);
            req.setAttribute("room", livingRoomDao.findById(roomId));
            req.setAttribute("message", "Вы успешно забронировали номер!");

            req.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Ошибка: " + e.getMessage());
            loadPage(req, resp);
        }
    }

    private boolean isRoomFree(int roomId, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM Booking WHERE ID_Living_room = ? AND Arrival_date < ? AND Departure_date > ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, java.sql.Date.valueOf(end));
            stmt.setDate(3, java.sql.Date.valueOf(start));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}