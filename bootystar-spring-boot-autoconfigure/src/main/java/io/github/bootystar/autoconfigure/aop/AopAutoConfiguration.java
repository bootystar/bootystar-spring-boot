package io.github.bootystar.autoconfigure.aop;

import io.github.bootystar.autoconfigure.aop.aspectj.MethodLimitAspect;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.RedissonMethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.ReentrantLockMethodLimitHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Advice;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * aop配置
 * @author bootystar
 * @see org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Advice.class})
@ConditionalOnProperty(value = "bootystar.aop.enabled", havingValue = "true", matchIfMissing = true)
public class AopAutoConfiguration {

    @Bean
    @ConditionalOnBean(MethodLimitAspect.class)
    public MethodLimitAspect methodLimitAspect(ApplicationContext applicationContext) {
        MethodLimitAspect methodLimitAspect = new MethodLimitAspect();
        try {
            Class<?> clazz = Class.forName("org.redisson.api.RedissonClient");
            Object bean = applicationContext.getBean(clazz);
            RedissonMethodLimitHandler redissonHandler = new RedissonMethodLimitHandler((RedissonClient) bean);
            methodLimitAspect.allocateLimitHandler(MethodLimitHandler.class, redissonHandler);
            log.debug("MethodLimitHandlerRedissonImpl Configured");
        } catch (Exception e) {
            methodLimitAspect.allocateLimitHandler(MethodLimitHandler.class, new ReentrantLockMethodLimitHandler());
            log.debug("MethodLimitHandlerReentrantLockImpl Configured");
        }
        return methodLimitAspect;
    }


}
