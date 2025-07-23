package io.github.bootystar.starter.excel.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigInteger;


/**
 * BigInteger类型转换器
 * 用于在Excel和Java BigInteger类型之间进行转换
 *
 * @author bootystar
 */
public class BigIntergerConverter implements Converter<BigInteger> {

    @Override
    public Class<BigInteger> supportJavaTypeKey() {
        return BigInteger.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigInteger convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String cellValue = cellData.getStringValue();
        if (cellValue == null || cellValue.isEmpty()) {
            return null;
        }
        return new BigInteger(cellValue);
    }

    @Override
    public WriteCellData<String> convertToExcelData(BigInteger value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        if (value == null) {
            return new WriteCellData<>("");
        }
        return new WriteCellData<>(String.valueOf(value));
    }
}