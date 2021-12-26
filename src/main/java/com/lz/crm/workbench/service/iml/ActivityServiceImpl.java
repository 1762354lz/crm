package com.lz.crm.workbench.service.iml;

import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.dao.ActivityDao;
import com.lz.crm.workbench.dao.ActivityRemarkDao;
import com.lz.crm.workbench.domain.Activity;
import com.lz.crm.workbench.domain.ActivityRemark;
import com.lz.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private ActivityRemarkDao activityRemarkDao;
    public Boolean saveActivityOne(Activity activity) {

      boolean bool=true;
        int num=activityDao.saveActivityOne(activity);
        if (num!=1)
            bool=false;

        return bool;
    }

    public List<Activity> getActivityList() {

        List<Activity> activityList=activityDao.getActivityList();
    return activityList;
    }


    public PageActivityVo<Activity> activityPageList(Map<String,Object> map) {
        PageActivityVo<Activity> pageActivityVo=new PageActivityVo();
        List<Activity> activityList=activityDao.activityPageList(map);
        int total=activityDao.activityListTost(map);
        System.out.println("##############"+total);

       pageActivityVo.setTotal(total);
       pageActivityVo.setActivityList(activityList);
        return pageActivityVo;
    }

    public Boolean deleteActivity(String[] activityDelId) {
        Boolean flag=true;
        int nums=activityDao.deleteActivity(activityDelId);
        if (nums!=activityDelId.length)
            flag=false;
           return flag;
    }

    public Activity detailActivity(String activityDetailId) {

       Activity activity=activityDao.detailActivity(activityDetailId);
       return activity;
    }

    public Activity getActivityOne(String activityId) {

        Activity activity=activityDao.getActivityOne(activityId);
        return activity;
    }

    public Boolean updateActivity(Activity activity) {
        Boolean flag=true;
        int num=activityDao.updateActivity(activity);
        if (num!=1)
            flag=false;
        return flag;
    }

    public Boolean saveRemark(ActivityRemark activityRemark) {
        Boolean flag=true;
        int num=activityRemarkDao.saveRemark(activityRemark);
        if (num!=1)
            flag=false;
        return flag;

    }

    public PageActivityVo<ActivityRemark> pageRemarkList(Integer pageNum, Integer pageSize,String activityId) {
            PageActivityVo<ActivityRemark> pageActivityVo=new PageActivityVo();
            List<ActivityRemark> remarkList=activityRemarkDao.pageRemarkList(pageSize,pageNum,activityId);
            int total=activityRemarkDao.remarkTost(activityId);
            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(remarkList);
            return pageActivityVo;
        }

    public Boolean updateRemark(ActivityRemark activityRemark) {
        Boolean flag=true;
        int num=activityRemarkDao.updateRemark(activityRemark);
        if (num!=1)
            flag=false;
        return flag;
    }

    public Boolean deleteRemark(String id) {
            Boolean flag=true;
            int nums=activityRemarkDao.deleteRemark(id);
            if (nums!=1)
                flag=false;
            return flag;

    }

}



