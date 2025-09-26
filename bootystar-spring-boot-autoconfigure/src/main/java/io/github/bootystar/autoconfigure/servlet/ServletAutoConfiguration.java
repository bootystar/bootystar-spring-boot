package io.github.bootystar.autoconfigure.servlet;

import io.github.bootystar.autoconfigure.ConditionalOnListProperty;
import io.github.bootystar.autoconfigure.servlet.filter.RefererFilter;
import io.github.bootystar.autoconfigure.servlet.filter.RepeatableFilter;
import io.github.bootystar.autoconfigure.servlet.filter.XssFilter;
import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@Slf4j
public class ServletAutoConfiguration {

    @Bean
    @ConditionalOnListProperty(value = "bootystar.servlet.filter.xss-includes")
    @ConditionalOnMissingBean(name = "xssFilterRegistration")
    public FilterRegistrationBean<XssFilter> xssFilterRegistration(ServletFilterProperties servletFilterProperties) {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        XssFilter xssFilter ;
        switch (servletFilterProperties.getXssSanitizer()){
            case NONE:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.none())
                );
                break;
            case SIMPLE_TEXT:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.simpleText())
                );
                break;
            case BASIC:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.basic())
                );
                break;
            case BASIC_WITH_IMAGES:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.basicWithImages())
                );
                break;
            case RELAXED:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.relaxed())
                );
                break;
            default:
                xssFilter = new XssFilter(servletFilterProperties.getXssIncludes(),
                        servletFilterProperties.getXssExcludes(),
                        s -> Jsoup.clean(s, Safelist.relaxed())
                );
                log.warn("Unsupported value '{}' for property 'bootystar.servlet.filter.xss-sanitizer', " +
                                "using 'RELAXED' instead. Supported values: [NONE, SIMPLE_TEXT, BASIC, BASIC_WITH_IMAGES, RELAXED]",
                        servletFilterProperties.getXssSanitizer());

        }
        registration.setFilter(xssFilter);
        registration.setName("xssFilter");
        // 设置为拦截所有路径，由过滤器内部进行路径匹配
        registration.addUrlPatterns("/*");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Bean
    @ConditionalOnListProperty(value = "bootystar.servlet.filter.referer-allow-domains")
    @ConditionalOnMissingBean(name = "refererFilterRegistration")
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
    @ConditionalOnProperty(value = "bootystar.servlet.filter.repeatable", havingValue = "true")
    @ConditionalOnMissingBean(name = "repeatableFilterRegistration")
    public FilterRegistrationBean<RepeatableFilter> repeatableFilterRegistration() {
        FilterRegistrationBean<RepeatableFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

}