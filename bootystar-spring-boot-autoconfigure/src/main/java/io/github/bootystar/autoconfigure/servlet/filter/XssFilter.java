package io.github.bootystar.autoconfigure.servlet.filter;


import io.github.bootystar.autoconfigure.servlet.request.XssHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.List;

/**
 * 防止XSS攻击的过滤器
 *
 * @author ruoyi
 * @author bootystar
 */
@RequiredArgsConstructor
public class XssFilter implements Filter {

    /**
     * 包含链接
     */
    @Getter
    protected final List<String> includes;
    /**
     * 排除链接
     */
    @Getter
    protected final List<String> excludes;
    /**
     * 匹配器
     */
    protected final PathMatcher matcher;



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)){
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String method = req.getMethod();
        // GET DELETE 不过滤
        if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
            chain.doFilter(request, response);
            return;
        }
        if (isMatchedURL(req, resp)) {
            XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(xssRequest, response);
            return;
        }
        chain.doFilter(request, response);
    }


    public boolean isMatchedURL(HttpServletRequest request, HttpServletResponse response) {
        return !this.isExcludeURL(request, response) && this.isIncludeURL(request, response);
    }

    public boolean isIncludeURL(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        // 如果没有包含规则，则默认不过滤
        if (includes == null || includes.isEmpty()) {
            return false;
        }
        // 检查是否匹配任意包含模式
        for (String pattern : includes) {
            if (matcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getServletPath();
        if (url == null || url.isEmpty()) {
            return false;
        }
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        for (String pattern : excludes) {
            if (matcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

   
    
}