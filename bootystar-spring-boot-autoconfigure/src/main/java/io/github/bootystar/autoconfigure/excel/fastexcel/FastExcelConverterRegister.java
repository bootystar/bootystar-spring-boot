package io.github.bootystar.autoconfigure.excel.fastexcel;


import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.DefaultConverterLoader;
import io.github.bootystar.autoconfigure.excel.ConverterRegister;
import io.github.bootystar.autoconfigure.excel.ExcelProperties;
import io.github.bootystar.autoconfigure.excel.fastexcel.converter.*;
import io.github.bootystar.autoconfigure.DateTimeFormatProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * easy excel转换器工具
 *
 * @author bootystar
 */
@Slf4j
public abstract class FastExcelConverterRegister {


    public static void registerConverters(ExcelProperties excelProperties, DateTimeFormatProperties dateTimeFormatProperties) {
        ExcelProperties.ConverterProperties converterProperties = excelProperties.getConverter();
        List<Converter> converters = new ArrayList<>();
        if (converterProperties.isBigDecimalToString()) {
            converters.add(new BigDecimalConverter());
        }
        if (converterProperties.isBigIntegerToString()) {
            converters.add(new BigIntergerConverter());
        }

        if (converterProperties.isLongToString()) {
            converters.add(new LongConverter());
        }
        if (converterProperties.isBooleanToString()) {
            converters.add(new BooleanConverter());
        }

        if (converterProperties.isFloatToString()) {
            converters.add(new FloatConverter());
        }
        if (converterProperties.isDoubleToString()) {
            converters.add(new DoubleConverter());
        }

        if (converterProperties.isSqlTimestampToString()) {
            converters.add(new SqlTimestampConverter(dateTimeFormatProperties.getDateTime()));
        }
        if (converterProperties.isSqlDateToString()) {
            converters.add(new SqlDateConverter(dateTimeFormatProperties.getDate()));
        }
        if (converterProperties.isSqlTimeToString()) {
            converters.add(new SqlTimeConverter(dateTimeFormatProperties.getTime()));
        }

        if (converterProperties.isLocalDateTimeToString()) {
            converters.add(new LocalDateTimeConverter(dateTimeFormatProperties.getDateTime()));
        }
        if (converterProperties.isLocalDateToString()) {
            converters.add(new LocalDateConverter(dateTimeFormatProperties.getDate()));
        }
        if (converterProperties.isLocalTimeToString()) {
            converters.add(new LocalTimeConverter(dateTimeFormatProperties.getTime()));
        }

        if (converterProperties.isDateToString()) {
            converters.add(new DateConverter(dateTimeFormatProperties.getDateTime(), dateTimeFormatProperties.getTimeZone()));
        }
        ConverterRegister.addConverters(DefaultConverterLoader.class, Converter.class, converters);
    }


}
