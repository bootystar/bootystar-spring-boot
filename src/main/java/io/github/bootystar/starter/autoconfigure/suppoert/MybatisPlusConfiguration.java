package io.github.bootystar.starter.autoconfigure.suppoert;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 * @author bootystar
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({BaseMapper.class, MybatisPlusInterceptor.class})
@ConditionalOnProperty(prefix = "bootystar.mybatis-plus", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MybatisPlusConfiguration {

    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        optimisticLockerInnerInterceptor(interceptor);
        paginationInnerInterceptor(interceptor);
        blockAttackInnerInterceptor(interceptor);
        log.debug("MybatisPlusInterceptor Configured");
        return interceptor;
    }

    public void optimisticLockerInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("OptimisticLockerInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("OptimisticLockerInnerInterceptor not found, skipped");
        }
    }

    public void paginationInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("PaginationInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("PaginationInnerInterceptor not found, skipped");
        }
    }

    public void blockAttackInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("BlockAttackInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("BlockAttackInnerInterceptor not found, skipped");
        }
    }


}
