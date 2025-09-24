package io.github.bootystar.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar")
public class BootystarProperties {
    /**
     * 时间格式
     */
    private String timeFormat = "HH:mm:ss";
    /**
     * 日期格式
     */
    private String dateFormat = "yyyy-MM-dd";
    /**
     * 日期时间格式
     */
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时区id
     */
    private String timeZoneId = "GMT+8";
}
