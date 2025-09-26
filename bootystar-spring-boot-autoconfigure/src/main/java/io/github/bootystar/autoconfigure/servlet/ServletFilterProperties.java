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
     * xss防护等级
     */
    private SanitizerType xssSanitizer = SanitizerType.RELAXED;
    
    /**
     * 防盗链允许访问的链接
     */
    private List<String> refererAllowDomains;
    /**
     * 可重复读过滤器
     */
    private Boolean repeatable = true;



    public enum SanitizerType {
        /**
         * 不允许任何标签
         */
        NONE,
        /**
         * 仅允许设置b、em、i、strong、u的简单文本格式
         */
        SIMPLE_TEXT,
        /**
         * 允许更完整的文本节点范围: a，b，blockquote，br，cite，code，dd，dl，dt，em，i，li，ol，p，pre，q，small，span，strike，strong，sub，sup，u，ul和适当的属性
         */
        BASIC,
        /**
         * 允许使用与basic相同的文本标记，还允许使用具有适当属性的img标记，其中src指向http或https
         */
        BASIC_WITH_IMAGES,
        /**
         * 允许完整范围的文本和结构体HTML:a，b，块引用，br，标题，引用，代码，col，colgroup，dd，div，dl，dt，em，h1，h2，h3，h4，h5，h6，i，img，li，ol，p，pre，q，small，span，strike，strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul
         */
        RELAXED,
    }

}