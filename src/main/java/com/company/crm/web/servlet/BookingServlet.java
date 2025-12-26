package com.company.crm.web.servlet;

import com.company.crm.models.Booking;
import com.company.crm.models.Client;
import com.company.crm.models.GroupApplication;
import com.company.crm.models.LivingRoom;
import com.company.crm.services.implement.BookingServiceImpl;
import com.company.crm.services.implement.ClientServiceImpl;
import com.company.crm.services.implement.GroupApplicationServiceImpl;
import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.dao.jdbc.BookingDaoJdbcImpl;
import com.company.crm.dao.jdbc.ClientDaoJdbcImpl;
import com.company.crm.dao.jdbc.GroupApplicationDaoJdbcImpl;
import com.company.crm.dao.jdbc.LivingRoomDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private ClientServiceImpl clientService;
    private BookingServiceImpl bookingService;
    private GroupApplicationServiceImpl groupApplicationService;
    private LivingRoomDao livingRoomDao;

    @Override
    public void init() {
        clientService = new ClientServiceImpl(new ClientDaoJdbcImpl());
        bookingService = new BookingServiceImpl(new BookingDaoJdbcImpl());
        groupApplicationService = new GroupApplicationServiceImpl(new GroupApplicationDaoJdbcImpl());
        livingRoomDao = new LivingRoomDaoJdbcImpl();
    }

    // Показать форму бронирования
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        List<LivingRoom> rooms = livingRoomDao.getAll();
        req.setAttribute("rooms", rooms);

        req.getRequestDispatcher("/WEB-INF/views/booking.jsp").forward(req, resp);
    }

    // Обработать форму
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // 1. Создаем клиента
        Client client = new Client(
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("phone"),
                req.getParameter("passport"),
                LocalDate.parse(req.getParameter("birthDate"))
        );
        clientService.add(client); // Сохраняем через сервис
        System.out.println("Сохраняем клиента: " + client);

        // 2. Создаем group application
        GroupApplication groupApplication = new GroupApplication(
                Integer.parseInt(req.getParameter("livingRoomId")), // id комнаты
                LocalDate.parse(req.getParameter("arrivalDate")),
                LocalDate.parse(req.getParameter("departureDate")),
                BigDecimal.valueOf(3000), // цена, можно потом динамически
                true, // статус
                ""    // комментарий
        );
        groupApplicationService.add(groupApplication); // Сохраняем
        int groupAppId = groupApplication.getId();      // Получаем сгенерированный ID
        System.out.println("Сохраняем group application: " + groupApplication);

        // 3. Создаем бронирование
        Booking booking = new Booking(
                0, // id будет сгенерирован базой
                client.getId(),
                Integer.parseInt(req.getParameter("livingRoomId")),
                1, // staff, пока заглушка
                groupAppId, // корректный внешний ключ
                LocalDate.parse(req.getParameter("arrivalDate")),
                LocalDate.parse(req.getParameter("departureDate")),
                Integer.parseInt(req.getParameter("guests")),
                LocalDateTime.now(),
                true,
                BigDecimal.valueOf(3000)
        );
        bookingService.add(booking); // Сохраняем
        System.out.println("Сохраняем бронирование: " + booking);

        // 4. Показываем страницу успеха
        req.setAttribute("message", "Вы успешно забронировали номер");
        req.getRequestDispatcher("/WEB-INF/views/success.jsp").forward(req, resp);
    }
}
