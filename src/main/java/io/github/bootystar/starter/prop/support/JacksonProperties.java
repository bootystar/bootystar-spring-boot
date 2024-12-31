package io.github.bootystar.starter.prop.support;

import lombok.Data;

/**
 * @author bootystar
 */
@Data
public class JacksonProperties {
    private boolean enabled = true;
    private boolean longToString = true;
    private boolean doubleToString = true;
    private boolean bigIntegerToString = true;
    private boolean bigDecimalToString = true;
}
