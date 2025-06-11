package io.github.bootystar.starter.excel.easyexcel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterLoader;
import io.github.bootystar.starter.excel.easyexcel.converter.*;
import io.github.bootystar.starter.prop.BootystarProperties;
import io.github.bootystar.starter.prop.support.ExcelProperties;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * easy excel转换器工具
 *
 * @author bootystar
 */
@Slf4j
public abstract class EasyExcelConverterRegister {
    private static final String WRITE_METHOD = "putWriteConverter";
    private static final String ALL_METHOD = "putAllConverter";
    private static volatile boolean isRegistered = false;

    public static void registerConverters(BootystarProperties properties) {
        if (isRegistered) {
            return;
        }
        ExcelProperties excelProperties = properties.getExcel();
        if (!excelProperties.isInitEasyExcel()){
            return;
        }
        if (excelProperties.isBigDecimalToString()){
            addConverters(new BigDecimalConverter());
        }
        if (excelProperties.isBigIntegerToString()){
            addConverters(new BigIntergerConverter());
        }
        if (excelProperties.isLongToString()){
            addConverters(new LongConverter());
        }
        if (excelProperties.isDoubleToString()){
            addConverters(new DoubleConverter());
        }
        if (excelProperties.isSqlTimestampToString()){
            addConverters(new SqlTimestampConverter(properties.getDateTimeFormat()));
        }
        if (excelProperties.isSqlDateToString()){
            addConverters(new SqlDateConverter(properties.getDateFormat()));
        }
        if (excelProperties.isSqlTimeToString()){
            addConverters(new SqlTimeConverter(properties.getTimeFormat()));
        }
        if (excelProperties.isLocalDateTimeToString()){
            addConverters(new LocalDateTimeConverter(properties.getDateTimeFormat()));
        }
        if (excelProperties.isLocalDateToString()){
            addConverters(new LocalDateConverter(properties.getDateFormat()));
        }
        if (excelProperties.isLocalTimeToString()){
            addConverters(new LocalTimeConverter(properties.getTimeFormat()));
        }
        if (excelProperties.isDateToString()){
            addConverters(new DateConverter(properties.getDateTimeFormat(), properties.getTimeZoneId()));
        }
        isRegistered = true;
    }

    private static Method getMethod(String writeMethod) {
        try {
            Class<DefaultConverterLoader> clazz = DefaultConverterLoader.class;
            Method method = clazz.getDeclaredMethod(writeMethod, Converter.class);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {
            log.debug("error", e);
        }
        return null;
    }

    private static Method method4Converter2write() {
        return getMethod(WRITE_METHOD);
    }

    private static Method method4Converter2all() {
        return getMethod(ALL_METHOD);
    }

    public static void addConverters(Converter<?>... converters) {
        Method method = method4Converter2write();
        Method method2 = method4Converter2all();
        if (method == null || method2 == null) {
            log.warn("EasyExcel add excel converter failed , export or import may produce error on special field!");
            return;
        }
        try {
            for (Converter<?> converter : converters) {
                method.invoke(null, converter);
                method2.invoke(null, converter);
            }
        } catch (IllegalAccessException e) {
            log.warn("IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            log.warn("InvocationTargetException", e);
        }
    }


}
