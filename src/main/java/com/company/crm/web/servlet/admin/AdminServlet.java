package com.company.crm.web.servlet.admin;

import com.company.crm.models.*;
import com.company.crm.services.implement.*;
import com.company.crm.dao.jdbc.*;

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

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    private ClientServiceImpl clientService;
    private BookingServiceImpl bookingService;
    private LivingRoomServiceImpl roomService;
    private StaffServiceImpl staffService;
    private GroupApplicationServiceImpl groupService;

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        roomService = new LivingRoomServiceImpl(new LivingRoomDaoJdbcImpl());
        staffService = new StaffServiceImpl(new StaffDaoJdbcImpl());
        groupService = new GroupApplicationServiceImpl(new GroupApplicationDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // ================= ПОИСК ПЕРСОНАЛА ПО ФИО =================
        String staffSearch = req.getParameter("staffSearch");

        List<Staff> allStaff = staffService.getAll();

        List<Staff> filteredStaff = new java.util.ArrayList<>();

        if (staffSearch != null && !staffSearch.trim().isEmpty()) {
            String searchLower = staffSearch.toLowerCase();

            for (Staff s : allStaff) {
                if (s.getName() != null &&
                        s.getName().toLowerCase().contains(searchLower)) {
                    filteredStaff.add(s);
                }
            }
        } else {
            filteredStaff = allStaff;
        }

        // ================= ПЕРЕДАЁМ В JSP =================
        req.setAttribute("clients", clientService.getAll());
        req.setAttribute("bookings", bookingService.getAll());
        req.setAttribute("rooms", roomService.getAll());
        req.setAttribute("groups", groupService.getAll());

        // ВАЖНО: staff — уже отфильтрованный
        req.setAttribute("staff", filteredStaff);

        // чтобы вернуть текст в поле поиска
        req.setAttribute("staffSearch", staffSearch);

        req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);
    }

    // ======================= POST =======================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String[] pathParts = req.getPathInfo().split("/");

        if (pathParts.length < 3) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }

        String entity = pathParts[1];
        String action = pathParts[2];

        switch (entity) {
            case "client": handleClient(req, action); break;
            case "booking": handleBooking(req, action); break;
            case "room": handleRoom(req, action); break;
            case "staff": handleStaff(req, action); break;
            case "group": handleGroup(req, action); break;
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }

    // ======================= CLIENT =======================
    private void handleClient(HttpServletRequest req, String action) {
        if ("add".equals(action)) {
            Client c = new Client(
                    req.getParameter("name"),
                    req.getParameter("email"),
                    req.getParameter("phone"),
                    req.getParameter("passport"),
                    null
            );
            clientService.add(c);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Client c = clientService.findById(id);
            if (c != null) {
                c.setName(req.getParameter("name"));
                c.setPhone_number(req.getParameter("phone"));
                c.setEmail(req.getParameter("email"));
                c.setPassport_data(req.getParameter("passport"));
                clientService.update(c);
            }

        } else if ("delete".equals(action)) {
            clientService.delete(Integer.parseInt(req.getParameter("id")));
        }
    }

    // ======================= BOOKING =======================
    private void handleBooking(HttpServletRequest req, String action) {
        if ("add".equals(action)) {
            Booking b = new Booking(
                    0,
                    Integer.parseInt(req.getParameter("idClient")),
                    Integer.parseInt(req.getParameter("idLivingRoom")),
                    1,
                    0,
                    LocalDate.parse(req.getParameter("arrivalDate")),
                    LocalDate.parse(req.getParameter("departureDate")),
                    Integer.parseInt(req.getParameter("guests")),
                    LocalDateTime.now(),
                    true,
                    new BigDecimal(req.getParameter("price"))
            );
            bookingService.add(b);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Booking b = bookingService.findById(id);
            if (b != null) {
                b.setIdClient(Integer.parseInt(req.getParameter("idClient")));
                b.setIdLivingRoom(Integer.parseInt(req.getParameter("idLivingRoom")));
                b.setArrivalDate(LocalDate.parse(req.getParameter("arrivalDate")));
                b.setDepartureDate(LocalDate.parse(req.getParameter("departureDate")));
                b.setNumberGuests(Integer.parseInt(req.getParameter("guests")));
                b.setPrice(new BigDecimal(req.getParameter("price")));
                bookingService.update(b);
            }

        } else if ("delete".equals(action)) {
            bookingService.delete(Integer.parseInt(req.getParameter("id")));
        }
    }

    // ======================= ROOM =======================
    private void handleRoom(HttpServletRequest req, String action) {
        if ("add".equals(action)) {
            LivingRoom r = new LivingRoom(
                    req.getParameter("room_number"),
                    req.getParameter("location"),
                    Integer.parseInt(req.getParameter("status"))
            );
            roomService.add(r);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            LivingRoom r = roomService.findById(id);
            if (r != null) {
                r.setRoom_number(req.getParameter("room_number"));
                r.setLocation(req.getParameter("location"));
                r.setStatus(Integer.parseInt(req.getParameter("status")));
                roomService.update(r);
            }

        } else if ("delete".equals(action)) {
            roomService.delete(Integer.parseInt(req.getParameter("id")));
        }
    }

    // ======================= STAFF =======================
    private void handleStaff(HttpServletRequest req, String action) {
        if ("add".equals(action)) {
            Staff s = new Staff(
                    req.getParameter("name"),
                    req.getParameter("passport_data"),
                    req.getParameter("phone_number"),
                    "",
                    Integer.parseInt(req.getParameter("work_experience"))
            );
            staffService.add(s);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Staff s = staffService.findById(id);
            if (s != null) {
                s.setName(req.getParameter("name"));
                s.setPassport_data(req.getParameter("passport_data"));
                s.setPhone_number(req.getParameter("phone_number"));
                s.setWork_experience(Integer.parseInt(req.getParameter("work_experience")));
                staffService.update(s);
            }

        } else if ("delete".equals(action)) {
            staffService.delete(Integer.parseInt(req.getParameter("id")));
        }
    }

    // ======================= GROUP =======================
    private void handleGroup(HttpServletRequest req, String action) {
        if ("add".equals(action)) {
            GroupApplication g = new GroupApplication(
                    Integer.parseInt(req.getParameter("idLivingRoom")),
                    LocalDate.parse(req.getParameter("arrivalDate")),
                    LocalDate.parse(req.getParameter("departureDate")),
                    new BigDecimal(req.getParameter("price")),
                    Boolean.parseBoolean(req.getParameter("status")),
                    req.getParameter("comment")
            );
            groupService.add(g);

        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            GroupApplication g = groupService.findById(id);
            if (g != null) {
                g.setIdLivingRoom(Integer.parseInt(req.getParameter("idLivingRoom")));
                g.setArrivalDate(LocalDate.parse(req.getParameter("arrivalDate")));
                g.setDepartureDate(LocalDate.parse(req.getParameter("departureDate")));
                g.setPrice(new BigDecimal(req.getParameter("price")));
                g.setStatus(Boolean.parseBoolean(req.getParameter("status")));
                g.setComment(req.getParameter("comment"));
                groupService.update(g);
            }

        } else if ("delete".equals(action)) {
            groupService.delete(Integer.parseInt(req.getParameter("id")));
        }
    }
}
