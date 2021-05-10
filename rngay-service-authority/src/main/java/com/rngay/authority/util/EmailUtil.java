package com.rngay.authority.util;

public class EmailUtil {

    public static String verify(String code) {
        return "<div style='background: #f8f8f8;padding: 30px 20px;'>" +
                    "<div style='background: #fff;padding: 50px 25px 40px;box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);'>" +
                        "<div style='font-size: 20px;font-weight: bold;margin-bottom: 30px;'>您好</div>" +
                        "<div>请使用下面的验证码验证您的操作，验证码10分钟有效：</div>" +
                        "<div style='overflow: hidden;margin-top: 20px;'>" +
                            "<span style='background: #409EFF;color: #fff;border-radius: 5px;padding: 10px;font-size:20px;font-weight:bold;'>"+code+"</span>" +
                        "</div>" +
                    "</div>" +
                "</div>";
    }

}
