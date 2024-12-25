package io.github.bootystar.starter.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@ConfigurationProperties(prefix = "bootystar.databind")
@Data
public class DatabindProp {
    private Boolean longToString = true;
    private Boolean doubleToString = true;
    private Boolean timePackSupport  = true;
    private String timeFormat = "HH:mm:ss";
    private String dateFormat = "yyyy-MM-dd";
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
}
