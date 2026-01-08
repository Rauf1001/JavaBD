package com.company.crm.web.servlet.user;

import com.company.crm.dao.jdbc.StaffDaoJdbcImpl;
import com.company.crm.models.Staff;
import com.company.crm.services.implement.StaffServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/staff")
public class StaffServlet extends HttpServlet {
    private StaffServiceImpl staffService;

    @Override
    public void init() {
        staffService = new StaffServiceImpl(new StaffDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String searchType = req.getParameter("searchType");
        String searchValue = req.getParameter("searchValue");
        int page = parse(req.getParameter("page"), 1);
        int size = parse(req.getParameter("size"), 5); // Размер страницы

        List<Staff> all = staffService.getAll();

        // Фильтрация
        if (searchValue != null && !searchValue.trim().isEmpty()) {
            String val = searchValue.toLowerCase().trim();
            all = all.stream().filter(s -> {
                switch (searchType != null ? searchType : "") {
                    case "id": return String.valueOf(s.getId()).equals(val);
                    case "name": return s.getName().toLowerCase().contains(val);
                    case "book": return s.getStaff_book_number().toLowerCase().contains(val);
                    default: return s.getName().toLowerCase().contains(val);
                }
            }).collect(Collectors.toList());
        }

        // Пагинация
        int totalItems = all.size();
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / size));

        // Защита от выхода за пределы страниц
        if (page > totalPages) page = totalPages;
        if (page < 1) page = 1;

        int from = (page - 1) * size;
        int to = Math.min(from + size, totalItems);

        // Если список пуст или page вышел за пределы из-за фильтра
        if (from > totalItems) {
            from = 0;
            to = 0;
        }

        req.setAttribute("staffs", all.subList(from, to));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchType", searchType);
        req.setAttribute("searchValue", searchValue);

        req.getRequestDispatcher("/WEB-INF/views/staff.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                staffService.add(new Staff(
                        req.getParameter("name"),
                        req.getParameter("passport"),
                        req.getParameter("phone"),
                        req.getParameter("book"),
                        parse(req.getParameter("exp"), 0)
                ));
            } else if ("delete".equals(action)) {
                staffService.delete(parse(req.getParameter("id"), 0));
            } else if ("update".equals(action)) {
                staffService.update(new Staff(
                        parse(req.getParameter("id"), 0),
                        req.getParameter("name"),
                        req.getParameter("passport"),
                        req.getParameter("phone"),
                        req.getParameter("book"),
                        parse(req.getParameter("exp"), 0)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Кодируем параметры для URL
        String sValue = req.getParameter("searchValue");
        String encodedSearch = (sValue != null) ? URLEncoder.encode(sValue, "UTF-8") : "";

        String params = "?page=" + parse(req.getParameter("page"), 1) +
                "&searchType=" + (req.getParameter("searchType") != null ? req.getParameter("searchType") : "") +
                "&searchValue=" + encodedSearch;

        resp.sendRedirect("staff" + params);
    }

    private int parse(String v, int d) {
        try { return Integer.parseInt(v); } catch (Exception e) { return d; }
    }
}