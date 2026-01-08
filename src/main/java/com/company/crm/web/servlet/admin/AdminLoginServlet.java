package com.company.crm.web.servlet.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminLoginServlet extends HttpServlet {

    private static final String COOKIE_NAME = "admin_auth_token";
    private static final String COOKIE_VALUE = "access_granted_secret_key"; // В реальности здесь был бы токен
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASS = "admin";

    // GET: Проверяем, залогинен ли уже пользователь (есть ли куки)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Проверяем наличие куки
        boolean isLoggedIn = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (COOKIE_NAME.equals(c.getName()) && COOKIE_VALUE.equals(c.getValue())) {
                    isLoggedIn = true;
                    break;
                }
            }
        }

        // 2. Логика перенаправления
        if (isLoggedIn) {
            // Если куки есть -> сразу показываем Админ Панель (твою страницу admin-dashboard.jsp)
            req.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(req, resp);
        } else {
            // Если куки нет -> показываем Форму Входа (новую страницу admin-login.jsp)
            req.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(req, resp);
        }
    }

    // POST: Обработка формы входа (логин и пароль)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        // Проверка логина и пароля
        if (ADMIN_LOGIN.equals(login) && ADMIN_PASS.equals(pass)) {
            // УСПЕХ: Создаем куки
            Cookie authCookie = new Cookie(COOKIE_NAME, COOKIE_VALUE);

            // Настройка куки
            authCookie.setMaxAge(24 * 60 * 60); // 24 часа в секундах
            authCookie.setPath("/"); // Кука доступна для всего приложения
            authCookie.setHttpOnly(true); // Защита от JS скриптов (безопасность)

            // Добавляем куки в ответ
            resp.addCookie(authCookie);

            // Перезагружаем страницу (GET запрос), чтобы сработала проверка в doGet и пустила в панель
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            // ОШИБКА: Возвращаем на форму входа с сообщением
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(req, resp);
        }
    }
}