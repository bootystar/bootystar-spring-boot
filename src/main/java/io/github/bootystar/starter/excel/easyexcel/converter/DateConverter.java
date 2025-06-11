package io.github.bootystar.starter.excel.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 日期转换器
 *
 * @author bootystar
 */
public class DateConverter implements Converter<Date> {
    private final DateTimeFormatter formatter;
    private final ZoneId zoneId;

    public DateConverter(String pattern, String zoneId) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
        this.zoneId = ZoneId.of(zoneId);
    }

    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Date convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        return Date.from(LocalDateTime.parse(cellValue, formatter).atZone(zoneId).toInstant());
    }

    @Override
    public WriteCellData<String> convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(value.toInstant().atZone(zoneId).toLocalDateTime().format(formatter));
    }
}