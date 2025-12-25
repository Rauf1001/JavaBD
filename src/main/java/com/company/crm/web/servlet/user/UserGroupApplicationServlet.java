package com.company.crm.web.servlet.user;

import com.company.crm.dao.jdbc.GroupApplicationDaoJdbcImpl;
import com.company.crm.models.GroupApplication;
import com.company.crm.services.implement.GroupApplicationServiceImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/webapp1/client/group-application")
public class UserGroupApplicationServlet extends HttpServlet {

    private GroupApplicationServiceImpl service;

    @Override
    public void init() {
        service = new GroupApplicationServiceImpl(
                new GroupApplicationDaoJdbcImpl()
        );
    }

    // ===================== GET =====================
    // показать все заявки (только просмотр)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("clientId") == null) {
            out.println("<p>Необходимо войти в систему</p>");
            return;
        }

        List<GroupApplication> applications = service.getAll();

        out.println("<h2>Групповые заявки</h2>");
        out.println("<table border='1'>");
        out.println("<tr>"
                + "<th>ID</th>"
                + "<th>Living room</th>"
                + "<th>Arrival</th>"
                + "<th>Departure</th>"
                + "<th>Price</th>"
                + "<th>Status</th>"
                + "<th>Comment</th>"
                + "</tr>");

        for (GroupApplication g : applications) {
            out.println("<tr>"
                    + "<td>" + g.getId() + "</td>"
                    + "<td>" + g.getIdLivingRoom() + "</td>"
                    + "<td>" + g.getArrivalDate() + "</td>"
                    + "<td>" + g.getDepartureDate() + "</td>"
                    + "<td>" + g.getPrice() + "</td>"
                    + "<td>" + (g.isStatus() ? "Подтверждено" : "Ожидание") + "</td>"
                    + "<td>" + g.getComment() + "</td>"
                    + "</tr>");
        }

        out.println("</table>");
    }

    // ===================== POST =====================
    // создание новой групповой заявки
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("clientId") == null) {
            out.println("<p>Необходимо войти в систему</p>");
            return;
        }

        try {
            int livingRoomId = Integer.parseInt(req.getParameter("idLivingRoom"));
            LocalDate arrival = LocalDate.parse(req.getParameter("arrivalDate"));
            LocalDate departure = LocalDate.parse(req.getParameter("departureDate"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            String comment = req.getParameter("comment");

            // ===== ВАЛИДАЦИЯ =====
            if (arrival.isBefore(LocalDate.now())) {
                out.println("<p>Дата заезда не может быть в прошлом</p>");
                return;
            }

            if (!arrival.isBefore(departure)) {
                out.println("<p>Дата выезда должна быть позже даты заезда</p>");
                return;
            }

            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                out.println("<p>Цена должна быть положительной</p>");
                return;
            }

            if (comment == null || comment.trim().length() < 5) {
                out.println("<p>Комментарий должен содержать минимум 5 символов</p>");
                return;
            }

            if (comment.length() > 255) {
                out.println("<p>Комментарий слишком длинный</p>");
                return;
            }

            GroupApplication application = new GroupApplication(
                    livingRoomId,
                    arrival,
                    departure,
                    price,
                    false,          // всегда ожидание
                    comment.trim()
            );

            service.add(application);

            out.println("<p>Групповая заявка успешно создана и ожидает подтверждения</p>");

        } catch (Exception e) {
            out.println("<p>Ошибка данных: " + e.getMessage() + "</p>");
        }
    }
}
