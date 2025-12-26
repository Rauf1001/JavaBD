//package com.company.crm.web.servlet.admin;
//
//import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
//import com.company.crm.models.Client;
//import com.company.crm.services.implement.ClientServiceImpl;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.time.LocalDate;
//import java.util.List;
//
//@WebServlet("/admin/client")
//public class AdminClientServlet extends HttpServlet {
//
//    private ClientServiceImpl clientService;
//
//    @Override
//    public void init() {
//        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        List<Client> clients = clientService.getAll();
//        req.setAttribute("clients", clients);
//
//        // ⬇️ ВАЖНО: forward, а не redirect
//        req.getRequestDispatcher("/WEB-INF/jsp/admin/client.jsp")
//                .forward(req, resp);
//    }
//}
