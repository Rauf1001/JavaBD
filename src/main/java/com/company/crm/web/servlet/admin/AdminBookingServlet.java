package com.company.crm.web.servlet.admin;

import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
import com.company.crm.models.Booking;
import com.company.crm.services.implement.BookingServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@WebServlet("/admin/booking")
public class AdminBookingServlet extends HttpServlet {

    private BookingServiceImpl bookingService;

    @Override
    public void init() {
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
    }

    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute("isAdmin"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // Получаем все бронирования
        List<Booking> bookings = bookingService.getAll();
        req.setAttribute("bookings", bookings);

        // Передаем на JSP
        req.getRequestDispatcher("/WEB-INF/jsp/admin/booking.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            Booking booking = new Booking(
                    Integer.parseInt(req.getParameter("idClient")),
                    Integer.parseInt(req.getParameter("idRoom")),
                    Integer.parseInt(req.getParameter("idStaff")),
                    Integer.parseInt(req.getParameter("idGroup")),
                    LocalDate.parse(req.getParameter("arrivalDate")),
                    LocalDate.parse(req.getParameter("departureDate")),
                    Integer.parseInt(req.getParameter("guests")),
                    LocalDateTime.now(),
                    Boolean.parseBoolean(req.getParameter("status")),
                    new BigDecimal(req.getParameter("price"))
            );

            bookingService.add(booking);

            resp.sendRedirect(req.getContextPath() + "/admin/booking");

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка добавления: " + e.getMessage());
            doGet(req, resp);
        }
    }

    // PUT и DELETE лучше делать через POST с hidden field: _method=PUT или _method=DELETE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // AJAX обработка или через hidden field + POST
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // AJAX обработка или через hidden field + POST
    }
}
