package io.github.bootystar.starter.excel.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;


/**
 * 双转换器
 *
 * @author bootystar
 */
public class FloatConverter implements Converter<Float> {

    @Override
    public Class<Float> supportJavaTypeKey() {
        return Float.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Float convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        return Float.parseFloat(cellValue);
    }

    @Override
    public WriteCellData<String> convertToExcelData(Float value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(new BigDecimal(Float.toString(value)).toPlainString());
    }
}