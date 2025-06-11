package io.github.bootystar.starter.excel.fastexcel.converter;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * sql时间转换器
 *
 * @author bootystar
 */
public class SqlTimeConverter implements Converter<Time> {
    private final DateTimeFormatter formatter;

    public SqlTimeConverter(String pattern) {
        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public Class<Time> supportJavaTypeKey() {
        return Time.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Time convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        return Time.valueOf(LocalTime.parse(cellValue, formatter));
    }

    @Override
    public WriteCellData<String> convertToExcelData(Time value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(value.toLocalTime().format(formatter));
    }
}