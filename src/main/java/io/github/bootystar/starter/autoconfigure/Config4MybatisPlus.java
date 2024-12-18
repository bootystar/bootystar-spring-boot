package io.github.bootystar.starter.autoconfigure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author bootystar
 */
@Slf4j
@ConditionalOnClass({MybatisPlusAutoConfiguration.class, MybatisPlusInterceptor.class})
@AutoConfiguration(after = MybatisPlusAutoConfiguration.class)
public class Config4MybatisPlus {

    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    @ConditionalOnBean(MybatisPlusAutoConfiguration.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new DbInterceptor());
        log.debug("MybatisPlusInterceptor Configured");
        optimisticLockerInnerInterceptor(interceptor);
        paginationInnerInterceptor(interceptor);
        blockAttackInnerInterceptor(interceptor);
        return interceptor;
    }

    public void optimisticLockerInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("OptimisticLockerInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("OptimisticLockerInnerInterceptor not found, skip");
        }
    }

    public void paginationInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("PaginationInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("PaginationInnerInterceptor not found, skip");
        }
    }

    public void blockAttackInnerInterceptor(MybatisPlusInterceptor interceptor) {
        try {
            Class<?> clazz = Class.forName("com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor");
            Object instance = clazz.getConstructor().newInstance();
            interceptor.addInnerInterceptor((InnerInterceptor) instance);
            log.debug("BlockAttackInnerInterceptor Configured");
        }catch (Exception e) {
            log.debug("BlockAttackInnerInterceptor not found, skip");
        }
    }


}
