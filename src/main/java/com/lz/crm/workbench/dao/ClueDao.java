package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    int save(Clue clue);

    List<Clue> getClueList(Map<String, Object> map);

    int clueListTost(Map<String, Object> map);

    int saveClueActivity(@Param("activityId") String activityId, @Param("clueId") String clueId, @Param("id") String id);

    List<Activity> showClueActivity(Map<String, Object> map);

    int showClueActivityTost(Map<String, Object> map);

    List<Activity> activityPageListRelate(Map<String, Object> map);

    int activityPageListRelateTost(Map<String, Object> map);

    int deleteRelate(@Param("activityId") String activityId, @Param("clueId") String clueId);

    Clue getClueOne(String id);

    int saveCostumer(Customer customer);

    int saveClueRemark(ClueRemark clueRemark);

    List<ClueRemark> pageClueRemarkList(@Param("pageSize") Integer pageSize, @Param("pageNum") Integer pageNum, @Param("clueId") String clueId);


    int remarkTost(String clueId);

    void deleteClueOne(String clueId);

    void deleteCActivity(String clueId);

    void deleteRActivity(String clueId);

    List<ClueActivityRelation> getCActivity(String clueId);

    void saveContactsActivity(ContactsActivityRelation contactsActivityRelation);



    List<ClueRemark> getCRemark(String clueId);

    void saveCostumerRemark(CustomerRemark customerRemark);

    void saveContactsRemark(ContactsRemark contactsRemark);

    List<Tran> ListTransaction(Map<String, Object> map);

    int TranListTost(Map<String, Object> map);

    List<String> getCustomerName(String name);


    List<TranHistory> ListTransactionHistory(@Param("pageSize") Integer pageSize, @Param("pageNum") Integer pageNum, @Param("tranId") String tranId);

    int tranHistoryTost(String tranId);

    int updateTran(Tran tran);

    int saveTranHistory(TranHistory tran);

    List<Map<String,Object>> getChars();

    int getCharsTotal();
}
