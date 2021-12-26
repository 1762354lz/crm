package com.lz.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public String getDate() {
        Date dat=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String date=simpleDateFormat.format(dat);
        return date;
    }


}
