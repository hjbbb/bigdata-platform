package cn.edu.xidian.bigdataplatform.ranger.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author suman.bn
 */
public class RangerHeadersInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate template) {
        template.header("Accept", "application/json");
        template.header("X-XSRF-HEADER", "\"\"");
        template.header("Content-Type", "application/json");
    }
}
