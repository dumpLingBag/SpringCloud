package com.rngay.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    /**
    * 实体类转 Map 对象，Map 的 Key 把驼峰转换为下划线形式
    * @Author pengcheng
    * @Date 2018/12/6
    **/
    public static Map<String, Object> entityToMap(Object obj){
        Map<String, Object> reMap = new HashMap<>();
        if (obj == null)
            return null;

        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                Object o = f.get(obj);
                if (o != null && !"".equals(o)) {
                    reMap.put(HumpUtil.humpToLine(f.getName()), o);
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

        return reMap;
    }

}
