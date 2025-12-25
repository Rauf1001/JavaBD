package com.company.crm.services.implement;

import com.company.crm.dao.interfaces.GroupApplicationDao;
import com.company.crm.models.GroupApplication;
import com.company.crm.services.interfaces.GroupApplicationService;

import java.util.List;

public class GroupApplicationServiceImpl implements GroupApplicationService {
    private final GroupApplicationDao repository;

    public GroupApplicationServiceImpl(GroupApplicationDao repository) {
        this.repository = repository;
    }

    public List<GroupApplication> deleteGroup_applicationInRange(int startId, int endId) {
        return repository.deleteGroup_applicationInRange(startId, endId);
    }

    public void add(GroupApplication g) { repository.add(g); }
    public  List<GroupApplication> getAll() { return repository.getAll(); }
    public GroupApplication findById(int id) { return repository.findById(id); }

    public GroupApplication update(GroupApplication updated) {
        boolean ok = repository.update(updated);
        return ok ? updated : null;
    }

    public GroupApplication delete(int id) {
        GroupApplication found = repository.findById(id);
        if (found == null) return null;
        return repository.delete(id) ? found : null;
    }


}
