package io.github.bootystar.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for date time format.
 *
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.datetime.format")
public class DateTimeFormatProperties {
    /**
     * Time format pattern
     */
    private String time = "HH:mm:ss";
    /**
     * Date format pattern
     */
    private String date = "yyyy-MM-dd";
    /**
     * Date time format pattern
     */
    private String dateTime = "yyyy-MM-dd HH:mm:ss";
    /**
     * Time zone ID
     */
    private String timeZone = "GMT+8";
}