package com.company.crm.web.servlet.admin;

import com.company.crm.dao.jdbc.*;
import com.company.crm.services.implement.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private BookingServiceImpl bookingService;
    private ClientServiceImpl clientService;
    private GroupApplicationServiceImpl groupService;
    private LivingRoomServiceImpl livingRoomService;
    private StaffServiceImpl staffService;

    @Override
    public void init() {
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
        groupService = new GroupApplicationServiceImpl(new GroupApplicationDaoJdbcImpl());
        livingRoomService = new LivingRoomServiceImpl(new LivingRoomDaoJdbcImpl());
        staffService = new StaffServiceImpl(new StaffDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("isAdmin") == null ||
                !(Boolean) session.getAttribute("isAdmin")) {

            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.setAttribute("bookings", bookingService.getAll());
        req.setAttribute("clients", clientService.getAll());
        req.setAttribute("groupApplications", groupService.getAll());
        req.setAttribute("livingRooms", livingRoomService.getAll());
        req.setAttribute("staff", staffService.getAll());

        // ✅ ВАЖНО: forward, а не redirect
        req.getRequestDispatcher("/admin/admin.jsp").forward(req, resp);
    }

}
