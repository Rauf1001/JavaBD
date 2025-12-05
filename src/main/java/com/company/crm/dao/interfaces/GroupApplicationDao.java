package com.company.crm.dao.interfaces;

import com.company.crm.models.GroupApplication;

import java.util.List;

public interface GroupApplicationDao extends GenericDao<GroupApplication> {

    List<GroupApplication> deleteGroup_applicationInRange(int startId, int endId);


}
