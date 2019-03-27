package com.rngay.service_authority.dao;

import java.util.List;
import java.util.Map;

public interface UARoleMenuDao {

    List<Map<String, Object>> loadMenuByOrgId(Integer orgId);

    List<Map<String, Object>> loadMenuByUserId(Integer orgId, Integer userId);

}
