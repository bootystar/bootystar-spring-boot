package io.github.bootystar.autoconfigure.excel;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Excel properties
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.excel")
public class ExcelProperties {
    
    /**
     * Whether to enable excel autoconfiguration
     */
    private boolean auto;

    /**
     * Whether to enable BigDecimal to string conversion
     */
    private boolean bigDecimalToString = true;

    /**
     * Whether to enable BigInteger to string conversion
     */
    private boolean bigIntegerToString = true;

    /**
     * Whether to enable Long to string conversion
     */
    private boolean longToString = true;

    /**
     * Whether to enable Boolean to string conversion
     */
    private boolean booleanToString = true;

    /**
     * Whether to enable Float to string conversion
     */
    private boolean floatToString = true;

    /**
     * Whether to enable Double to string conversion
     */
    private boolean doubleToString = true;

    /**
     * Whether to enable SQL timestamp to string conversion
     */
    private boolean sqlTimestampToString = true;

    /**
     * Whether to enable SQL date to string conversion
     */
    private boolean sqlDateToString = true;

    /**
     * Whether to enable SQL time to string conversion
     */
    private boolean sqlTimeToString = true;

    /**
     * Whether to enable LocalDateTime to string conversion
     */
    private boolean localDateTimeToString = true;

    /**
     * Whether to enable LocalDate to string conversion
     */
    private boolean localDateToString = true;

    /**
     * Whether to enable LocalTime to string conversion
     */
    private boolean localTimeToString = true;

    /**
     * Whether to enable Date to string conversion
     */
    private boolean dateToString = true;
}