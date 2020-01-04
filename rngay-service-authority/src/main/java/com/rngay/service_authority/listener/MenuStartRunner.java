package com.rngay.service_authority.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.service_authority.dao.MenuDao;
import com.rngay.service_authority.util.ContextAware;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MenuStartRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MenuDao menuDao = ContextAware.getService(MenuDao.class);
        Integer url = menuDao.selectCount(new QueryWrapper<MenuDTO>().eq("component", "AuthorityMenu"));
        if (url <= 0) {
            MenuDTO systemManage = new MenuDTO();
            systemManage.setName("系统管理");
            systemManage.setIcon("iconfont icon-bug");
            systemManage.setPath("0");
            systemManage.setSort(1);
            systemManage.setPid(0L);
            systemManage.setMenuType(0);
            int id = menuDao.insert(systemManage);
            if (id > 0) {
                MenuDTO menuManage = new MenuDTO();
                menuManage.setName("菜单管理");
                menuManage.setSort(1);
                menuManage.setMenuType(1);
                menuManage.setPid(systemManage.getId());
                menuManage.setPath("/authority");
                menuManage.setComponent("AuthorityMenu");
                menuManage.setIcon("iconfont icon-xingzhuang-tuoyuanxing");
                menuDao.insert(menuManage);
            }
        }
    }
}
