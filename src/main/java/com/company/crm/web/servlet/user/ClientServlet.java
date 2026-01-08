package com.company.crm.web.servlet.user;

import com.company.crm.models.Client;
import com.company.crm.services.implement.ClientServiceImpl;
import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    private ClientServiceImpl clientService;
    private static final int PAGE_SIZE = 15;
    private static final Logger LOGGER = Logger.getLogger(ClientServlet.class.getName());

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Параметры поиска
        String searchType = req.getParameter("searchType");
        String searchValue = req.getParameter("searchValue");
        if (searchValue == null) searchValue = "";

        // Параметры страницы
        String pageParam = req.getParameter("page");
        int currentPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        // Действия (удаление)
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null) clientService.delete(Integer.parseInt(idStr));
        }

        List<Client> allClients = clientService.getAll();

        // --- ЛОГИКА ПОИСКА ---
        if (allClients != null && !searchValue.isEmpty()) {
            final String finalValue = searchValue.toLowerCase();
            allClients = allClients.stream().filter(c -> {
                if ("id".equals(searchType)) return String.valueOf(c.getId()).equals(finalValue);
                if ("name".equals(searchType)) return c.getName().toLowerCase().contains(finalValue);
                if ("phone".equals(searchType)) return c.getPhone_number().contains(finalValue);
                return true;
            }).collect(Collectors.toList());
        }

        if (allClients != null) {
            allClients.sort(Comparator.comparingInt(Client::getId));
        }

        int totalClients = (allClients != null) ? allClients.size() : 0;
        int totalPages = Math.max(1, (int) Math.ceil((double) totalClients / PAGE_SIZE));
        if (currentPage > totalPages) currentPage = totalPages;
        if (currentPage < 1) currentPage = 1;

        int startIndex = (currentPage - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, totalClients);

        req.setAttribute("clients", (allClients != null && totalClients > 0) ? allClients.subList(startIndex, endIndex) : null);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("searchType", searchType);
        req.setAttribute("searchValue", searchValue);

        req.getRequestDispatcher("/WEB-INF/views/client-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String action = req.getParameter("action");

        String searchType = req.getParameter("searchType");
        String searchValue = req.getParameter("searchValue");
        String page = req.getParameter("page");
        if (page == null) page = "1";

        String redirectParams = String.format("?page=%s&searchType=%s&searchValue=%s",
                page, (searchType != null ? searchType : ""), (searchValue != null ? searchValue : ""));

        try {
            if ("add".equals(action)) {
                clientService.add(new Client(
                        req.getParameter("name"),
                        req.getParameter("email"),
                        req.getParameter("phone"),
                        req.getParameter("passport"),
                        (req.getParameter("birth_date") != null && !req.getParameter("birth_date").isEmpty())
                                ? LocalDate.parse(req.getParameter("birth_date")) : null
                ));
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Client client = clientService.findById(id);
                if (client != null) {
                    client.setName(req.getParameter("name"));
                    client.setEmail(req.getParameter("email"));
                    client.setPhone_number(req.getParameter("phone"));
                    client.setPassport_data(req.getParameter("passport"));
                    if (req.getParameter("birth_date") != null && !req.getParameter("birth_date").isEmpty()) {
                        client.setBirth_date(LocalDate.parse(req.getParameter("birth_date")));
                    }
                    clientService.update(client);
                }
            } else if ("delete".equals(action)) { // ТЕПЕРЬ УДАЛЕНИЕ ЗДЕСЬ
                String idStr = req.getParameter("id");
                if (idStr != null) {
                    clientService.delete(Integer.parseInt(idStr));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in Client POST", e);
        }
        resp.sendRedirect(req.getContextPath() + "/clients" + redirectParams);
    }
}