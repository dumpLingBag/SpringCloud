package com.rngay.service_socket.service;

import java.util.List;
import java.util.Map;

public interface SysMessageService {

    List<Map<String, Object>> getSysInfo(String userId);

}
