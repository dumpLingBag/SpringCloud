package com.rngay.service_authority.listener;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.model.UAMenu;
import com.rngay.service_authority.model.UAUrl;
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
        Dao sqlDao = ContextAware.getSQLDao();
        ApplicationContext context = ContextAware.getContext();
        Map<String, Object> controllers = context.getBeansWithAnnotation(Controller.class);
        if (!controllers.isEmpty()) {
            List<Map<String, Object>> urlList = new ArrayList<>();
            Set<String> set = controllers.keySet();
            for (String key : set) {
                Object controller = controllers.get(key);
                RequestMapping classAnnotation = controller.getClass().getAnnotation(RequestMapping.class);
                if (classAnnotation != null) {
                    String[] classMappings = classAnnotation.value();
                    if (classMappings.length > 0) {
                        Method[] methods = controller.getClass().getMethods();
                        if (methods != null && methods.length > 0) {
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
                                Map<String, Object> classUrl = new HashMap<>();
                                classUrl.put("id", classMapping);
                                classUrl.put("url", classMapping);
                                classUrl.put("name", classAnnotation.name());
                                urlList.add(classUrl);
                                Set<String> mappingUrlSet = new HashSet<>();
                                for (Map<String, String> methodMappingMap : methodMappingList) {
                                    if (mappingUrlSet.add(methodMappingMap.get("url"))) {
                                        String url = methodMappingMap.get("url");
                                        url = url.replaceAll("^/+", "");
                                        url = url.replaceAll("/+$", "");
                                        url = url.replaceAll("^\\+", "");
                                        url = url.replaceAll("\\+$", "");
                                        Map<String, Object> methodUrl = new HashMap<>();
                                        methodUrl.put("id", classMapping+"_"+url);
                                        methodUrl.put("url", classMapping+"/"+url);
                                        methodUrl.put("pid", classMapping);
                                        methodUrl.put("name", methodMappingMap.get("name"));
                                        urlList.add(methodUrl);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            List<UAUrl> mapList = sqlDao.query(UAUrl.class);
            Map<Object, Object> commonMap = new HashMap<>();
            if (!mapList.isEmpty()) {
                for (UAUrl url : mapList) {
                    commonMap.put(url.getId(), url.getCommon());
                }
            }
            for (Map<String, Object> map : urlList) {
                if (map.get("pid") == null || "".equals(map.get("pid"))) {
                    map.put("pid", "null");
                }
                map.put("common", commonMap.get(map.get("id")));
            }

            if (clearUrlOnRestart) {
                sqlDao.update("TRUNCATE TABLE ua_url");
                sqlDao.batchInsert(urlList, "ua_url");
            } else {
                sqlDao.insertOrUpdate(urlList, "ua_url");
            }

            long url = sqlDao.count(UAMenu.class, Cnd.where("component", "=", "AuthorityMenu"));
            if (url <= 0) {
                UAMenu systemManage = new UAMenu();
                systemManage.setName("系统管理");
                systemManage.setIcon("iconfont icon-bug");
                systemManage.setSort(0);
                systemManage.setPid(0);
                int id = sqlDao.insert(systemManage);
                if (id > 0) {
                    UAMenu menuManage = new UAMenu();
                    menuManage.setName("菜单管理");
                    menuManage.setSort(0);
                    menuManage.setPid(id);
                    menuManage.setPath("/authority");
                    menuManage.setComponent("AuthorityMenu");
                    sqlDao.insert(menuManage);
                }
            }
        }

        List<UAUrl> urlList = sqlDao.query(UAUrl.class);
        if (!urlList.isEmpty()) {
            Map<Object, Object> urlMap = new HashMap<>();
            for (UAUrl uaUrl : urlList) {
                if (uaUrl != null) {
                    urlMap.put(uaUrl.getUrl(), uaUrl.getCommon());
                }
            }
            redisUtil.set(AuthorityUtil.APPLICATION_COMMON_URL_KEY, urlMap);
        }
    }

}
