package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.Tran;
import com.lz.crm.workbench.domain.TranHistory;

public interface TranDao {
    void saveTran(Tran tran);

    int saveTranHistory(TranHistory tranHistory);

    int saveTransaction(Tran tran);


    Tran detailTransaction(String tranId);
}
