package io.github.bootystar.starter.prop.support;

import lombok.Data;

/**
 * Excel配置属性类
 * 用于配置Excel相关属性
 *
 * @author bootystar
 */
@Data
public class ExcelProperties {
    /**
     * 是否初始化FastExcel转换器
     */
    private boolean initFastExcelConverter = true;
    
    /**
     * 是否初始化EasyExcel转换器
     */
    private boolean initEasyExcelConverter = true;
    
    /**
     * Excel转换器配置
     */
    private ConverterProperties converter = new ConverterProperties();
    
    @Data
    public static class ConverterProperties {
        /**
         * BigDecimal转字符串
         */
        private boolean bigDecimalToString = true;
        
        /**
         * BigInteger转字符串
         */
        private boolean bigIntegerToString = true;

        /**
         * Long转字符串
         */
        private boolean longToString = true;
        
        /**
         * Boolean转字符串
         */
        private boolean booleanToString = true;

        /**
         * Float转字符串
         */
        private boolean floatToString = true;
        
        /**
         * Double转字符串
         */
        private boolean doubleToString = true;

        /**
         * SQL时间戳转字符串
         */
        private boolean sqlTimestampToString = true;
        
        /**
         * SQL日期转字符串
         */
        private boolean sqlDateToString = true;
        
        /**
         * SQL时间转字符串
         */
        private boolean sqlTimeToString = true;

        /**
         * LocalDateTime转字符串
         */
        private boolean localDateTimeToString = true;
        
        /**
         * LocalDate转字符串
         */
        private boolean localDateToString = true;
        
        /**
         * LocalTime转字符串
         */
        private boolean localTimeToString = true;

        /**
         * Date转字符串
         */
        private boolean dateToString = true;
    }
}