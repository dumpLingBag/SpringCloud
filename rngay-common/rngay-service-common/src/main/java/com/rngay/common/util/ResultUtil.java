package com.rngay.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

public class ResultUtil {

    private static final Logger log = LoggerFactory.getLogger(ResultUtil.class);
    public ResultUtil() {}

    public static void writeJson(HttpServletResponse response, int code, String msg, Object data) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.print(JsonUtil.obj2String(getMap(code, msg, data)));
        } catch (Exception e) {
            log.error("返回json失败");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static void writeJson(HttpServletResponse response, int code, String msg) {
        writeJson(response, code, msg, null);
    }

    /*public static void writeJson(HttpServletResponse response, ResultCodeEnum codeEnum) {
        writeJson(response, codeEnum.getCode(), codeEnum.getMsg());
    }*/

    /*public static void writeJson(HttpServletResponse response, int code, String msg, Object data) {
        writeJson(response, code, msg, data);
    }*/

    private static HashMap<String, Object> getMap(int code, String msg, Object data) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", code);
        hashMap.put("msg", msg);
        hashMap.put("data", data);
        return hashMap;
    }

}
