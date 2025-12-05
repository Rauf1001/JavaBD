package com.company.crm.services.interfaces;

import com.company.crm.models.LivingRoom;

import java.util.List;

public interface LivingRoomService extends GenericService<LivingRoom> {

    boolean isRoomNumberTaken(String roomNumber);

    List<LivingRoom> deleteLivingRoomsInRange(int startId, int endId);
}
