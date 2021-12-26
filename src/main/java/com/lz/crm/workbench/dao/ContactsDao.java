package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {
    void saveContacts(Contacts contacts);

    List<Contacts> contactsPageList(Map<String, Object> map);

    int ContactsListTost(Map<String, Object> map);
}
