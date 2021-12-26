package com.lz.crm.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageActivityVo<T> {
    int total;
    List<T> activityList;

}
