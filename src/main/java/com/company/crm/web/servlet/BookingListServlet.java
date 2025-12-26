package com.company.crm.web.servlet;

import com.company.crm.models.Booking;
import com.company.crm.models.Client;
import com.company.crm.services.implement.BookingServiceImpl;
import com.company.crm.services.implement.ClientServiceImpl;
import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bookings")
public class BookingListServlet extends HttpServlet {

    private BookingServiceImpl bookingService;
    private ClientServiceImpl clientService;

    @Override
    public void init() {
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        clientService  = new ClientServiceImpl(new ClientDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Booking> bookings = bookingService.getAll();
        List<Map<String, Object>> rows = new ArrayList<>();

        for (Booking b : bookings) {
            Client c = clientService.findById(b.getIdClient());

            Map<String, Object> row = new HashMap<>();
            row.put("fullName", c.getName());
            row.put("phone", c.getPhone());
            row.put("email", c.getEmail());
            row.put("passport", c.getPassport());
            row.put("arrival", b.getArrivalDate());
            row.put("departure", b.getDepartureDate());
            row.put("guests", b.getNumberGuests());

            rows.add(row);
        }

        req.setAttribute("rows", rows);
        req.getRequestDispatcher("/WEB-INF/views/booking-list.jsp")
                .forward(req, resp);
    }
}
