package com.company.crm.web.servlet;

import com.company.crm.dao.interfaces.BookingDao;
import com.company.crm.dao.interfaces.ClientDao;
import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
import com.company.crm.dao.jdbc.LivingRoomDaoJdbcImpl;
import com.company.crm.models.Booking;
import com.company.crm.models.Client;
import com.company.crm.models.LivingRoom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private ClientDao clientDao;
    private BookingDao bookingDao;
    private LivingRoomDao livingRoomDao;

    @Override
    public void init() {
        clientDao = new ClientDaoJdbcImpl();
        bookingDao = new BookingDaoJdbcImpl();
        livingRoomDao = new LivingRoomDaoJdbcImpl();
    }

    // показать форму
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<LivingRoom> rooms = livingRoomDao.getAll();
        req.setAttribute("rooms", rooms);

        req.getRequestDispatcher("/WEB-INF/views/booking.jsp")
                .forward(req, resp);
    }

    // обработать форму
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Клиент

        Client client = new Client(
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("phone"),
                req.getParameter("passport"),
                LocalDate.parse(req.getParameter("birthDate"))
        );
        clientDao.add(client);

        // 2. Бронирование
        Booking booking = new Booking(
                client.getId(),
                Integer.parseInt(req.getParameter("livingRoomId")),
                0, // staff
                0, // groupApplication
                LocalDate.parse(req.getParameter("arrivalDate")),
                LocalDate.parse(req.getParameter("departureDate")),
                Integer.parseInt(req.getParameter("guests")),
                LocalDateTime.now(),
                true,
                BigDecimal.valueOf(3000) // временно
        );
        bookingDao.add(booking);

        // 3. Успех
        req.setAttribute("message", "Вы успешно забронировали номер");
        req.getRequestDispatcher("/WEB-INF/views/success.jsp")
                .forward(req, resp);
    }
}
