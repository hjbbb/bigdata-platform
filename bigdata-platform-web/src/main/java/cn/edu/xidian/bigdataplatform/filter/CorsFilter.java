package cn.edu.xidian.bigdataplatform.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {
    public static final String HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    @Value("${cors.header.allow-origin}")
    private String allowedOrigins;

    @Value("${cors.header.allow-methods}")
    private String allowedMethods;

    @Value("${cors.header.allow-headers}")
    private String allowedHeaders;

    @Value("${cors.header.cache-control}")
    private String cacheControl;

    @Value("${cors.header.pragma}")
    private String pragma;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader(HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
        response.setHeader(HEADER_ACCESS_CONTROL_ALLOW_METHODS, allowedMethods);
        response.setHeader(HEADER_ACCESS_CONTROL_ALLOW_HEADERS, allowedHeaders);
        response.setHeader(HEADER_CACHE_CONTROL, cacheControl);
        response.setHeader(HEADER_PRAGMA, pragma);
        filterChain.doFilter(servletRequest, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}