package com.rngay.service_authority.controller;

import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.CommonUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.service.CommonService;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping(value = "authorityCommonUrl", name = "公共权限")
public class CommonController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MenuUrlService menuUrlService;

    @GetMapping(value = "load")
    public Result<List<UrlDTO>> load(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(menuUrlService.load());
        }
        return Result.success();
    }

    @GetMapping(value = "loadOpen")
    public Result<List<UrlDTO>> loadOpen(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(commonService.loadOpen());
        }
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() == null || urlDTO.getUrlId().isEmpty()) {
            return Result.fail(ResultCodeEnum.COMMON_AUTHORITY_FAIL);
        }
        return Result.success(commonService.update(urlDTO));
    }

    public static void main(String[] args) {
        final HttpGet request = new HttpGet("http://127.0.0.1:9001/authority/authorityRoleMenu/load");
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        try {
            //request.setEntity(new StringEntity("<xml><URL><![CDATA[http://cyngrok.rngay.cn/chuanyisoft/gpa/gp/h5WxMsg/h5WxMsgInterface/wxMsg]]></URL><ToUserName><![CDATA[gh_fcef73d8766a]]></ToUserName><FromUserName><![CDATA[11111]]></FromUserName><CreateTime>11111</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[111111]]></Content><MsgId>11111111</MsgId></xml>"));
            client.start();
            client.execute(request, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response) {
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                    System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    try {
                        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                        System.out.println(" response content is : " + content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Exception e) {
                    System.out.println(request.getRequestLine() + "->" + e);
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                }

                @Override
                public void cancelled() {
                    System.out.println(request.getRequestLine() + " cancelled");
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
