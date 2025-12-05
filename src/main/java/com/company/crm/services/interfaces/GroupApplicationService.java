package com.company.crm.services.interfaces;

import com.company.crm.models.GroupApplication;

import java.util.List;

public interface GroupApplicationService extends GenericService<GroupApplication> {

    List<GroupApplication> deleteGroup_applicationInRange(int startId, int endId);

}
