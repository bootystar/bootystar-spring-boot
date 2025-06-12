package io.github.bootystar.starter.excel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author bootystar
 */
@Slf4j
public abstract class ConverterRegister {
    public static final String easyExcelLoaderClass = "com.alibaba.excel.converters.DefaultConverterLoader";
    public static final String fastExcelLoaderClass = "cn.idev.excel.converters.DefaultConverterLoader";
    
    @SneakyThrows
    public static <T> void addConverters(Class<?> converterLoaderClass, Class<T> converterClass, List<T> converters) {
        if (converters==null || converters.isEmpty()){
            log.debug("no converters need to add for {}", converterClass.getName());
            return;
        }
        Method putWriteConverter = converterLoaderClass.getDeclaredMethod("putWriteConverter", converterClass);
        putWriteConverter.setAccessible(true);
        Method putAllConverter = converterLoaderClass.getDeclaredMethod("putAllConverter", converterClass);
        putAllConverter.setAccessible(true);
        for (Object converter : converters) {
            putWriteConverter.invoke(null, converter);
            putAllConverter.invoke(null, converter);
        }
        log.debug("success added {} converters for {}", converters.size(), converterClass.getName());
    }
    
    
  
        
}
