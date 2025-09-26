package io.github.bootystar.autoconfigure.servlet;

import io.github.bootystar.autoconfigure.ConditionalOnListProperty;
import io.github.bootystar.autoconfigure.servlet.filter.RefererFilter;
import io.github.bootystar.autoconfigure.servlet.filter.RepeatableFilter;
import io.github.bootystar.autoconfigure.servlet.filter.XssFilter;
import jakarta.servlet.DispatcherType;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;


/**
 * servlet自动配置
 *
 * @author bootystar
 */
@AutoConfiguration
@EnableConfigurationProperties({ServletFilterProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(value = "bootystar.servlet.enabled", havingValue = "true", matchIfMissing = true)
public class ServletAutoConfiguration {

    @Bean
    @ConditionalOnListProperty(value = "bootystar.servlet.filter.xss-includes")
    public FilterRegistrationBean<XssFilter> xssFilterRegistration(ServletFilterProperties servletFilterProperties) {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        XssFilter xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                servletFilterProperties.getXssExcludes(), 
                s -> Jsoup.clean(s, Safelist.relaxed())
                );
        registration.setFilter(xssFilter);
        registration.setName("xssFilter");
        // 设置为拦截所有路径，由过滤器内部进行路径匹配
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnListProperty(value = "bootystar.servlet.filter.referer-allow-domains")
    public FilterRegistrationBean<RefererFilter> refererFilterRegistration(ServletFilterProperties servletFilterProperties) {
        FilterRegistrationBean<RefererFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        RefererFilter refererFilter = new RefererFilter(servletFilterProperties.getRefererAllowDomains());
        registration.setFilter(refererFilter);
        registration.setName("refererFilter");
        // 设置为拦截所有路径，由过滤器内部进行路径匹配
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnProperty(value = "bootystar.servlet.filter.repeatable-enable", havingValue = "true")
    public FilterRegistrationBean<RepeatableFilter> someFilterRegistration() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}