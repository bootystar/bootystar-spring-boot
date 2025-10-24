package io.github.luxmixus.autoconfigure.excel.easyexcel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterLoader;
import io.github.luxmixus.autoconfigure.excel.ConverterRegister;
import io.github.luxmixus.autoconfigure.excel.ExcelProperties;
import io.github.luxmixus.autoconfigure.excel.easyexcel.converter.*;
import io.github.luxmixus.autoconfigure.DateTimeFormatProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * easy excel转换器工具
 *
 * @author luxmixus
 */
@Slf4j
public abstract class EasyExcelConverterRegister {

    public static void registerConverters(ExcelProperties excelProperties, DateTimeFormatProperties dateTimeFormatProperties) {
        List<Converter> converters = new ArrayList<>();
        if (excelProperties.isBigDecimalToString()) {
            converters.add(new BigDecimalConverter());
        }
        if (excelProperties.isBigIntegerToString()) {
            converters.add(new BigIntergerConverter());
        }

        if (excelProperties.isLongToString()) {
            converters.add(new LongConverter());
        }
        if (excelProperties.isBooleanToString()) {
            converters.add(new BooleanConverter());
        }

        if (excelProperties.isFloatToString()) {
            converters.add(new FloatConverter());
        }
        if (excelProperties.isDoubleToString()) {
            converters.add(new DoubleConverter());
        }

        if (excelProperties.isSqlTimestampToString()) {
            converters.add(new SqlTimestampConverter(dateTimeFormatProperties.getDateTime()));
        }
        if (excelProperties.isSqlDateToString()) {
            converters.add(new SqlDateConverter(dateTimeFormatProperties.getDate()));
        }
        if (excelProperties.isSqlTimeToString()) {
            converters.add(new SqlTimeConverter(dateTimeFormatProperties.getTime()));
        }

        if (excelProperties.isLocalDateTimeToString()) {
            converters.add(new LocalDateTimeConverter(dateTimeFormatProperties.getDateTime()));
        }
        if (excelProperties.isLocalDateToString()) {
            converters.add(new LocalDateConverter(dateTimeFormatProperties.getDate()));
        }
        if (excelProperties.isLocalTimeToString()) {
            converters.add(new LocalTimeConverter(dateTimeFormatProperties.getTime()));
        }

        if (excelProperties.isDateToString()) {
            converters.add(new DateConverter(dateTimeFormatProperties.getDateTime(), dateTimeFormatProperties.getTimeZone()));
        }
        ConverterRegister.addConverters(DefaultConverterLoader.class, Converter.class, converters);
    }


}
