//package com.company.crm.web.servlet.admin;
//
//import com.company.crm.dao.jdbc.GroupApplicationDaoJdbcImpl;
//import com.company.crm.models.GroupApplication;
//import com.company.crm.services.implement.GroupApplicationServiceImpl;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@WebServlet("/admin/group-application")
//public class AdminGroupApplicationServlet extends HttpServlet {
//
//    private GroupApplicationServiceImpl groupService;
//
//    @Override
//    public void init() {
//        groupService = new GroupApplicationServiceImpl(
//                new GroupApplicationDaoJdbcImpl()
//        );
//    }
//
//    // ===================== GET =====================
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        resp.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//
//        String idParam = req.getParameter("id");
//
//        if (idParam != null) {
//            int id = Integer.parseInt(idParam);
//            GroupApplication g = groupService.findById(id);
//
//            if (g == null) {
//                out.println("<p>Групповая заявка не найдена</p>");
//                return;
//            }
//
//            out.println("<h2>Групповая заявка</h2>");
//            out.println("<pre>" + g + "</pre>");
//            return;
//        }
//
//        List<GroupApplication> list = groupService.getAll();
//
//        out.println("<h2>Все групповые заявки</h2>");
//        out.println("<table border='1'>");
//        out.println("<tr>");
//        out.println("<th>ID</th>");
//        out.println("<th>ID LivingRoom</th>");
//        out.println("<th>Arrival</th>");
//        out.println("<th>Departure</th>");
//        out.println("<th>Price</th>");
//        out.println("<th>Status</th>");
//        out.println("<th>Comment</th>");
//        out.println("</tr>");
//
//        for (GroupApplication g : list) {
//            out.println("<tr>");
//            out.println("<td>" + g.getId() + "</td>");
//            out.println("<td>" + g.getIdLivingRoom() + "</td>");
//            out.println("<td>" + g.getArrivalDate() + "</td>");
//            out.println("<td>" + g.getDepartureDate() + "</td>");
//            out.println("<td>" + g.getPrice() + "</td>");
//            out.println("<td>" + g.isStatus() + "</td>");
//            out.println("<td>" + g.getComment() + "</td>");
//            out.println("</tr>");
//        }
//
//        out.println("</table>");
//    }
//
//    // ===================== POST =====================
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        GroupApplication g = new GroupApplication(
//                Integer.parseInt(req.getParameter("idLivingRoom")),
//                LocalDate.parse(req.getParameter("arrivalDate")),
//                LocalDate.parse(req.getParameter("departureDate")),
//                new BigDecimal(req.getParameter("price")),
//                Boolean.parseBoolean(req.getParameter("status")),
//                req.getParameter("comment")
//        );
//
//        groupService.add(g);
//
//        resp.getWriter().println("Групповая заявка добавлена успешно");
//    }
//
//    // ===================== PUT =====================
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        int id = Integer.parseInt(req.getParameter("id"));
//        GroupApplication existing = groupService.findById(id);
//
//        if (existing == null) {
//            resp.getWriter().println("Групповая заявка не найдена");
//            return;
//        }
//
//        existing.setIdLivingRoom(Integer.parseInt(req.getParameter("idLivingRoom")));
//        existing.setArrivalDate(LocalDate.parse(req.getParameter("arrivalDate")));
//        existing.setDepartureDate(LocalDate.parse(req.getParameter("departureDate")));
//        existing.setPrice(new BigDecimal(req.getParameter("price")));
//        existing.setStatus(Boolean.parseBoolean(req.getParameter("status")));
//        existing.setComment(req.getParameter("comment"));
//
//        GroupApplication updated = groupService.update(existing);
//
//        resp.getWriter().println(
//                updated != null
//                        ? "Групповая заявка обновлена"
//                        : "Ошибка обновления"
//        );
//    }
//
//    // ===================== DELETE =====================
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        int id = Integer.parseInt(req.getParameter("id"));
//        GroupApplication deleted = groupService.delete(id);
//
//        resp.getWriter().println(
//                deleted != null
//                        ? "Групповая заявка удалена"
//                        : "Групповая заявка не найдена"
//        );
//    }
//}
