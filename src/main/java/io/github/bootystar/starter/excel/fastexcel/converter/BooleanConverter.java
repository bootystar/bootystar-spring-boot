package io.github.bootystar.starter.excel.fastexcel.converter;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;


/**
 * Boolean类型转换器
 * 用于在Excel和Java Boolean类型之间进行转换
 *
 * @author bootystar
 */
public class BooleanConverter implements Converter<Boolean> {


    @Override
    public Class<Boolean> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        return "是".equals(cellValue) || "TRUE".equalsIgnoreCase(cellValue);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(value ? "是" : "否");
    }
}