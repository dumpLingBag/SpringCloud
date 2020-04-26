package com.rngay.authority.security.util;

public interface UrlMatcher {

    /**
     *
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    Object compile(String paramString);

    /**
     *
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    boolean pathMatchesUrl(Object paramObject, String paramString);

    /**
     *
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    String getUniversalMatchPattern();

    /**
     *
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    boolean requiresLowerCaseUrl();

}
