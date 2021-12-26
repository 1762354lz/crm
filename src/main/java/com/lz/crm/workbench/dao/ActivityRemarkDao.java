package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityRemarkDao {
    int saveRemark(ActivityRemark activityRemark);

    List<ActivityRemark> pageRemarkList(@Param("pageSize") Integer pageSize, @Param("pageNum") Integer pageNum, @Param("activityId") String activityId);

    int remarkTost(String activityId);

    int updateRemark(ActivityRemark activityRemark);

    int deleteRemark(String id);
}
