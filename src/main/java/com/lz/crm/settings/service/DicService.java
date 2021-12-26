package com.lz.crm.settings.service;

import com.lz.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getDicAll();

}
