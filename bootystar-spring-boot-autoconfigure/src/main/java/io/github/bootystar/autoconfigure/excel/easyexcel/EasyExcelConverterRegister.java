package io.github.bootystar.autoconfigure.excel.easyexcel;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.DefaultConverterLoader;
import io.github.bootystar.autoconfigure.excel.ConverterRegister;
import io.github.bootystar.autoconfigure.excel.ExcelProperties;
import io.github.bootystar.autoconfigure.excel.easyexcel.converter.*;
import io.github.bootystar.autoconfigure.BootystarProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * easy excel转换器工具
 *
 * @author bootystar
 */
@Slf4j
public abstract class EasyExcelConverterRegister {

    public static void registerConverters(ExcelProperties excelProperties,BootystarProperties bootystarProperties) {
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
            converters.add(new SqlTimestampConverter(bootystarProperties.getDateTimeFormat()));
        }
        if (converterProperties.isSqlDateToString()) {
            converters.add(new SqlDateConverter(bootystarProperties.getDateFormat()));
        }
        if (converterProperties.isSqlTimeToString()) {
            converters.add(new SqlTimeConverter(bootystarProperties.getTimeFormat()));
        }

        if (converterProperties.isLocalDateTimeToString()) {
            converters.add(new LocalDateTimeConverter(bootystarProperties.getDateTimeFormat()));
        }
        if (converterProperties.isLocalDateToString()) {
            converters.add(new LocalDateConverter(bootystarProperties.getDateFormat()));
        }
        if (converterProperties.isLocalTimeToString()) {
            converters.add(new LocalTimeConverter(bootystarProperties.getTimeFormat()));
        }

        if (converterProperties.isDateToString()) {
            converters.add(new DateConverter(bootystarProperties.getDateTimeFormat(), bootystarProperties.getTimeZoneId()));
        }
        ConverterRegister.addConverters(DefaultConverterLoader.class, Converter.class, converters);
    }


}
