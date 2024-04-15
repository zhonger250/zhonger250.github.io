package com.example.common.filter;

import com.example.common.wrapper.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 丁祥瑞
 * xss过滤器
 *
 */
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //对原有的request请求进行包装
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        // 请求放行
        chain.doFilter(xssHttpServletRequestWrapper, response);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
