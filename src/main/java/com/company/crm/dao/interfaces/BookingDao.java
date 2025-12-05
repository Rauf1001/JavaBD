package com.company.crm.dao.interfaces;

import com.company.crm.models.Booking;

import java.util.List;

public interface BookingDao extends GenericDao<Booking> {

    List<Booking> deleteBookingInRange(int startId, int endId);


}
