package com.lz.crm.settings.dao;

import com.lz.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getDicValueBy(String code);
}
