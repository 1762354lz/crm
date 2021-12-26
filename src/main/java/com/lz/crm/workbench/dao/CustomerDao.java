package com.lz.crm.workbench.dao;

import com.lz.crm.workbench.domain.Customer;

public interface CustomerDao {
    Customer getCustomerOne(String company);

    void saveCostumer(Customer customer);
}
