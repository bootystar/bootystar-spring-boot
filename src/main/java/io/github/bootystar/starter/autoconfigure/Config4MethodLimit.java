package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.spring.aop.aspect.MethodLimitAspect;
import io.github.bootystar.starter.spring.aop.handler.MethodLimitHandler;
import io.github.bootystar.starter.spring.aop.handler.MethodSignatureHandler;
import io.github.bootystar.starter.spring.aop.handler.impl.MethodLimitHandlerImpl;
import io.github.bootystar.starter.spring.aop.handler.impl.MethodLimitHandlerRedissonImpl;
import io.github.bootystar.starter.spring.aop.handler.impl.MethodSignatureHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Advice;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

/**
 * @author bootystar
 */
@Slf4j
@ConditionalOnClass({AopAutoConfiguration.class, Advice.class})
@AutoConfiguration(after = AopAutoConfiguration.class)
public class Config4MethodLimit implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public MethodLimitAspect methodLimitAspect() {
        MethodLimitAspect methodLimitAspect = new MethodLimitAspect();
        methodLimitAspect.setSignatureHandler(MethodSignatureHandler.class, new MethodSignatureHandlerImpl());
        try {
            Class<?> clazz = Class.forName("org.redisson.api.RedissonClient");
            Object bean = applicationContext.getBean(clazz);
            MethodLimitHandlerRedissonImpl redissonHandler = new MethodLimitHandlerRedissonImpl((RedissonClient) bean);
            methodLimitAspect.setLimitHandler(MethodLimitHandler.class, redissonHandler);
            log.debug("MethodLimitHandlerRedissonImpl Configured");
        } catch (Exception e) {
            methodLimitAspect.setLimitHandler(MethodLimitHandler.class, new MethodLimitHandlerImpl());
            log.debug("MethodLimitHandlerImpl Configured");
        }
        return methodLimitAspect;
    }


}
