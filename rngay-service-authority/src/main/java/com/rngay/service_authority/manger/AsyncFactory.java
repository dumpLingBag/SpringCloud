package com.rngay.service_authority.manger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rngay.common.util.ServletUtils;
import com.rngay.common.util.ip.AddressUtils;
import com.rngay.common.util.ip.IPUtil;
import com.rngay.common.util.spring.SpringUtils;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.feign.authority.OperLogDTO;
import com.rngay.service_authority.service.LoginInfoService;
import com.rngay.service_authority.service.OperLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class AsyncFactory {

    private static final Logger sys_user_logger = LoggerFactory.getLogger(AsyncFactory.class);

    public static TimerTask recordLogin(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IPUtil.getIPAddress(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    String address = AddressUtils.getRealAddressByIP(ip);
                    // 获取客户端操作系统
                    String os = userAgent.getOperatingSystem().getName();
                    // 获取客户端浏览器
                    String browser = userAgent.getBrowser().getName();
                    LoginInfoDTO loginInfo = new LoginInfoDTO();
                    loginInfo.setUsername(username);
                    loginInfo.setIpaddr(ip);
                    loginInfo.setLoginLocation(address);
                    loginInfo.setBrowser(browser);
                    loginInfo.setOs(os);
                    loginInfo.setMessage(message);
                    loginInfo.setStatus(Integer.parseInt(status));
                    SpringUtils.getBean(LoginInfoService.class).save(loginInfo);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final OperLogDTO operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                // 远程查询操作地点
                try {
                    operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                    SpringUtils.getBean(OperLogService.class).save(operLog);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
