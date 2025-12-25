package com.company.crm.web.servlet.admin;

import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
import com.company.crm.models.Client;
import com.company.crm.services.implement.ClientServiceImpl;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private ClientServiceImpl clientService;

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Client client = new Client(
                req.getParameter("name"),                  // имя
                req.getParameter("email"),                 // email (будет использоваться как пароль)
                req.getParameter("phone_number"),
                req.getParameter("passport_data"),
                LocalDate.parse(req.getParameter("birth_date"))
        );

        clientService.add(client);

        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
