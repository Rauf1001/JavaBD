package com.company.crm.web.servlet;

import com.company.crm.dao.interfaces.BookingDao;
import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookings")
public class BookingListServlet extends HttpServlet {

    private BookingDao bookingDao;

    @Override
    public void init() {
        bookingDao = new BookingDaoJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("bookings", bookingDao.getAll());
        req.getRequestDispatcher("/WEB-INF/views/booking-list.jsp")
                .forward(req, resp);
    }
}
