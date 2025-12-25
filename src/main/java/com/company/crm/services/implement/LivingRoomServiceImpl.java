package com.company.crm.services.implement;

import com.company.crm.dao.interfaces.LivingRoomDao;
import com.company.crm.models.LivingRoom;
import com.company.crm.services.interfaces.LivingRoomService;

import java.util.List;

public class LivingRoomServiceImpl implements LivingRoomService {

    private final LivingRoomDao repository;

    public LivingRoomServiceImpl(LivingRoomDao repository) {
        this.repository = repository;
    }

    @Override
    public boolean isRoomNumberTaken(String roomNumber) {
        return repository.existsByRoomNumber(roomNumber);
    }

    @Override
    public List<LivingRoom> deleteLivingRoomsInRange(int startId, int endId) {
        return repository.deleteLivingRoomsInRange(startId, endId);
    }

    @Override
    public void add(LivingRoom living_room) {
        repository.add(living_room);
    }

    @Override
    public LivingRoom findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<LivingRoom> getAll() {
        return repository.getAll();
    }

    @Override
    public LivingRoom update(LivingRoom updated) {
        boolean ok = repository.update(updated);
        return ok ? updated : null;
    }

    @Override
    public LivingRoom delete(int id) {
        LivingRoom found = repository.findById(id);
        if (found == null) return null;
        return repository.delete(id) ? found : null;
    }
}
