package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int saveActivityOne(Activity activity);

    List<Activity> getActivityList();

    List<Activity> activityPageList(Map<String, Object> map);
    int activityListTost(Map<String, Object> map);

    int deleteActivity(String[] activityDelId);

    Activity detailActivity(String activityDetailId);

    Activity getActivityOne(String activityId);

    int updateActivity(Activity activity);



}
