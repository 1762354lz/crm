package com.lz.crm.workbench.service;

import com.lz.crm.vo.PageActivityVo;
import com.lz.crm.workbench.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ClueService {
    Boolean save(Clue clue);

    PageActivityVo<Clue> getClueList(Map<String, Object> map);

    Boolean saveClueActivity(String[] activityId, String clueId);

    PageActivityVo<Activity> showClueActivity(Map<String, Object> map);

    PageActivityVo<Activity> activityPageListRelate(Map<String, Object> map);

    Boolean deleteRelate(String activityId, String clueId);

    Clue getClueOne(String id);

    Boolean saveCostumer(Customer customer);

    void convert(Tran tran, String clueId, HttpServletRequest request);

    Boolean saveClueRemark(ClueRemark clueRemark);

    PageActivityVo<ClueRemark> pageClueRemarkList(Integer pageNum, Integer pageSize, String clueId);

    PageActivityVo<Contacts> contactsPageList(Map<String, Object> map);

    PageActivityVo<Tran> ListTransaction(Map<String, Object> map);

    List<String> getCustomerName(String name);

    Boolean saveTransaction(Tran tran, HttpServletRequest request);

    Tran detailTransaction(String tranId);

    PageActivityVo<TranHistory> ListTransactionHistory(HttpServletRequest request, Integer pageNum, Integer pageSize, String clueId);

    void changeStage(Tran tran);

    PageActivityVo<Map<String,Object>> getChars();

}
