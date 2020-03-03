package com.rngay.common.util.ip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rngay.common.util.JsonUtil;
import com.rngay.common.util.StringUtils;
import com.rngay.common.util.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) throws JsonProcessingException {
        String address = "XX XX";
        // 内网不查询
        if (IPUtil.internalIp(ip))
        {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr))
        {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        ObjectMapper mapper = JsonUtil.string2Obj(rspStr, ObjectMapper.class);
        if (mapper != null) {
            ObjectWriter data = mapper.writer().withRootName("data");
            String region = data.writeValueAsString("region");
            String city = data.writeValueAsString("city");
            address = region + " " + city;
        }
        return address;
    }

}
