package com.company.crm.web.servlet.user;

import com.company.crm.dao.interfaces.*;
import com.company.crm.dao.jdbc.*;
import com.company.crm.models.*;
import com.company.crm.services.implement.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/clients-info")
public class ClientsInfoServlet extends HttpServlet {
    private BookingServiceImpl bookingService;
    private ClientServiceImpl clientService;
    private LivingRoomDao livingRoomDao;
    private StaffDao staffDao;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() {
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
        livingRoomDao = new LivingRoomDaoJdbcImpl();
        staffDao = new StaffDaoJdbcImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        if(req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));

        List<Booking> allBookings = bookingService.getAll();
        int total = allBookings.size();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        int fromIndex = (page - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, total);

        List<Booking> pagedBookings = allBookings.subList(fromIndex, toIndex);

        // Мапы для названий
        Map<Integer, String> clientMap = clientService.getAll().stream().collect(Collectors.toMap(Client::getId, Client::getName, (a,b)->a));
        Map<Integer, String> roomMap = livingRoomDao.getAll().stream().collect(Collectors.toMap(LivingRoom::getId, LivingRoom::getRoom_number, (a,b)->a));
        Map<Integer, String> staffMap = staffDao.getAll().stream().collect(Collectors.toMap(Staff::getId, Staff::getName, (a,b)->a));

        req.setAttribute("bookings", pagedBookings);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("clientMap", clientMap);
        req.setAttribute("roomMap", roomMap);
        req.setAttribute("staffMap", staffMap);
        req.getRequestDispatcher("/WEB-INF/views/clients-info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int id = Integer.parseInt(req.getParameter("id"));

        if ("update".equals(action)) {
            Booking b = bookingService.findById(id);
            b.setArrivalDate(LocalDate.parse(req.getParameter("arrival")));
            b.setDepartureDate(LocalDate.parse(req.getParameter("departure")));
            b.setNumberGuests(Integer.parseInt(req.getParameter("guests")));
            bookingService.update(b);
        } else if ("delete".equals(action)) {
            bookingService.delete(id);
        }
        resp.sendRedirect("clients-info?page=" + req.getParameter("page"));
    }
}