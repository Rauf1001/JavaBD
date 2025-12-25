package com.company.crm.web.servlet.admin;

import com.company.crm.dao.jdbc.LivingRoomDaoJdbcImpl;
import com.company.crm.models.LivingRoom;
import com.company.crm.services.implement.LivingRoomServiceImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/living-room")
public class AdminLivingRoomServlet extends HttpServlet {

    private LivingRoomServiceImpl livingRoomService;

    @Override
    public void init() {
        livingRoomService = new LivingRoomServiceImpl(
                new LivingRoomDaoJdbcImpl()
        );
    }

    // ===================== GET =====================
    // /admin/living-room
    // /admin/living-room?id=3
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String idParam = req.getParameter("id");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            LivingRoom room = livingRoomService.findById(id);

            if (room == null) {
                out.println("<p>Комната не найдена</p>");
                return;
            }

            out.println("<h2>Жилая комната</h2>");
            out.println("<pre>" + room + "</pre>");
            return;
        }

        List<LivingRoom> rooms = livingRoomService.getAll();
        out.println("<h2>Все жилые комнаты</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Room Number</th><th>Location</th><th>Status</th></tr>");

        for (LivingRoom r : rooms) {
            out.printf(
                    "<tr><td>%d</td><td>%s</td><td>%s</td><td>%d</td></tr>",
                    r.getId(),
                    r.getRoom_number(),
                    r.getLocation(),
                    r.getStatus()
            );
        }
        out.println("</table>");
    }

    // ===================== POST =====================
    // добавление
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String roomNumber = req.getParameter("roomNumber");

        if (livingRoomService.isRoomNumberTaken(roomNumber)) {
            resp.getWriter().println("Номер комнаты уже существует");
            return;
        }

        LivingRoom room = new LivingRoom(
                roomNumber,
                req.getParameter("location"),
                Integer.parseInt(req.getParameter("status"))
        );

        livingRoomService.add(room);

        resp.getWriter().println("Комната добавлена успешно");
    }

    // ===================== PUT =====================
    // обновление
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        LivingRoom existing = livingRoomService.findById(id);

        if (existing == null) {
            resp.getWriter().println("Комната не найдена");
            return;
        }

        existing.setRoom_number(req.getParameter("roomNumber"));
        existing.setLocation(req.getParameter("location"));
        existing.setStatus(Integer.parseInt(req.getParameter("status")));

        LivingRoom updated = livingRoomService.update(existing);

        resp.getWriter().println(
                updated != null ? "Комната обновлена" : "Ошибка обновления"
        );
    }

    // ===================== DELETE =====================
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        LivingRoom deleted = livingRoomService.delete(id);

        resp.getWriter().println(
                deleted != null ? "Комната удалена" : "Комната не найдена"
        );
    }
}
