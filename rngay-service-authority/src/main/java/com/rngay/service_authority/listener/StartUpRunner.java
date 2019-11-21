package com.rngay.service_authority.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.common.cache.RedisUtil;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.dao.MenuDao;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.service.UrlService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.ContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Value(value = "${platform.clearUrlOnRestart}")
    private Boolean clearUrlOnRestart;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) {
        MenuDao menuDao = ContextAware.getService(MenuDao.class);
        UrlDao urlDao = ContextAware.getService(UrlDao.class);
        UrlService urlService = ContextAware.getService(UrlService.class);
        ApplicationContext context = ContextAware.getContext();
        Map<String, Object> controllers = context.getBeansWithAnnotation(Controller.class);
        if (!controllers.isEmpty()) {
            List<UrlDTO> urlList = new ArrayList<>();
            for (Map.Entry<String, Object> key : controllers.entrySet()) {
                Object value = key.getValue();
                RequestMapping classAnnotation = value.getClass().getAnnotation(RequestMapping.class);
                if (classAnnotation != null) {
                    String[] classMappings = classAnnotation.value();
                    if (classMappings.length > 0) {
                        Method[] methods = value.getClass().getMethods();
                        if (methods.length > 0) {
                            List<Map<String, String>> methodMappingList = new ArrayList<>();
                            for (Method method : methods) {
                                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                                if (methodAnnotation != null) {
                                    String[] methodMappings = methodAnnotation.value();
                                    if (methodMappings.length > 0) {
                                        for (String methodMapping : methodMappings) {
                                            Map<String, String> methodMappingMap = new HashMap<>();
                                            methodMappingMap.put("url", methodMapping);
                                            methodMappingMap.put("name", methodAnnotation.name());
                                            methodMappingList.add(methodMappingMap);
                                        }
                                    }
                                }
                            }
                            for (String classMapping : classMappings) {
                                classMapping = classMapping.replaceAll("^/+", "");
                                classMapping = classMapping.replaceAll("/+$", "");
                                classMapping = classMapping.replaceAll("^\\+", "");
                                classMapping = classMapping.replaceAll("\\+$", "");
                                UrlDTO classUrl = new UrlDTO();
                                classUrl.setId(classMapping);
                                classUrl.setUrl(classMapping);
                                classUrl.setName(classAnnotation.name());
                                urlList.add(classUrl);
                                Set<String> mappingUrlSet = new HashSet<>();
                                for (Map<String, String> methodMappingMap : methodMappingList) {
                                    if (mappingUrlSet.add(methodMappingMap.get("url"))) {
                                        String url = methodMappingMap.get("url");
                                        url = url.replaceAll("^/+", "");
                                        url = url.replaceAll("/+$", "");
                                        url = url.replaceAll("^\\+", "");
                                        url = url.replaceAll("\\+$", "");
                                        UrlDTO methodUrl = new UrlDTO();
                                        methodUrl.setId(classMapping + "_" + url);
                                        methodUrl.setUrl(classMapping + "/" + url);
                                        methodUrl.setPid(classMapping);
                                        methodUrl.setName(methodMappingMap.get("name"));
                                        urlList.add(methodUrl);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            List<UrlDTO> mapList = urlDao.selectList(null);
            Map<Object, Object> commonMap = new HashMap<>();
            if (!mapList.isEmpty()) {
                for (UrlDTO url : mapList) {
                    commonMap.put(url.getId(), url.getCommon());
                }
            }
            for (UrlDTO arr : urlList) {
                if (arr.getPid() == null || "".equals(arr.getPid())) {
                    arr.setPid("null");
                }
                arr.setCommon((Integer) commonMap.get(arr.getId()));
            }

            if (!urlList.isEmpty()) {
                if (clearUrlOnRestart) {
                    urlDao.updateSql();
                    urlService.saveBatch(urlList);
                } else {
                    urlService.saveOrUpdateBatch(urlList);
                }
            }

            Integer url = menuDao.selectCount(new QueryWrapper<MenuDTO>().eq("component", "AuthorityMenu"));
            if (url <= 0) {
                MenuDTO systemManage = new MenuDTO();
                systemManage.setName("系统管理");
                systemManage.setIcon("iconfont icon-bug");
                systemManage.setSort(0);
                systemManage.setPid(0L);
                menuDao.insert(systemManage);
                int id = menuDao.insert(systemManage);
                if (id > 0) {
                    MenuDTO menuManage = new MenuDTO();
                    menuManage.setName("菜单管理");
                    menuManage.setSort(0);
                    menuManage.setPid((long) id);
                    menuManage.setPath("/authority");
                    menuManage.setComponent("AuthorityMenu");
                    menuDao.insert(menuManage);
                }
            }
        }

        List<UrlDTO> urlList = urlDao.selectList(null);
        if (!urlList.isEmpty()) {
            Map<Object, Object> urlMap = new HashMap<>();
            for (UrlDTO uaUrl : urlList) {
                if (uaUrl != null) {
                    urlMap.put(uaUrl.getUrl(), uaUrl.getCommon());
                }
            }
            redisUtil.set(AuthorityUtil.APPLICATION_COMMON_URL_KEY, urlMap);
        }
    }

}
