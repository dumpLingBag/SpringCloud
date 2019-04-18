package com.rngay.service_authority.util;

import java.util.List;
import java.util.Map;

public class SortUtil {

    /**
    * 排序菜单数据
    * @Author: pengcheng
    * @Date: 2019/3/31
    */
    public static List<Map<String, Object>> mapSort(List<Map<String, Object>> mapList) {
        mapList.sort((o1, o2) -> {
            Integer column1 = Integer.valueOf(o1.get("sort").toString());
            Integer column2 = Integer.valueOf(o2.get("sort").toString());
            return column1.compareTo(column2);
        });
        return mapList;
    }

}
