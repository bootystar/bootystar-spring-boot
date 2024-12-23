package io.github.bootystar.starter.constants;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 */
public abstract class DateConst {
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String  DATE_TIME_EXPRESSION="yyyy-MM-dd HH:mm:ss";
    public static final String  DATE_EXPRESSION="yyyy-MM-dd";
    public static final String  TIME_EXPRESSION="HH:mm:ss";

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static final SimpleDateFormat SDF_DATE_TIME=new SimpleDateFormat(DATE_TIME_EXPRESSION);
    public static final DateTimeFormatter DTF_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME_EXPRESSION);
    public static final DateTimeFormatter DTF_LOCAL_DATE = DateTimeFormatter.ofPattern(DATE_EXPRESSION);
    public static final DateTimeFormatter DTF_LOCAL_TIME = DateTimeFormatter.ofPattern(TIME_EXPRESSION);



}
