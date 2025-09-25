package io.github.bootystar.autoconfigure.servlet;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * servlet属性
 *
 * @author bootystar
 */
@ConfigurationProperties("bootystar.servlet.filter")
@Data
public class ServletFilterProperties {

    /**
     * xss匹配的url(ant模式匹配)
     */
    private List<String> xssIncludes;
    /**
     * xss排除的url(ant模式匹配)
     */
    private List<String> xssExcludes;
    /**
     * 防盗链允许访问的链接
     */
    private List<String> refererAllowDomains;

}