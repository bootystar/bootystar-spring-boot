package io.github.bootystar.starter.helper;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期操作工具
 *
 * @author bootystar
 */
public abstract class DateHelper {

    /**
     * 格式化
     * YYYY 代表 Week Year
     * yyyy 代表year
     * <p>
     * MM 代表 月（Month）
     * mm代表 秒（Min）
     * <p>
     * DD  格式是指当前日期在当年中的天数
     * dd  当月的天
     * <p>
     * HH代表24小时制
     * hh代表12小时制
     */
    public static final String DATE_TIME_EXPRESSION = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_EXPRESSION = "yyyy-MM-dd";
    public static final String TIME_EXPRESSION = "HH:mm:ss";
    public static final ZoneId ZONE_ID = ZoneId.of("GMT+8");
    public static final DateTimeFormatter DTF_LOCAL_DATE_TIME = DateTimeFormatter.ofPattern(DATE_TIME_EXPRESSION);
    public static final DateTimeFormatter DTF_LOCAL_DATE = DateTimeFormatter.ofPattern(DATE_EXPRESSION);
    public static final DateTimeFormatter DTF_LOCAL_TIME = DateTimeFormatter.ofPattern(TIME_EXPRESSION);


    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式, 填充日期时间字符串
     *
     * @param source 来源
     * @return {@link String }
     * @author bootystar
     */
    public static String fillDateTimeString(String source) {
        if (source == null) {
            return null;
        }
        int length = source.length();
        switch (length) {
            case 4:
                source += "-01-01 00:00:00";
                break;
            case 7:
                source += "-01 00:00:00";
                break;
            case 10:
                source += " 00:00:00";
                break;
            case 13:
                source += ":00:00";
                break;
            case 16:
                source += ":00";
                break;
            default:
                break;
        }
        return source;
    }


    /**
     * 按照yyyy-MM-dd的格式, 填充日期字符串
     *
     * @param source 来源
     * @return {@link String }
     * @author bootystar
     */
    public static String fillDateString(String source) {
        if (source == null) {
            return null;
        }
        int length = source.length();
        switch (length) {
            case 4:
                source += "-01-01";
                break;
            case 7:
                source += "-01";
                break;
            default:
                break;
        }
        return source;
    }

    /**
     * 按照HH:mm:ss的格式, 填充时间字符串
     *
     * @param source 来源
     * @return {@link String }
     * @author bootystar
     */
    public static String fillTimeString(String source) {
        if (source == null) {
            return null;
        }
        int length = source.length();
        switch (length) {
            case 2:
                source += ":00:00";
                break;
            case 5:
                source += ":00";
            default:
                break;
        }
        return source;
    }

    /**
     * 获取SimpleDateFormat
     * <p>
     * SimpleDateFormat有线程安全的问题, 每次调用都创建新对象避免线程问题
     *
     * @return {@link SimpleDateFormat }
     * @author bootystar
     */
    public static SimpleDateFormat newSimpleDateFormat() {
        return new SimpleDateFormat(DATE_TIME_EXPRESSION);
    }


    /**
     * 字符串转Date
     * <p>
     * 若字符串缺失年月日时分秒, 会自动按照yyyy-MM-dd HH:mm:ss填充
     *
     * @param source 来源
     * @return {@link Date }
     * @author bootystar
     */
    @SneakyThrows
    public static Date string2Date(String source) {
        if (source == null) return null;
        source = fillDateTimeString(source);
        return newSimpleDateFormat().parse(source);
    }


    /**
     * 字符串根据指定格式转Date
     *
     * @param source  要转化的字符串
     * @param pattern 格式
     * @return {@link Date }
     * @author bootystar
     */
    public static Date string2Date(String source, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(pattern);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException("字符格式不匹配");
        }
    }

    /**
     * 字符串转LocalDateTime
     *
     * @param source 来源
     * @return {@link LocalDateTime }
     * @author bootystar
     */
    public static LocalDateTime string2LocalDateTime(String source) {
        if (source == null) return null;
        source = fillDateTimeString(source);
        return LocalDateTime.parse(source, DTF_LOCAL_DATE_TIME);
    }

    /**
     * 字符串根据指定格式转LocalDateTime
     *
     * @param source  来源
     * @param pattern 图案
     * @return {@link LocalDateTime }
     * @author bootystar
     */
    public static LocalDateTime string2LocalDateTime(String source, String pattern) {
        if (source == null) return null;
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转LocalDate
     *
     * @param source 来源
     * @return {@link LocalDate }
     * @author bootystar
     */
    public static LocalDate string2LocalDate(String source) {
        if (source == null) return null;
        source = fillDateString(source);
        return LocalDate.parse(source, DTF_LOCAL_DATE);
    }

    /**
     * 字符串根据指定格式转LocalDate
     *
     * @param source  来源
     * @param pattern 图案
     * @return {@link LocalDate }
     * @author bootystar
     */
    public static LocalDate string2LocalDate(String source, String pattern) {
        if (source == null) return null;
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串转LocalTime
     *
     * @param source 来源
     * @return {@link LocalTime }
     * @author bootystar
     */
    public static LocalTime string2LocalTime(String source) {
        if (source == null) return null;
        source = fillTimeString(source);
        return LocalTime.parse(source, DTF_LOCAL_TIME);
    }

    /**
     * 字符串根据指定格式转LocalTime
     *
     * @param source  来源
     * @param pattern 图案
     * @return {@link LocalTime }
     * @author bootystar
     */
    public static LocalTime string2LocalTime(String source, String pattern) {
        if (source == null) return null;
        return LocalTime.parse(source, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * Date转LocalDate
     *
     * @param date 日期
     * @return {@link LocalDate }
     * @author bootystar
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDate();
    }

    /**
     * Date转LocalDate
     *
     * @param date   日期
     * @param zoneId 时区id
     * @return {@link LocalDate }
     * @author bootystar
     */
    public static LocalDate date2LocalDate(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * Date转LocalTime
     *
     * @param date 日期
     * @return {@link LocalTime }
     * @author bootystar
     */
    public static LocalTime date2DateTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalTime();
    }

    /**
     * Date转LocalTime
     *
     * @param date   日期
     * @param zoneId 时区id
     * @return {@link LocalTime }
     * @author bootystar
     */
    public static LocalTime date2DateTime(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date 日期
     * @return {@link LocalDateTime }
     * @author bootystar
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date   日期
     * @param zoneId 时区id
     * @return {@link LocalDateTime }
     * @author bootystar
     */
    public static LocalDateTime date2LocalDateTime(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param local 本地
     * @return {@link Date }
     * @author bootystar
     */
    public static Date localDateTime2Date(LocalDateTime local) {
        ZonedDateTime zdt = local.atZone(ZONE_ID);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalTime转Date
     *
     * @param local 本地
     * @return 指定时间+今日日期拼接的Date
     * @author bootystar
     */
    public static Date localTime2Date(LocalTime local) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, local);
        Instant instant = localDateTime.atZone(ZONE_ID).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转Date
     *
     * @param local 本地
     * @return 今日日期0时0分0秒对应的Date
     * @author bootystar
     */
    public static Date localDate2Date(LocalDate local) {
        ZonedDateTime zdt = local.atStartOfDay(ZONE_ID);
        return Date.from(zdt.toInstant());
    }


}
