package com.lz.crm.settings.service.iml;

import com.lz.crm.settings.dao.DicTypeDao;
import com.lz.crm.settings.dao.DicValueDao;
import com.lz.crm.settings.domain.DicType;
import com.lz.crm.settings.domain.DicValue;
import com.lz.crm.settings.service.DicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dicService")
public class DicServiceImpl implements DicService {
    @Resource
    private DicTypeDao dicTypeDao;
    @Resource
    private DicValueDao dicValueDao;
    public Map<String, List<DicValue>> getDicAll() {
        Map <String,List<DicValue>> map=new HashMap<String,List<DicValue>>();
        List<DicType> dicTypeList=dicTypeDao.getDicTypeAll();
        for (DicType dicType:dicTypeList) {
            List<DicValue> dicValueList = dicValueDao.getDicValueBy(dicType.getCode());
            map.put(dicType.getCode(), dicValueList);
        }
        return map;
    }
}
