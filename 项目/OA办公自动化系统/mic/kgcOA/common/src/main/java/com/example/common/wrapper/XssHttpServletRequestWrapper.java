package com.example.common.wrapper;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 丁祥瑞
 * 封装用户的请求， 过滤请求中的参数的有害部分
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 原来的请求
     */
    private HttpServletRequest originalServletRequest;

    /**
     * 有参构造方法, 将原来的请求通过super关键字传递给父类
     *
     * @param servletRequest
     */
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        this.originalServletRequest = servletRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOriginalRequest(HttpServletRequest request) {
        // 判断传入的请求是否是被封装后的请求
        // instanceof 判断是否是某个类的实例
        // 如果宠物是dog类型的, 将宠物转为Dog类型(向下转型)

        if (request instanceof XssHttpServletRequestWrapper) {
            // 从被封装后的请求获得原始请求
            return ((XssHttpServletRequestWrapper) request).getOriginalRequest();
        }
        // 如果不是被封装的请求, 则直接返回
        return request;
    }

    /**
     * 去除参数中的有害部分
     *
     * @param value
     * @return
     */
    private String xssEncode(String value) {
        return HtmlUtil.filter(value);
    }

    /**
     * userName ==> <script>alert("123");</script>
     * 过滤请求中的参数
     *
     * @param name 参数名
     * @return 过滤后的的内容
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (StrUtil.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 过滤请求中的参数
     *
     * @return 过滤后的的内容
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // 获得原来所有参数的名字和参数名字对应的参数值的数组
        Map<String, String[]> temp = super.getParameterMap();
        // 获得map中的key的集合(获得所有参数名字的集合)
        Set<String> paramNames = temp.keySet();
        // 过滤后的内容
        Map<String, String[]> result = new HashMap<>();
        // 遍历所有参数的名字
        for (String paramName : paramNames) {
            // 获得参数名对应的参数值的数组(Map中的key:参数的名字, value:参数的值)
            String[] values = temp.get(paramName);
            // 对参数中的值进行过滤
            for (int i = 0; i < values.length; ++i) {
                values[i] = xssEncode(values[i]);
            }
            // 将过滤后的内容保存起来
            paramName = xssEncode(paramName);
            result.put(paramName, values);
        }
        return result;
    }

    /**
     * 过滤请求中的参数
     *
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        // 根据参数的名字获得参数对应的数组
        String[] temps = super.getParameterValues(name);
        // Hutool工具类    字符串工具类StrUtil   集合工具类CollUtil       数组工具类ArrayUtil
        if (ArrayUtil.isNotEmpty(temps)) {
            //  过滤参数中的值
            for (int i = 0; i < temps.length; ++i) {
                temps[i] = xssEncode(temps[i]);
            }
        }
        // 返回过滤后的参数值
        return temps;
    }

    /**
     * 过滤请求的参数
     *
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        // 获得请求头中参数的值
        String value = super.getHeader(xssEncode(name));
        // 如果参数值不为空
        if (StrUtil.isNotEmpty(value)) {
            // 对参数值进行过滤
            value = xssEncode(value);
        }

        return value;
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOriginalRequest() {
        return originalServletRequest;
    }

}
