package io.github.bootystar.autoconfigure.excel;

import io.github.bootystar.autoconfigure.DateTimeFormatProperties;
import io.github.bootystar.autoconfigure.excel.easyexcel.EasyExcelConverterRegister;
import io.github.bootystar.autoconfigure.excel.fastexcel.FastExcelConverterRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * excel配置
 *
 * @author bootystar
 */
@Slf4j
@AutoConfiguration
@ConditionalOnProperty(value = "bootystar.excel.auto", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ExcelProperties.class, DateTimeFormatProperties.class})
public class ExcelAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(cn.idev.excel.converters.DefaultConverterLoader.class)
    @RequiredArgsConstructor
    static class FastExcelConfiguration implements ApplicationRunner {
        private final DateTimeFormatProperties dateTimeFormatProperties;
        private final ExcelProperties excelProperties;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            FastExcelConverterRegister.registerConverters(excelProperties, dateTimeFormatProperties);
            log.debug("FastExcelConverterRegister register Configured");
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(com.alibaba.excel.converters.DefaultConverterLoader.class)
    @RequiredArgsConstructor
    static class EasyExcelConfiguration implements ApplicationRunner {
        private final DateTimeFormatProperties dateTimeFormatProperties;
        private final ExcelProperties excelProperties;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            EasyExcelConverterRegister.registerConverters(excelProperties, dateTimeFormatProperties);
            log.debug("EasyExcelConverterRegister register Configured");
        }
    }

}
