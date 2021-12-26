package com.lz.crm.workbench.web;

import com.lz.crm.settings.domain.User;
import com.lz.crm.settings.service.UserService;
import com.lz.crm.utils.DateUtil;
import com.lz.crm.utils.UuidUtil;
import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.domain.Activity;
import com.lz.crm.workbench.domain.ActivityRemark;
import com.lz.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/workbench/activity")
@Controller
public class ActivityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @ResponseBody
    @RequestMapping("/getUserList.do")
    public List getUserList(){
        System.out.println("ActivityController-------");
       List<User> userList=userService.getUserList();
        return userList;

    }
    @RequestMapping("/saveActivityOne.do")
    @ResponseBody
    public Boolean saveActivityOne(Activity activity, HttpServletRequest request)
    {

        activity.setId(new UuidUtil().getUUID());
        activity.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        activity.setCreateTime(new DateUtil().getDate());
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        activity.setEditTime(new DateUtil().getDate());
       Boolean bool=activityService.saveActivityOne(activity);
        ;
        return bool;
    }
    @ResponseBody
    @RequestMapping("/getActivityList.do")
    public List getActivityList(){
     List<Activity> activityList=activityService.getActivityList();
        return activityList;
    }
    @ResponseBody
    @RequestMapping("/activityPageList.do")
    public PageActivityVo<Activity> activityPageList(@RequestParam Map<String,Object> map){

      int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
      pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
   int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Activity> activityList=activityService.activityPageList(map);
        return activityList;
    }
    @ResponseBody
    @RequestMapping("/pageRemarkList.do")
    public PageActivityVo<ActivityRemark> pageRemarkList(Integer pageNum,Integer pageSize,String activityId) {


        pageNum = (pageNum - 1) * pageSize;
        System.out.println(pageNum);

        PageActivityVo<ActivityRemark> remarkList = activityService.pageRemarkList(pageNum, pageSize,activityId);
        return remarkList;
    }
    @ResponseBody
    @RequestMapping("/deleteActivity.do")
    public Boolean deleteActivity(@RequestParam("params[]") String[] activityDelId){
        Boolean flag;
       flag= activityService.deleteActivity(activityDelId);

        return flag;
    }
    @ResponseBody
    @RequestMapping("/deleteRemark.do")
    public Boolean deleteRemark(String id){
        Boolean flag;
        flag= activityService.deleteRemark(id);

        return flag;
    }
    @ResponseBody
    @RequestMapping("/detailActivity.do")
    public ModelAndView detailActivity(String activityDetailId){
ModelAndView modelAndView=new ModelAndView();
Activity activity=activityService.detailActivity(activityDetailId);
        System.out.println("----"+activityDetailId);
modelAndView.addObject("activity",activity);
modelAndView.setViewName("workbench/activity/detail");

        return modelAndView;
    }

    @RequestMapping("/detailActivity2.do")
    public String detailActivity2(String activityDetailId){

        return "redirect:detail.jsp";
    }

    @ResponseBody
    @RequestMapping("/getActivityOne.do")
    public Activity getActivityOne(String activityId){
        Activity activity=activityService.getActivityOne(activityId);
        return activity;

    }
    @ResponseBody
    @RequestMapping("/updateActivity.do")
    public Map<String,Object> updateActivity(Activity activity, HttpServletRequest request){
        Map<String,Object> map=new HashMap<String, Object>();
        activity.setEditTime(new DateUtil().getDate());
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        Boolean bool=activityService.updateActivity(activity);
        map.put("bool",bool);
        map.put("activity",activity);
        return map;
    }
    @ResponseBody
    @RequestMapping("/saveRemark.do")
    public Boolean saveRemark(ActivityRemark activityRemark, HttpServletRequest request){
        activityRemark.setId(new UuidUtil().getUUID());
        activityRemark.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setCreateTime(new DateUtil().getDate());
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        activityRemark.setEditTime(new DateUtil().getDate());

        Boolean bool=activityService.saveRemark(activityRemark);
        return bool;
    }
    @ResponseBody
    @RequestMapping("/updateRemark.do")
    public Boolean updateRemark(ActivityRemark activityRemark, HttpServletRequest request){
        activityRemark.setEditTime(new DateUtil().getDate());
        activityRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        Boolean bool=activityService.updateRemark(activityRemark);

        return bool;
    }

}
