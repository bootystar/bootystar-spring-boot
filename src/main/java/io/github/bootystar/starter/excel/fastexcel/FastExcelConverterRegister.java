package io.github.bootystar.starter.excel.fastexcel;


import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.DefaultConverterLoader;
import io.github.bootystar.starter.excel.ConverterRegister;
import io.github.bootystar.starter.excel.fastexcel.converter.*;
import io.github.bootystar.starter.prop.BootystarProperties;
import io.github.bootystar.starter.prop.support.ExcelProperties;
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


    public static void registerConverters(BootystarProperties properties) {
        ExcelProperties.ConverterProperties converterProperties = properties.getExcel().getConverter();
        List<Converter> converters = new ArrayList<>();
        
        if (converterProperties.isBigDecimalToString()){
            converters.add(new BigDecimalConverter());
        }
        if (converterProperties.isBigIntegerToString()){
            converters.add(new BigIntergerConverter());
        }

        if (converterProperties.isLongToString()){
            converters.add(new LongConverter());
        }
        if (converterProperties.isBooleanToString()){
            converters.add(new BooleanConverter());
        }

        if (converterProperties.isFloatToString()){
            converters.add(new FloatConverter());
        }
        if (converterProperties.isDoubleToString()){
            converters.add(new DoubleConverter());
        }

        if (converterProperties.isSqlTimestampToString()){
            converters.add(new SqlTimestampConverter(properties.getDateTimeFormat()));
        }
        if (converterProperties.isSqlDateToString()){
            converters.add(new SqlDateConverter(properties.getDateFormat()));
        }
        if (converterProperties.isSqlTimeToString()){
            converters.add(new SqlTimeConverter(properties.getTimeFormat()));
        }

        if (converterProperties.isLocalDateTimeToString()){
            converters.add(new LocalDateTimeConverter(properties.getDateTimeFormat()));
        }
        if (converterProperties.isLocalDateToString()){
            converters.add(new LocalDateConverter(properties.getDateFormat()));
        }
        if (converterProperties.isLocalTimeToString()){
            converters.add(new LocalTimeConverter(properties.getTimeFormat()));
        }

        if (converterProperties.isDateToString()){
            converters.add(new DateConverter(properties.getDateTimeFormat(), properties.getTimeZoneId()));
        }
        ConverterRegister.addConverters(DefaultConverterLoader.class, Converter.class, converters);
    }

   


}
