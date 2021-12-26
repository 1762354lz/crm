package com.lz.crm.workbench.service.iml;

import com.lz.crm.settings.domain.User;
import com.lz.crm.utils.DateUtil;
import com.lz.crm.utils.UuidUtil;
import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.dao.ClueDao;
import com.lz.crm.workbench.dao.ContactsDao;
import com.lz.crm.workbench.dao.CustomerDao;
import com.lz.crm.workbench.dao.TranDao;
import com.lz.crm.workbench.domain.*;
import com.lz.crm.workbench.service.ClueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceIml implements ClueService {
    @Resource
    private ClueDao clueDao;
    @Resource
    private ContactsDao contactsDao;
    @Resource
    private CustomerDao customerDao;
    @Resource
    private TranDao tranDao;
    public Boolean save(Clue clue) {

            boolean bool=true;
            int num=clueDao.save(clue);
            if (num!=1)
                bool=false;

            return bool;
        }

    public PageActivityVo<Clue> getClueList(Map<String, Object> map) {

            PageActivityVo<Clue> pageActivityVo=new PageActivityVo();
            List<Clue> clueList=clueDao.getClueList(map);
            int total=clueDao.clueListTost(map);
            System.out.println("##############"+total);

            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(clueList);
            return pageActivityVo;

    }
    public Boolean saveClueActivity(String[] activityId, String clueId) {
        Boolean flag=true;
        for (String aId:activityId
             ) {
            String id=new UuidUtil().getUUID();
            int nums=clueDao.saveClueActivity(aId,clueId,id);
            if (nums!=1)
               return flag=false;
        }

        return flag;
    }

    public PageActivityVo<Activity> showClueActivity(Map<String, Object> map) {

        PageActivityVo<Activity> pageActivityVo=new PageActivityVo();
        List<Activity> activityList=clueDao.showClueActivity(map);
        int total=clueDao.showClueActivityTost(map);
        System.out.println("##############"+total);

        pageActivityVo.setTotal(total);
        pageActivityVo.setActivityList(activityList);
        return pageActivityVo;
    }

    public PageActivityVo<Activity> activityPageListRelate(Map<String, Object> map) {
        PageActivityVo<Activity> pageActivityVo=new PageActivityVo();
        List<Activity> activityList=clueDao.activityPageListRelate(map);
        int total=clueDao.activityPageListRelateTost(map);
        System.out.println("##############"+total);

        pageActivityVo.setTotal(total);
        pageActivityVo.setActivityList(activityList);
        return pageActivityVo;
    }

    public Boolean deleteRelate(String activityId,String clueId) {
            Boolean flag=true;
            int nums=clueDao.deleteRelate(activityId,clueId);
            if (nums!=1)
                flag=false;
            return flag;

        }

    public Clue getClueOne(String id) {

            Clue clue=clueDao.getClueOne(id);
            return clue;
        }

    public Boolean saveCostumer(Customer customer) {

            boolean bool=true;
            int num=clueDao.saveCostumer(customer);
            if (num!=1)
                bool=false;

            return bool;
        }

    public void convert(Tran tran, String clueId, HttpServletRequest request) {
        Clue clue=clueDao.getClueOne(clueId);
        Customer customer=customerDao.getCustomerOne(clue.getCompany());

        if(customer==null)
        { customer=new Customer();
        customer.setId(new UuidUtil().getUUID());
        customer.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        customer.setCreateTime(new DateUtil().getDate());
        customer.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        customer.setEditTime(new DateUtil().getDate());
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setDescription(clue.getDescription());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(clue.getOwner());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
            customerDao.saveCostumer(customer);
        }
        Contacts contacts=new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setId(new UuidUtil().getUUID());
        contacts.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        contacts.setCreateTime(new DateUtil().getDate());
        contacts.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        contacts.setEditTime(new DateUtil().getDate());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCustomerId(customer.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());

        contactsDao.saveContacts(contacts);
        if (tran.toString()!=null) {
            tran.setContactsId(contacts.getId());
            tran.setContactSummary(clue.getContactSummary());
            tran.setId(new UuidUtil().getUUID());
            tran.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
            tran.setCreateTime(new DateUtil().getDate());
            tran.setEditBy(((User)request.getSession().getAttribute("user")).getName());
            tran.setEditTime(new DateUtil().getDate());
            tran.setCustomerId(customer.getId());
            tran.setDescription(clue.getDescription());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setOwner(clue.getOwner());
            tran.setSource(clue.getSource());

            tranDao.saveTran(tran);
            TranHistory tranHistory=new TranHistory();
            tranHistory.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
            tranHistory.setCreateTime(new DateUtil().getDate());
            tranHistory.setId(new UuidUtil().getUUID());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setPossibility(tran.getPossibility());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());

            tranDao.saveTranHistory(tranHistory);
        }

        List<ClueActivityRelation> clueActivityRelationList=clueDao.getCActivity(clueId);
for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){
    ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
    contactsActivityRelation.setId(new UuidUtil().getUUID());
contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
contactsActivityRelation.setContactsId(contacts.getId());
clueDao.saveContactsActivity(contactsActivityRelation );}
        List<ClueRemark> clueRemarkList=clueDao.getCRemark(clueId);
        for (ClueRemark clueRemark:clueRemarkList){
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            customerRemark.setId(new UuidUtil().getUUID());
            customerRemark.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
            customerRemark.setCreateTime(new DateUtil().getDate());
            customerRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
            customerRemark.setEditTime(new DateUtil().getDate());
            customerRemark.setCustomerId(customer.getId());
            clueDao.saveCostumerRemark(customerRemark);

            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setId(new UuidUtil().getUUID());
            contactsRemark.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
            contactsRemark.setCreateTime(new DateUtil().getDate());
            contactsRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());
            contactsRemark.setEditTime(new DateUtil().getDate());
            contactsRemark.setContactsId(contacts.getId());
            clueDao.saveContactsRemark(contactsRemark);
        }

        clueDao.deleteClueOne(clueId);
        clueDao.deleteCActivity(clueId);
        clueDao.deleteRActivity(clueId);

    }

    public Boolean saveClueRemark(ClueRemark clueRemark) {
            Boolean flag=true;
            int num=clueDao.saveClueRemark(clueRemark);
            if (num!=1)
                flag=false;
            return flag;

        }


        public PageActivityVo<ClueRemark> pageClueRemarkList(Integer pageNum, Integer pageSize, String clueId) {
            PageActivityVo<ClueRemark> pageActivityVo=new PageActivityVo();
            List<ClueRemark> remarkList=clueDao.pageClueRemarkList(pageSize,pageNum,clueId);
            int total=clueDao. remarkTost(clueId);
            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(remarkList);
            return pageActivityVo;
        }

    public PageActivityVo<Contacts> contactsPageList(Map<String, Object> map) {
            PageActivityVo<Contacts> pageActivityVo=new PageActivityVo();
            List<Contacts> contactsList=contactsDao.contactsPageList(map);
            int total=contactsDao.ContactsListTost(map);
            System.out.println("##############"+total);

            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(contactsList);
            return pageActivityVo;

    }

        public PageActivityVo<Tran> ListTransaction(Map<String, Object> map) {

            PageActivityVo<Tran> pageActivityVo=new PageActivityVo();
            List<Tran> clueList=clueDao.ListTransaction(map);
            System.out.println("@@2@@@"+clueList);
            int total=clueDao.TranListTost(map);


            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(clueList);
            return pageActivityVo;

        }

    public List<String> getCustomerName(String name) {
        List<String> cname=clueDao.getCustomerName(name);
        return cname;
    }

    public Boolean saveTransaction(Tran tran, HttpServletRequest request) {
        Customer customer=customerDao.getCustomerOne(tran.getCustomerId());
        if (customer==null) {
            customer=new Customer();
            customer.setId(new UuidUtil().getUUID());
            customer.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
            customer.setCreateTime(new DateUtil().getDate());
            customer.setEditBy(((User)request.getSession().getAttribute("user")).getName());
            customer.setEditTime(new DateUtil().getDate());

            customer.setContactSummary(tran.getContactSummary());
            customer.setDescription(tran.getDescription());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setOwner(tran.getOwner());
            customer.setName(tran.getCustomerId());


            customerDao.saveCostumer(customer);
        }
        tran.setCustomerId(customer.getId());
        tran.setId(new UuidUtil().getUUID());
        tran.setCreateBy( ((User)request.getSession().getAttribute("user")).getName());
        tran.setCreateTime(new DateUtil().getDate());
        tran.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setEditTime(new DateUtil().getDate());
        tran.setPossibility((String) request.getSession().getServletContext().getAttribute(tran.getStage()));

            boolean bool=true;
            int num=tranDao.saveTransaction(tran);
            if (num!=1)
                bool=false;
            TranHistory tranHistory=new TranHistory();
            tranHistory.setTranId(tran.getId());
            tranHistory.setStage(tran.getStage());
            tranHistory.setPossibility(tran.getPossibility());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setId(new UuidUtil().getUUID());
            tranHistory.setCreateTime(new DateUtil().getDate());
        tranHistory.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
            int n=tranDao.saveTranHistory(tranHistory);
        if (n!=1)
            bool=false;
            return bool;

    }

    public Tran detailTransaction(String tranId) {
        Tran tran=tranDao.detailTransaction(tranId);
        return tran;
    }

    public PageActivityVo<TranHistory> ListTransactionHistory(HttpServletRequest request, Integer pageNum, Integer pageSize, String tranId) {
        PageActivityVo<TranHistory> pageActivityVo=new PageActivityVo();
        List<TranHistory> remarkList=clueDao.ListTransactionHistory(pageSize,pageNum,tranId);
        for (TranHistory t:remarkList
        ) {
            t.setPossibility(((Map<String, String>) request.getSession().getServletContext().getAttribute("stringMap")).get(t.getStage()));
        }


        int total=clueDao. tranHistoryTost(tranId);
        pageActivityVo.setTotal(total);
        pageActivityVo.setActivityList(remarkList);
        return pageActivityVo;
    }

    public void changeStage(Tran tran) {

        int n=clueDao.updateTran(tran);
        TranHistory tranHistory=new TranHistory();
        tranHistory.setTranId(tran.getId());
        System.out.println("444444"+tranHistory);
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setId(new UuidUtil().getUUID());

        int num=clueDao.saveTranHistory(tranHistory);

    }

    public PageActivityVo<Map<String,Object>> getChars() {

            PageActivityVo<Map<String,Object>> pageActivityVo=new PageActivityVo();
            List<Map<String,Object>> clueList=clueDao.getChars();

            int total=clueDao.getCharsTotal();
            pageActivityVo.setTotal(total);
            pageActivityVo.setActivityList(clueList);
            return pageActivityVo;



    }


}




