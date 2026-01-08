package com.company.crm.web.servlet.user;

import com.company.crm.models.LivingRoom;
import com.company.crm.services.implement.LivingRoomServiceImpl;
import com.company.crm.dao.jdbc.LivingRoomDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/livingrooms")
public class LivingRoomServlet extends HttpServlet {

    private LivingRoomServiceImpl roomService;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() {
        roomService = new LivingRoomServiceImpl(new LivingRoomDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pageParam = req.getParameter("page");
        String searchType = req.getParameter("searchType");
        String searchValue = req.getParameter("searchValue");

        int currentPage = parse(pageParam, 1);
        List<LivingRoom> allRooms = roomService.getAll();

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            allRooms = allRooms.stream().filter(r -> {
                String val = searchValue.toLowerCase();
                switch (searchType != null ? searchType : "") {
                    case "id": return String.valueOf(r.getId()).equals(val);
                    case "room_number": return r.getRoom_number().toLowerCase().contains(val);
                    case "location": return r.getLocation().toLowerCase().contains(val);
                    default: return true;
                }
            }).collect(Collectors.toList());
        }

        int totalRooms = allRooms.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalRooms / PAGE_SIZE));

        if (currentPage > totalPages) currentPage = totalPages;
        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalRooms);

        req.setAttribute("rooms", allRooms.subList(startIndex, endIndex));
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchType", searchType);
        req.setAttribute("searchValue", searchValue);

        req.getRequestDispatcher("/WEB-INF/views/livingroom-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        // Для сохранения состояния
        String sType = req.getParameter("searchType");
        String sValue = req.getParameter("searchValue");
        String page = req.getParameter("page");

        try {
            if ("add".equals(action)) {
                roomService.add(new LivingRoom(
                        req.getParameter("room_number"),
                        req.getParameter("location"),
                        Integer.parseInt(req.getParameter("status"))
                ));
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                LivingRoom room = roomService.findById(id);
                if (room != null) {
                    room.setRoom_number(req.getParameter("room_number"));
                    room.setLocation(req.getParameter("location"));
                    room.setStatus(Integer.parseInt(req.getParameter("status")));
                    roomService.update(room);
                }
            } else if ("delete".equals(action)) {
                roomService.delete(Integer.parseInt(req.getParameter("id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Редирект назад с сохранением параметров
        StringBuilder url = new StringBuilder(req.getContextPath() + "/livingrooms?");
        if (sType != null && !sType.isEmpty()) url.append("searchType=").append(sType).append("&");
        if (sValue != null && !sValue.isEmpty()) {
            url.append("searchValue=").append(URLEncoder.encode(sValue, "UTF-8")).append("&");
        }
        if (page != null) url.append("page=").append(page);

        resp.sendRedirect(url.toString());
    }

    private int parse(String val, int def) {
        try { return Integer.parseInt(val); } catch (Exception e) { return def; }
    }
}