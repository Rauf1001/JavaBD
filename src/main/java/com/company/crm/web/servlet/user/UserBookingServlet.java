//package com.company.crm.web.servlet.client;
//
//import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
//import com.company.crm.models.Booking;
//import com.company.crm.services.implement.BookingServiceImpl;
//
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@WebServlet("/webapp1/client/booking")
//public class UserBookingServlet extends HttpServlet {
//
//    private BookingServiceImpl bookingService;
//
//    @Override
//    public void init() {
//        bookingService = new BookingServiceImpl(
//                new BookingDaoJdbcImpl()
//        );
//    }
//
//    // ===================== GET =====================
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        resp.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("clientId") == null) {
//            out.println("<p>Необходимо войти в систему</p>");
//            return;
//        }
//
//        int clientId = (int) session.getAttribute("clientId");
//
//        List<Booking> bookings = bookingService.getAll();
//
//        out.println("<h2>Мои бронирования</h2>");
//        out.println("<table border='1'>");
//        out.println("<tr>"
//                + "<th>ID</th>"
//                + "<th>Room</th>"
//                + "<th>Arrival</th>"
//                + "<th>Departure</th>"
//                + "<th>Guests</th>"
//                + "<th>Status</th>"
//                + "<th>Price</th>"
//                + "</tr>");
//
//        for (Booking b : bookings) {
//            if (b.getIdClient() == clientId) {
//                out.println("<tr>"
//                        + "<td>" + b.getId() + "</td>"
//                        + "<td>" + b.getIdLivingRoom() + "</td>"
//                        + "<td>" + b.getArrivalDate() + "</td>"
//                        + "<td>" + b.getDepartureDate() + "</td>"
//                        + "<td>" + b.getNumberGuests() + "</td>"
//                        + "<td>" + (b.isStatus() ? "Подтверждено" : "Ожидание") + "</td>"
//                        + "<td>" + b.getPrice() + "</td>"
//                        + "</tr>");
//            }
//        }
//        out.println("</table>");
//    }
//
//    // ===================== POST =====================
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        resp.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//
//        HttpSession session = req.getSession(false);
//        if (session == null || session.getAttribute("clientId") == null) {
//            out.println("<p>Необходимо войти в систему</p>");
//            return;
//        }
//
//        try {
//            int clientId = (int) session.getAttribute("clientId");
//            int livingRoomId = Integer.parseInt(req.getParameter("idLivingRoom"));
//            int guests = Integer.parseInt(req.getParameter("numberGuests"));
//
//            LocalDate arrival = LocalDate.parse(req.getParameter("arrivalDate"));
//            LocalDate departure = LocalDate.parse(req.getParameter("departureDate"));
//            BigDecimal price = new BigDecimal(req.getParameter("price"));
//
//            // ===== ВАЛИДАЦИЯ =====
//            if (arrival.isBefore(LocalDate.now())) {
//                out.println("<p>Дата заезда не может быть в прошлом</p>");
//                return;
//            }
//
//            if (!arrival.isBefore(departure)) {
//                out.println("<p>Дата выезда должна быть позже даты заезда</p>");
//                return;
//            }
//
//            if (guests <= 0) {
//                out.println("<p>Количество гостей должно быть больше 0</p>");
//                return;
//            }
//
//            if (price.compareTo(BigDecimal.ZERO) <= 0) {
//                out.println("<p>Цена должна быть положительной</p>");
//                return;
//            }
//
//            Booking booking = new Booking(
//                    clientId,
//                    livingRoomId,
//                    0,              // staff назначает админ
//                    0,              // group application
//                    arrival,
//                    departure,
//                    guests,
//                    LocalDateTime.now(),
//                    false,          // ожидание подтверждения
//                    price
//            );
//
//            bookingService.add(booking);
//
//            out.println("<p>Бронирование успешно создано и ожидает подтверждения</p>");
//
//        } catch (Exception e) {
//            out.println("<p>Ошибка данных: " + e.getMessage() + "</p>");
//        }
//    }
//}
