package com.company.crm.dao.inmemory;

import com.company.crm.dao.interfaces.GroupApplicationDao;
import com.company.crm.models.GroupApplication;

import java.util.ArrayList;
import java.util.List;

public class GroupApplicationDaoInMemory implements GroupApplicationDao {

    private final List<GroupApplication> database = new ArrayList<>();
    private int idCounter = 1;

    public void addAll(List<GroupApplication> list) {
        for (GroupApplication groupApplication : list) add(groupApplication);
    }

    @Override
    public void add(GroupApplication g) {
        g.setId(idCounter++);
        database.add(g);
    }

    @Override
    public List<GroupApplication> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public GroupApplication findById(int id) {
        return database.stream()
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(GroupApplication updated) {
        GroupApplication existing = findById(updated.getId());
        if (existing == null) return false;

        existing.setIdLivingRoom(updated.getIdLivingRoom());
        existing.setArrivalDate(updated.getArrivalDate());
        existing.setDepartureDate(updated.getDepartureDate());
        existing.setPrice(updated.getPrice());
        existing.setStatus(updated.isStatus());
        existing.setComment(updated.getComment());

        return true;
    }

    @Override
    public boolean delete(int id) {
        GroupApplication g = findById(id);
        if (g == null) return false;
        return database.remove(g);
    }

    @Override
    public List<GroupApplication> deleteGroup_applicationInRange(int startId, int endId) {
        List<GroupApplication> toDelete = new ArrayList<>();

        for (GroupApplication g : database) {
            if (g.getId() >= startId && g.getId() <= endId) {
                toDelete.add(g);
            }
        }

        database.removeAll(toDelete);
        return toDelete;
    }
}
