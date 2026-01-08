package com.company.crm.web.servlet.user;

import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
import com.company.crm.models.Booking;
import com.company.crm.models.Client;
import com.company.crm.services.implement.BookingServiceImpl;
import com.company.crm.services.implement.ClientServiceImpl;
import java.util.Comparator; // <--- Добавить эту строку
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal; // ВАЖНО
import java.nio.charset.StandardCharsets; // ВАЖНО
import java.time.LocalDate; // ВАЖНО
import java.time.LocalDateTime;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet("/bookings")
public class BookingListServlet extends HttpServlet {

    private BookingServiceImpl bookingService;
    private ClientServiceImpl clientService;

    @Override
    public void init() {
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String searchType = req.getParameter("searchType");
        String searchValue = req.getParameter("searchValue");
        int page = parseInt(req.getParameter("page"), 1);
        int size = 10;

        List<Booking> allBookings = bookingService.getAll();
        List<Client> allClients = clientService.getAll();

        Map<Integer, Client> clientMap = allClients.stream()
                .collect(Collectors.toMap(Client::getId, Function.identity(), (existing, replacement) -> existing));

        // --- БЛОК ФИЛЬТРАЦИИ ---
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            String val = searchValue.toLowerCase().trim();
            allBookings = allBookings.stream().filter(b -> {
                switch (searchType != null ? searchType : "") {
                    case "id": return String.valueOf(b.getId()).equals(val);
                    case "room": return String.valueOf(b.getIdLivingRoom()).equals(val);
                    default: return true;
                }
            }).collect(Collectors.toList());
        }

        // --- ДОБАВИТЬ СОРТИРОВКУ ЗДЕСЬ ---
        // Сортируем список по ID (от меньшего к большему) перед нарезкой на страницы
        if (allBookings != null) {
            allBookings.sort(Comparator.comparingInt(Booking::getId));
        }
        // ---------------------------------

        // --- БЛОК ПАГИНАЦИИ ---
        int totalItems = allBookings.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / size));
        if (page > totalPages) page = totalPages;
        if (page < 1) page = 1;

        int from = (page - 1) * size;
        int to = Math.min(from + size, totalItems);

        req.setAttribute("bookings", allBookings.subList(from, to));
        req.setAttribute("clientList", allClients);
        // ... остальной код без изменений
        req.setAttribute("clientMap", clientMap);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchType", searchType);
        req.setAttribute("searchValue", searchValue);

        req.getRequestDispatcher("/WEB-INF/views/booking-list.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name()); // Исправлено
        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                Booking b = new Booking(); // ЗДЕСЬ НУЖЕН ПУСТОЙ КОНСТРУКТОР (см. пункт 2 ниже)

                b.setIdClient(parseInt(req.getParameter("clientId"), 0));
                b.setIdLivingRoom(parseInt(req.getParameter("roomId"), 0));
                b.setIdStaff(parseInt(req.getParameter("staffId"), 0));

                // ИСПРАВЛЕНИЕ: Parse LocalDate (HTML5 date input returns YYYY-MM-DD string)
                b.setArrivalDate(LocalDate.parse(req.getParameter("arrival")));
                b.setDepartureDate(LocalDate.parse(req.getParameter("departure")));

                b.setNumberGuests(parseInt(req.getParameter("guests"), 1));

                // ИСПРАВЛЕНИЕ: BigDecimal
                String priceStr = req.getParameter("price");
                b.setPrice(priceStr != null && !priceStr.isEmpty() ? new BigDecimal(priceStr) : BigDecimal.ZERO);

                b.setStatus(true);
                b.setBookingTime(LocalDateTime.now());

                bookingService.add(b);

            } else if ("update".equals(action)) {
                Booking b = bookingService.findById(parseInt(req.getParameter("id"), 0));
                if (b != null) {
                    b.setIdClient(parseInt(req.getParameter("clientId"), b.getIdClient()));
                    b.setIdLivingRoom(parseInt(req.getParameter("roomId"), b.getIdLivingRoom()));
                    b.setIdStaff(parseInt(req.getParameter("staffId"), b.getIdStaff()));

                    // ИСПРАВЛЕНИЕ: LocalDate
                    b.setArrivalDate(LocalDate.parse(req.getParameter("arrival")));
                    b.setDepartureDate(LocalDate.parse(req.getParameter("departure")));

                    b.setNumberGuests(parseInt(req.getParameter("guests"), b.getNumberGuests()));

                    // ИСПРАВЛЕНИЕ: BigDecimal
                    String priceStr = req.getParameter("price");
                    if (priceStr != null && !priceStr.isEmpty()) {
                        b.setPrice(new BigDecimal(priceStr));
                    }

                    b.setStatus(Boolean.parseBoolean(req.getParameter("status")));

                    bookingService.update(b);
                }

            } else if ("delete".equals(action)) {
                bookingService.delete(parseInt(req.getParameter("id"), 0));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Для отладки можно оставить, но лучше использовать Logger
        }

        String sValue = req.getParameter("searchValue");
        String encodedSearch = (sValue != null) ? URLEncoder.encode(sValue, StandardCharsets.UTF_8.name()) : "";
        String params = "?page=" + req.getParameter("page") +
                "&searchType=" + req.getParameter("searchType") +
                "&searchValue=" + encodedSearch;

        resp.sendRedirect("bookings" + params);
    }

    private int parseInt(String val, int def) {
        try { return Integer.parseInt(val); } catch (Exception e) { return def; }
    }
}