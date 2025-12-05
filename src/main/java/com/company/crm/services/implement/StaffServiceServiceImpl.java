package com.company.crm.services.implement;

import com.company.crm.dao.interfaces.StaffDao;
import com.company.crm.models.Staff;
import com.company.crm.services.interfaces.StaffService;

import java.util.List;

public class StaffServiceServiceImpl implements StaffService {

    private final StaffDao repository;

    public StaffServiceServiceImpl(StaffDao repository) {
        this.repository = repository;
    }

    public List<Staff> deleteStaffInRange(int startId, int endId) {
        return repository.deleteStaffInRange(startId, endId);
    }

    @Override
    public void add(Staff staff) {
        repository.add(staff);
    }

    @Override
    public Staff findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Staff> getAll() {
        return repository.getAll();
    }

    @Override
    public Staff update(Staff updated) {
        boolean ok = repository.update(updated);
        return ok ? updated : null;
    }

    @Override
    public Staff delete(int id) {
        Staff found = repository.findById(id);
        if (found == null) return null;
        return repository.delete(id) ? found : null;
    }
}
