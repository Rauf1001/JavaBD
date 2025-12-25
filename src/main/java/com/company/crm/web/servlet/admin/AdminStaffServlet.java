package com.company.crm.web.servlet.admin;

import com.company.crm.models.Staff;
import com.company.crm.services.implement.StaffServiceImpl;
import com.company.crm.dao.jdbc.StaffDaoJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/staff")
public class AdminStaffServlet extends HttpServlet {

    private StaffServiceImpl staffService;

    @Override
    public void init() throws ServletException {
        StaffDaoJdbcImpl staffDao = new StaffDaoJdbcImpl();
        staffService = new StaffServiceImpl(staffDao);
    }

    // Получение всех сотрудников или одного по ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String idParam = request.getParameter("id");

            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Staff staff = staffService.findById(id);
                if (staff != null) {
                    out.println("<h2>Сотрудник с ID " + id + "</h2>");
                    out.printf("<p>%s</p>", staff.toString());
                } else {
                    out.println("<p>Сотрудник с ID " + id + " не найден</p>");
                }
            } else {
                List<Staff> staffList = staffService.getAll();
                out.println("<h2>Список сотрудников</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Имя</th><th>Паспорт</th><th>Телефон</th><th>Номер трудовой</th><th>Опыт работы</th></tr>");
                for (Staff s : staffList) {
                    out.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%d</td></tr>",
                            s.getId(), s.getName(), s.getPassport_data(), s.getPhone_number(),
                            s.getStaff_book_number(), s.getWork_experience());
                }
                out.println("</table>");
            }
        }
    }

    // Добавление нового сотрудника
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String passport = request.getParameter("passport");
        String phone = request.getParameter("phone");
        String staffBook = request.getParameter("staffBook");
        int workExperience = Integer.parseInt(request.getParameter("workExperience"));

        Staff newStaff = new Staff(name, passport, phone, staffBook, workExperience);
        staffService.add(newStaff);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<p>Сотрудник успешно добавлен!</p>");
            out.println("<a href='" + request.getContextPath() + "/admin/staff'>Вернуться к списку</a>");
        }
    }

    // Обновление существующего сотрудника
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff existingStaff = staffService.findById(id);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (existingStaff == null) {
                out.println("<p>Сотрудник с ID " + id + " не найден</p>");
                return;
            }

            String name = request.getParameter("name");
            String passport = request.getParameter("passport");
            String phone = request.getParameter("phone");
            String staffBook = request.getParameter("staffBook");
            int workExperience = Integer.parseInt(request.getParameter("workExperience"));

            Staff updatedStaff = new Staff(id, name, passport, phone, staffBook, workExperience);
            Staff result = staffService.update(updatedStaff);

            if (result != null) {
                out.println("<p>Сотрудник успешно обновлен!</p>");
            } else {
                out.println("<p>Ошибка при обновлении сотрудника.</p>");
            }
            out.println("<a href='" + request.getContextPath() + "/admin/staff'>Вернуться к списку</a>");
        }
    }

    // Удаление сотрудника
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff deletedStaff = staffService.delete(id);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (deletedStaff != null) {
                out.println("<p>Сотрудник успешно удален: " + deletedStaff.getName() + "</p>");
            } else {
                out.println("<p>Сотрудник с ID " + id + " не найден</p>");
            }
            out.println("<a href='" + request.getContextPath() + "/admin/staff'>Вернуться к списку</a>");
        }
    }
}
