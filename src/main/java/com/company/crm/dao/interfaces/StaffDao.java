package com.company.crm.dao.interfaces;

import com.company.crm.models.Staff;

import java.util.List;

public interface StaffDao extends GenericDao<Staff> {

    List<Staff> deleteStaffInRange(int startId, int endId);
}
