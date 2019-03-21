package com.rngay.service_authority.service;

import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.model.UAMenuUrl;

import java.util.List;
import java.util.Map;

public interface UAMenuUrlService {

    List<Map<String, Object>> load();

    List<UAMenuUrl> loadUrl(Integer id);

    Integer update(UpdateUrlDTO updateUrlDTO);

}
