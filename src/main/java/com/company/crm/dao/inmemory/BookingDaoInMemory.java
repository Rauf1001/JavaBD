package com.company.crm.dao.inmemory;

import com.company.crm.dao.interfaces.BookingDao;
import com.company.crm.models.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingDaoInMemory implements BookingDao {

    private final List<Booking> database = new ArrayList<>();
    private int idCounter = 1;

    public void addAll(List<Booking> list) {
        for (Booking booking : list) add(booking);
    }

    @Override
    public void add(Booking booking) {
        booking.setId(idCounter++);
        database.add(booking);
    }

    @Override
    public List<Booking> getAll() {
        return new ArrayList<>(database);
    }

    @Override
    public Booking findById(int id) {
        return database.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(Booking updated) {
        Booking existing = findById(updated.getId());
        if (existing == null) return false;

        existing.setIdClient(updated.getIdClient());
        existing.setIdLivingRoom(updated.getIdLivingRoom());
        existing.setIdStaff(updated.getIdStaff());
        existing.setIdGroupApplication(updated.getIdGroupApplication());
        existing.setArrivalDate(updated.getArrivalDate());
        existing.setDepartureDate(updated.getDepartureDate());
        existing.setNumberGuests(updated.getNumberGuests());
        existing.setBookingTime(updated.getBookingTime());
        existing.setStatus(updated.isStatus());
        existing.setPrice(updated.getPrice());

        return true;
    }

    @Override
    public boolean delete(int id) {
        Booking booking = findById(id);
        if (booking == null) return false;
        return database.remove(booking);
    }

    @Override
    public List<Booking> deleteBookingInRange(int startId, int endId) {
        List<Booking> toDelete = new ArrayList<>();

        for (Booking b : database) {
            if (b.getId() >= startId && b.getId() <= endId) {
                toDelete.add(b);
            }
        }

        database.removeAll(toDelete);
        return toDelete;
    }
}
