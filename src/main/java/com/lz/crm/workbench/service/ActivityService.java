package com.lz.crm.workbench.service;

import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.domain.Activity;
import com.lz.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Boolean saveActivityOne(Activity activity);

    List<Activity> getActivityList();

    PageActivityVo<Activity> activityPageList(Map<String, Object> map);

    Boolean deleteActivity(String[] id);

    Activity detailActivity(String activityDetailId);

    Activity getActivityOne(String activityId);

    Boolean updateActivity(Activity activity);

    Boolean saveRemark(ActivityRemark activityRemark);

    PageActivityVo<ActivityRemark> pageRemarkList(Integer pageNum, Integer pageSize, String activityId);

    Boolean updateRemark(ActivityRemark activityRemark);

    Boolean deleteRemark(String id);
}
