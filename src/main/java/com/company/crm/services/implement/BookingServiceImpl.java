package com.company.crm.services.implement;

import com.company.crm.dao.interfaces.BookingDao;
import com.company.crm.models.Booking;
import com.company.crm.services.interfaces.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDao repository;

    public BookingServiceImpl(BookingDao repository) {
        this.repository = repository;
    }


    public List<Booking> deleteBookingsInRange(int startId, int endId) {
        return repository.deleteBookingInRange(startId, endId);

    }

    public void add(Booking b) {
        repository.add(b);
    }

    public List<Booking> getAll() {
        return repository.getAll();
    }

    public Booking findById(int id) {
        return repository.findById(id);
    }

    public Booking update(Booking updated) {
        boolean ok = repository.update(updated);
        return ok ? updated : null;
    }

    public Booking delete(int id) {
        Booking found = repository.findById(id);
        if (found == null) return null;
        return repository.delete(id) ? found : null;
    }

}
