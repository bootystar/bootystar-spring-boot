package io.github.bootystar.autoconfigure.servlet.utils.html;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * HTML转义和清理工具类
 *
 * @author ruoyi
 * @author bootystar
 */
public abstract class EscapeUtil {

    /**
     * 清除所有HTML标签，只保留文本内容。
     * 使用 Jsoup 的 Safelist.none() 来确保安全，有效防止XSS攻击。
     *
     * @param content 包含HTML的文本
     * @return 清理和转义后的纯文本
     */
    public static String clean(String content) {
        if (StringUtils.isEmpty(content)) {
            return StringUtils.EMPTY;
        }
        // Jsoup.clean(content, Safelist.none()) 会移除所有HTML标签
        // 第二个参数 Safelist.none() 意味着不允许任何HTML标签
        return Jsoup.clean(content, Safelist.none());
    }
}