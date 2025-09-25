package io.github.bootystar.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.datetime.format")
public class DateTimeFormatProperties {
    /**
     * 时间格式
     */
    private String time = "HH:mm:ss";
    /**
     * 日期格式
     */
    private String date = "yyyy-MM-dd";
    /**
     * 日期时间格式
     */
    private String dateTime = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时区id
     */
    private String timeZone = "GMT+8";
}
