package io.github.bootystar.autoconfigure.core;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import io.github.bootystar.autoconfigure.prop.MybatisPlusProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * bootystar mybatis plus自动配置
 * @see com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
 * @author bootystar
 */
@Slf4j
@ConditionalOnClass({com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class, MybatisPlusInterceptor.class})
@AutoConfiguration(after = com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class)
@EnableConfigurationProperties(MybatisPlusProperties.class)
@ConditionalOnProperty(prefix = "bootystar.mybatis-plus", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BootystarMybatisPlusAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    @ConditionalOnBean(com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new DbInterceptor());
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
