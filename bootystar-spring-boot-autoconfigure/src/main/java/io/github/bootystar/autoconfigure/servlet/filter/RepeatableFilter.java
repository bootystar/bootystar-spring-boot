package io.github.bootystar.autoconfigure.servlet.filter;

import io.github.bootystar.autoconfigure.servlet.request.CachedBodyRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * 可重复读过滤器
 * @author bootystar
 */
public class RepeatableFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        chain.doFilter(new CachedBodyRequestWrapper(request), res);
    }
}
