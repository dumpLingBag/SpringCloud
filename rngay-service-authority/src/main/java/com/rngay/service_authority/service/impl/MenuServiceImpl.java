package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.platform.MenuDTO;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.feign.platform.MenuUrlDTO;
import com.rngay.feign.platform.RoleMenuDTO;
import com.rngay.feign.platform.vo.MetaVo;
import com.rngay.service_authority.dao.MenuDao;
import com.rngay.service_authority.dao.MenuUrlDao;
import com.rngay.service_authority.dao.RoleMenuDao;
import com.rngay.service_authority.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuUrlDao menuUrlDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public Integer save(MenuDTO uaMenu) {
        MenuDTO byId = menuDao.selectById(uaMenu.getPid());
        byId.setComponent(null);
        menuDao.updateById(byId);
        return menuDao.insert(uaMenu);
    }

    @Override
    public Integer update(MenuDTO uaMenu) {
        return menuDao.updateById(uaMenu);
    }

    @Override
    public List<MenuDTO> load() {
        List<MenuDTO> list = new ArrayList<>();
        List<MenuDTO> menus = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", 0));
        for (MenuDTO menu : menus) {
            MenuDTO mu = new MenuDTO();
            children(mu, menu);
            list.add(mu);
        }
        return list;
    }

    @Override
    public List<MenuDTO> loadByPid() {
        return menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid",0).isNull("component"));
    }

    @Override
    public Integer delete(MenuIdListDTO menuIdList) {
        if (menuIdList.getMenuIdList().size() > 1) {
            menuUrlDao.delete(new QueryWrapper<MenuUrlDTO>().in("menu_id", menuIdList.getMenuIdList()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().in("menu_id", menuIdList.getMenuIdList()));
            menuDao.deleteBatchIds(menuIdList.getMenuIdList());
            return menuIdList.getMenuIdList().size();
        }
        MenuDTO menu = menuDao.selectById(new QueryWrapper<MenuDTO>().eq("menu_id", menuIdList.getMenuIdList().get(0)));
        if (menu != null) {
            menuUrlDao.delete(new QueryWrapper<MenuUrlDTO>().eq("menu_id", menu.getId()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().eq("menu_id", menu.getId()));
            menuDao.deleteById(menu.getId());
            if (!menu.getPid().equals(0)) {
                List<MenuDTO> pid = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", menu.getPid()).ge("sort", menu.getSort()));
                if (pid != null && !pid.isEmpty()) {
                    List<Integer> list = new ArrayList<>();
                    pid.forEach(kev -> list.add(kev.getId()));
                    return menuDao.updateSort(list);
                }
                return 0;
            }
        }
        return 0;
    }

    private List<MenuDTO> getChildren(Integer parentId) {
        List<MenuDTO> list = new ArrayList<>();
        List<MenuDTO> children = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", parentId));
        for (MenuDTO menu : children) {
            if (parentId.equals(menu.getPid())) {
                MenuDTO mu = new MenuDTO();
                children(mu, menu);
                list.add(mu);
            }
        }
        return list;
    }

    private void children(MenuDTO mu,MenuDTO menu) {
        mu.setId(menu.getId());
        mu.setName(menu.getName());
        mu.setPid(menu.getPid());
        mu.setSort(menu.getSort());
        mu.setIcon(menu.getIcon());
        mu.setPath(menu.getPath());
        mu.setComponent(menu.getComponent());
        MetaVo vo = new MetaVo();
        vo.setKeepAlive(menu.getKeepAlive());
        vo.setAuth(menu.getAuth());
        vo.setTitle(menu.getName());
        mu.setMeta(vo);
        mu.setChildren(getChildren(menu.getId()));
    }
}
