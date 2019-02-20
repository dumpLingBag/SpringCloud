package com.rngay.service_user.dao;

import com.rngay.service_user.model.UAIcons;
import com.rngay.service_user.model.UAUser;

import java.util.List;
import java.util.Map;

public interface UAIconDao {

    /**
     * 加载 icon 图标
     * @Author: pengcheng
     * @Date: 2018/12/22
     */
    List<UAIcons> loadIcon();

}
