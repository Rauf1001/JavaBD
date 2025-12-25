package com.company.crm.web.servlet.admin;

import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
import com.company.crm.models.Client;
import com.company.crm.services.implement.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private ClientServiceImpl clientService;

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password"); // в нашем случае email

// LoginServlet.java
        if ("admin".equals(username) && "admin".equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("isAdmin", true);

            resp.sendRedirect(req.getContextPath() + "/admin/client");
            return;
        }




        // проверка обычного пользователя
        List<Client> clients = clientService.getAll();
        for (Client client : clients) {
            if (client.getName().equals(username) && client.getEmail().equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("clientId", client.getId());
                resp.sendRedirect(req.getContextPath() + "/client/client.jsp");
                return;
            }
        }



    }
}
