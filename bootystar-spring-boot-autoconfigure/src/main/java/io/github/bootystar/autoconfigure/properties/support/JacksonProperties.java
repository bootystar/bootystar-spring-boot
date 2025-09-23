package io.github.bootystar.autoconfigure.properties.support;

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
