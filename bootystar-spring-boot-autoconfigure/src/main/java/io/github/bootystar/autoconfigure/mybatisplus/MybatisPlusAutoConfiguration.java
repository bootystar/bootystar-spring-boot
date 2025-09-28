package io.github.bootystar.autoconfigure.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus配置
 *
 * @author bootystar
 * @see com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({BaseMapper.class})
@ConditionalOnProperty(value = "bootystar.mybatis-plus.auto", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class MybatisPlusAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(MybatisPlusInterceptor.class)
    static class mybatisPlusInterceptorConfiguration {
        @Bean
        @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
        public MybatisPlusInterceptor mybatisPlusInterceptor(MybatisPlusProperties mybatisPlusProperties) {
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            if (mybatisPlusProperties.isOptimisticLocker()) {
                optimisticLockerInnerInterceptor(interceptor);
            }
            if (mybatisPlusProperties.isPagination()) {
                paginationInnerInterceptor(interceptor);
            }
            if (mybatisPlusProperties.isBlockAttack()) {
                blockAttackInnerInterceptor(interceptor);
            }
            log.debug("MybatisPlusInterceptor Configured");
            return interceptor;
        }

        public void optimisticLockerInnerInterceptor(MybatisPlusInterceptor interceptor) {
            try {
                Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor");
                Object instance = clazz.getConstructor().newInstance();
                interceptor.addInnerInterceptor((InnerInterceptor) instance);
                log.debug("OptimisticLockerInnerInterceptor Configured");
            } catch (Exception e) {
                log.debug("OptimisticLockerInnerInterceptor not found, skipped");
            }
        }

        public void paginationInnerInterceptor(MybatisPlusInterceptor interceptor) {
            try {
                Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor");
                Object instance = clazz.getConstructor().newInstance();
                interceptor.addInnerInterceptor((InnerInterceptor) instance);
                log.debug("PaginationInnerInterceptor Configured");
            } catch (Exception e) {
                log.debug("PaginationInnerInterceptor not found, skipped");
            }
        }

        public void blockAttackInnerInterceptor(MybatisPlusInterceptor interceptor) {
            try {
                Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor");
                Object instance = clazz.getConstructor().newInstance();
                interceptor.addInnerInterceptor((InnerInterceptor) instance);
                log.debug("BlockAttackInnerInterceptor Configured");
            } catch (Exception e) {
                log.debug("BlockAttackInnerInterceptor not found, skipped");
            }
        }
    }


}
