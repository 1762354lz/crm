package com.lz.crm.workbench.web;

import com.lz.crm.settings.domain.User;
import com.lz.crm.utils.DateUtil;
import com.lz.crm.utils.UuidUtil;
import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.domain.*;
import com.lz.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequestMapping("/workbench/clue")
@Controller
public class ClueController {
    @Resource
    private ClueService clueService;
    @ResponseBody
    @RequestMapping("/getCustomerName.do")
    public List<String> getCustomerName(String name){
        List<String> cname=clueService.getCustomerName(name);
        return cname;
    }
    @ResponseBody
    @RequestMapping("/pageClueRemarkList.do")
    public PageActivityVo<ClueRemark> pageClueRemarkList(Integer pageNum, Integer pageSize, String clueId) {


        pageNum = (pageNum - 1) * pageSize;
        System.out.println(pageNum);

        PageActivityVo<ClueRemark> remarkList = clueService.pageClueRemarkList(pageNum, pageSize,clueId);
        return remarkList;
    }
    @ResponseBody
    @RequestMapping("/ListTransactionHistory.do")
    public PageActivityVo<TranHistory> ListTransactionHistory(HttpServletRequest request, Integer pageNum, Integer pageSize, String tranId) {


        pageNum = (pageNum - 1) * pageSize;
        System.out.println(pageNum);

        PageActivityVo<TranHistory> remarkList = clueService.ListTransactionHistory(request,pageNum, pageSize,tranId);

        return remarkList;
    }
    @ResponseBody
    @RequestMapping("/saveClueRemark.do")
    public Boolean saveClueRemark(ClueRemark clueRemark, HttpServletRequest request){
        clueRemark.setId(new UuidUtil().getUUID());
        clueRemark.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        clueRemark.setCreateTime(new DateUtil().getDate());
        clueRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        clueRemark.setEditTime(new DateUtil().getDate());

        Boolean bool=clueService.saveClueRemark(clueRemark);
        return bool;
    }
    @ResponseBody
    @RequestMapping("/convert.do")
    public ModelAndView convert(Tran tran, String clueId, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        clueService.convert(tran,clueId,request);
        modelAndView.setViewName("workbench/clue/index");
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping("/save.do")
    public Boolean save(Clue clue, HttpServletRequest request)
    {

        clue.setId(new UuidUtil().getUUID());
        clue.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        clue.setCreateTime(new DateUtil().getDate());
        clue.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        clue.setEditTime(new DateUtil().getDate());
        Boolean bool=clueService.save(clue);
        return bool;
    }
    @ResponseBody
    @RequestMapping("/getClueList.do")
    public PageActivityVo<Clue> getClueList(@RequestParam Map<String,Object> map){

        int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
        pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
        int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Clue> clueList=clueService.getClueList(map);
        return clueList;
    }
    @ResponseBody
    @RequestMapping("/ListTransaction.do")
    public PageActivityVo<Tran> ListTransaction(@RequestParam Map<String,Object> map){

        int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
        pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
        int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Tran> clueList=clueService.ListTransaction(map);
        return clueList;
    }

    @ResponseBody
    @RequestMapping("/saveClueActivity.do")
    public Boolean saveClueActivity(@RequestParam("activityId[]")String[] activityId,String clueId){
        Boolean flag;

        flag= clueService.saveClueActivity(activityId,clueId);

        return flag;
    }

    @RequestMapping("/saveTransaction.do")
    public ModelAndView saveTransaction(Tran tran, HttpServletRequest request){
        Boolean flag;
ModelAndView modelAndView=new ModelAndView();
        flag= clueService.saveTransaction(tran,request);
if (flag)
       modelAndView.setViewName("workbench/clue/transaction");
return modelAndView;
    }
    @ResponseBody
    @RequestMapping("/saveCostumer.do")
    public Boolean saveCostumer(Customer customer, HttpServletRequest request){
        Boolean flag;
        customer.setId(new UuidUtil().getUUID());
        customer.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        customer.setCreateTime(new DateUtil().getDate());
        customer.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        customer.setEditTime(new DateUtil().getDate());
        flag= clueService.saveCostumer(customer);

        return flag;
    }
    @ResponseBody
    @RequestMapping("/showClueActivity.do")
    public PageActivityVo<Activity> showClueActivity(@RequestParam Map<String,Object> map){

        int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
        pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
        int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Activity> activityList=clueService.showClueActivity(map);
        return activityList;
    }
    @ResponseBody
    @RequestMapping("/activityPageListRelate.do")
    public PageActivityVo<Activity> activityPageListRelate(@RequestParam Map<String,Object> map){

        int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
        pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
        int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Activity> activityList=clueService.activityPageListRelate(map);
        return activityList;
    }
    @ResponseBody
    @RequestMapping("/deleteRelate.do")
    public Boolean deleteRelate(String activityId,String clueId){
        Boolean flag;
        flag= clueService.deleteRelate(activityId,clueId);

        return flag;
    }

    @RequestMapping("/getClueOne.do")

    public ModelAndView getClueOne(String cid){
        ModelAndView modelAndView=new ModelAndView();
        Clue clue=clueService.getClueOne(cid);
        System.out.println("----"+cid);
        modelAndView.addObject("clue",clue);
        modelAndView.setViewName("workbench/clue/convert");

        return modelAndView;
    }
    @ResponseBody
    @RequestMapping("/contactsPageList.do")
    public PageActivityVo<Contacts> contactsPageList(@RequestParam Map<String,Object> map){

        int pn=Integer.valueOf((String)map.get("pageNum"));
        System.out.println(pn);
        pn-=1;
        int ps=Integer.valueOf((String)map.get("pageSize"));
        int pageNum=pn*ps;
        System.out.println(pageNum);
        map.put("pageNum",pageNum);

        map.put("pageSize",Integer.valueOf((String)map.get("pageSize")));
        System.out.println(map);
        PageActivityVo<Contacts> contactsList=clueService.contactsPageList(map);
        return contactsList;
    }
    @ResponseBody
    @RequestMapping("/detailTransaction.do")
    public ModelAndView detailTransaction(String tranId, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        Tran tran=clueService.detailTransaction(tranId);

        Map<String,String> map=(Map<String,String>)request.getSession().getServletContext().getAttribute("stringMap");
        System.out.println(map);
        System.out.println(map.get(tran.getStage()));
        tran.setPossibility( map.get(tran.getStage()));
        modelAndView.addObject("tran",tran);

        modelAndView.setViewName("workbench/transaction/detail");

        return modelAndView;
    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Tran changeStage(Tran tran, HttpServletRequest request){
        Map<String,String> map= (Map<String,String>) request.getSession().getServletContext().getAttribute("stringMap");
        String possibility= map.get(tran.getStage());
        tran.setPossibility(possibility);
        tran.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        tran.setCreateTime(new DateUtil().getDate());
        tran.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setEditTime(new DateUtil().getDate());
        clueService.changeStage(tran);

        return tran;
    }
    @ResponseBody
    @RequestMapping("/getChars.do")
    public PageActivityVo<Map<String,Object>> getChars() {


        PageActivityVo<Map<String,Object>> remarkList = clueService.getChars();
        return remarkList;
    }
}
