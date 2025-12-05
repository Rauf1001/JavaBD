package com.company.crm.dao.inmemory;

import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.models.LivingRoom;

import java.util.ArrayList;
import java.util.List;

public class LivingRoomDaoInMemory implements LivingRoomDao {

    private final List<LivingRoom> database = new ArrayList<>();
    private int idCounter = 1;

    public void addAll(List<LivingRoom> list) {
        for (LivingRoom living_room : list) add(living_room);
    }

    @Override
    public void add(LivingRoom livingRoom) {
        livingRoom.setId(idCounter++);
        database.add(livingRoom);
    }

    @Override
    public List<LivingRoom> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public LivingRoom findById(int id) {
        return database.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(LivingRoom updated) {
        LivingRoom existing = findById(updated.getId());
        if (existing == null) return false;

        existing.setRoom_number(updated.getRoom_number());
        existing.setLocation(updated.getLocation());
        existing.setStatus(updated.getStatus());
        return true;
    }

    @Override
    public boolean delete(int id) {
        LivingRoom existing = findById(id);
        if (existing == null) return false;
        return database.remove(existing);
    }

    @Override
    public boolean existsByRoomNumber(String roomNumber) {
        return database.stream()
                .anyMatch(r -> r.getRoom_number().equalsIgnoreCase(roomNumber));
    }

    @Override
    public List<LivingRoom> deleteLivingRoomsInRange(int startId, int endId) {
        List<LivingRoom> toDelete = new ArrayList<>();

        for (LivingRoom r : database) {
            if (r.getId() >= startId && r.getId() <= endId) {
                toDelete.add(r);
            }
        }

        database.removeAll(toDelete);
        return toDelete;
    }
}
