package io.github.bootystar.starter.excel.fastexcel;


import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.DefaultConverterLoader;
import io.github.bootystar.starter.excel.fastexcel.converter.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * easy excel转换器工具
 *
 * @author bootystar
 */
@Slf4j
public abstract class FastExcelConverterRegister {
    private static final String WRITE_METHOD = "putWriteConverter";
    private static final String ALL_METHOD = "putAllConverter";
    private static volatile boolean isRegistered = false;

    public static void registerExtraConverters(String dateTimePattern, String datePattern, String timePattern, String zoneId) {
        if (isRegistered) {
            return;
        }
        addConverters(
                new DateConverter(dateTimePattern, zoneId)

                , new LocalDateTimeConverter(dateTimePattern)
                , new LocalDateConverter(datePattern)
                , new LocalTimeConverter(timePattern)
                
                , new SqlTimestampConverter(dateTimePattern)
                , new SqlDateConverter(datePattern)
                , new SqlTimeConverter(timePattern)

                , new BooleanConverter()
                , new FloatConverter()
                , new DoubleConverter()
                , new LongConverter()
                , new BigIntergerConverter()
                , new BigDecimalConverter()
        );
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

    public static void addConverters(Object... converters) {
        Method method = method4Converter2write();
        Method method2 = method4Converter2all();
        if (method == null || method2 == null) {
            log.warn("EasyExcel add excel converter failed , export or import may produce error on special field!");
            return;
        }
        try {
            for (Object converter : converters) {
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
