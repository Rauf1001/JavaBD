//package com.company.crm.web.servlet.admin;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter("/admin/*")
//public class AdminFilter implements Filter {
//    public void doFilter(...) {
//        HttpSession session = req.getSession(false);
//        if (session == null || !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//        chain.doFilter(req, resp);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//    }
//}
