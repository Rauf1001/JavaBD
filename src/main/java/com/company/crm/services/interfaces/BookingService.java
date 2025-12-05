package com.company.crm.services.interfaces;

import com.company.crm.models.Booking;

import java.util.List;

public interface BookingService extends GenericService<Booking> {

    List<Booking> deleteBookingsInRange(int startId, int endId);

}
