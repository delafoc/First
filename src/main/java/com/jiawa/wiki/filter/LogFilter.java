//package com.jiawa.wiki.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*; // servlet可以理解为请求接口
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component // 添加这个注解Spring Boot会自动扫描
//public class LogFilter implements Filter { // 实现Filter接口
//
//    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        // 打印请求信息
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        LOG.info("------------- LogFilter 开始 -------------");
//        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
//        LOG.info("远程地址: {}", request.getRemoteAddr());
//
//        long startTime = System.currentTimeMillis(); // 开始时间
//        filterChain.doFilter(servletRequest, servletResponse);
//        LOG.info("------------- LogFilter 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
//    }
//}
//
