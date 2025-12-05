package com.company.crm.services.interfaces;

import com.company.crm.models.Staff;

import java.util.List;

public interface StaffService  extends GenericService<Staff> {

    List<Staff> deleteStaffInRange(int startId, int endId);

}
