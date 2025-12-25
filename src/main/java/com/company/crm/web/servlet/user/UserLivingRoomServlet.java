package com.company.crm.web.servlet.user;

import com.company.crm.dao.jdbc.LivingRoomDaoJdbcImpl;
import com.company.crm.models.LivingRoom;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/webapp1/client/living-room")
public class UserLivingRoomServlet extends HttpServlet {

    private LivingRoomDaoJdbcImpl livingRoomService;

    @Override
    public void init() {
        livingRoomService = new LivingRoomDaoJdbcImpl();
    }

    // ===================== GET =====================
    // показать все доступные жилые комнаты
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

        List<LivingRoom> rooms = livingRoomService.getAll();

        out.println("<h2>Доступные жилые комнаты</h2>");
        out.println("<table border='1'>");
        out.println("<tr>"
                + "<th>ID</th>"
                + "<th>Номер комнаты</th>"
                + "<th>Местоположение</th>"
                + "<th>Статус</th>"
                + "</tr>");

        for (LivingRoom r : rooms) {
            String statusText = r.getStatus() == 1 ? "Занято" : "Свободно";
            out.println("<tr>"
                    + "<td>" + r.getId() + "</td>"
                    + "<td>" + r.getRoom_number() + "</td>"
                    + "<td>" + r.getLocation() + "</td>"
                    + "<td>" + statusText + "</td>"
                    + "</tr>");
        }

        out.println("</table>");
    }

    // ===================== POST =====================
    // создание комнаты запрещено для обычного юзера
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<p>Создание комнат запрещено для обычного пользователя</p>");
    }

}
