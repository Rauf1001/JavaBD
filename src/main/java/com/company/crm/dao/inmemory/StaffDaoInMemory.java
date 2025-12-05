package com.company.crm.dao.inmemory;

import com.company.crm.dao.interfaces.StaffDao;
import com.company.crm.models.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffDaoInMemory implements StaffDao {

    private final List<Staff> database = new ArrayList<>();
    private int idCounter = 1;


    public void addAll(List<Staff> list) {
        for (Staff staff : list) add(staff);
    }
    @Override
    public void add(Staff staff) {
        staff.setId(idCounter++);
        database.add(staff);
    }

    @Override
    public List<Staff> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public Staff findById(int id) {
        return database.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(Staff updated) {
        Staff existing = findById(updated.getId());
        if (existing == null) return false;

        existing.setName(updated.getName());
        existing.setPassport_data(updated.getPassport_data());
        existing.setPhone_number(updated.getPhone_number());
        existing.setStaff_book_number(updated.getStaff_book_number());
        existing.setWork_experience(updated.getWork_experience());

        return true;
    }

    @Override
    public boolean delete(int id) {
        Staff existing = findById(id);
        if (existing == null) return false;
        return database.remove(existing);
    }

    @Override
    public List<Staff> deleteStaffInRange(int startId, int endId) {
        List<Staff> toDelete = new ArrayList<>();

        for (Staff s : database) {
            if (s.getId() >= startId && s.getId() <= endId) {
                toDelete.add(s);
            }
        }

        database.removeAll(toDelete);
        return toDelete;
    }
}
