package com.company.crm.dao.interfaces;

import com.company.crm.models.LivingRoom;

import java.util.List;

public interface LivingRoomDao extends GenericDao<LivingRoom> {


    boolean existsByRoomNumber(String roomnumber);

    List<LivingRoom> deleteLivingRoomsInRange(int startId, int endId);
}
