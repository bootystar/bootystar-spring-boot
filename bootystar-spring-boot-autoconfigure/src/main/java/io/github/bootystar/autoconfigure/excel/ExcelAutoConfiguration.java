package io.github.bootystar.autoconfigure.excel;

import io.github.bootystar.autoconfigure.BootystarProperties;
import io.github.bootystar.autoconfigure.excel.easyexcel.EasyExcelConverterRegister;
import io.github.bootystar.autoconfigure.excel.fastexcel.FastExcelConverterRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * excel配置
 *
 * @author bootystar
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "bootystar.excel", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({BootystarProperties.class, ExcelProperties.class})
public class ExcelAutoConfiguration implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ExcelProperties excelProperties = applicationContext.getBean(ExcelProperties.class);
        BootystarProperties bootystarProperties = applicationContext.getBean(BootystarProperties.class);
        if (excelProperties.isInitFastExcelConverter()) {
            try {
                Class.forName("cn.idev.excel.FastExcel");
                FastExcelConverterRegister.registerConverters(excelProperties, bootystarProperties);
                log.debug("FastExcelConverterRegister init success");
            } catch (ClassNotFoundException e) {
                log.debug("not class found , FastExcelConverterRegister won't work");
            } catch (Exception e) {
                log.debug("FastExcelConverterRegister init failed", e);
            }
        }
        if (excelProperties.isInitEasyExcelConverter()) {
            try {
                Class.forName("com.alibaba.excel.EasyExcel");
                EasyExcelConverterRegister.registerConverters(excelProperties, bootystarProperties);
                log.debug("EasyExcelConverterRegister init success");
            } catch (ClassNotFoundException e) {
                log.debug("not class found , EasyExcelConverterRegister won't work");
            } catch (Exception e) {
                log.debug("EasyExcelConverterRegister init failed", e);
            }
        }

    }

}
