package io.github.luxmixus.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for date time format.
 *
 * @author luxmixus
 */
@Data
@ConfigurationProperties("luxmixus.datetime.format")
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