//package io.github.bootystar.starter.autoconfigure.temp;
//
//import io.github.bootystar.starter.spring.aspect.MethodLimitAspect;
//import io.github.bootystar.starter.spring.handler.MethodLimitHandler;
//import io.github.bootystar.starter.spring.handler.impl.RedissonMethodLimitHandler;
//import io.github.bootystar.starter.spring.handler.impl.ReentrantLockMethodLimitHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.weaver.Advice;
//import org.redisson.api.RedissonClient;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//
///**
// * bootystar aop自动配置
// *
// * @author bootystar
// * @see AopAutoConfiguration
// */
//@Slf4j
//@AutoConfiguration(after = {AopAutoConfiguration.class})
//@ConditionalOnClass({Advice.class})
//@ConditionalOnProperty(prefix = "bootystar.aop", name = "enabled", havingValue = "true", matchIfMissing = true)
//public class BootystarAopAutoConfiguration {
//
//    /**
//     * 方法限流切面
//     *
//     * @return {@link MethodLimitAspect }
//     * @author bootystar
//     * @see org.redisson.spring.starter.RedissonAutoConfiguration
//     */
//    @Bean
//    public MethodLimitAspect methodLimitAspect(ApplicationContext applicationContext) {
//        MethodLimitAspect methodLimitAspect = new MethodLimitAspect();
//        try {
//            Class<?> clazz = Class.forName("org.redisson.api.RedissonClient");
//            Object bean = applicationContext.getBean(clazz);
//            RedissonMethodLimitHandler redissonHandler = new RedissonMethodLimitHandler((RedissonClient) bean);
//            methodLimitAspect.allocateLimitHandler(MethodLimitHandler.class, redissonHandler);
//            log.debug("MethodLimitHandlerRedissonImpl Configured");
//        } catch (Exception e) {
//            methodLimitAspect.allocateLimitHandler(MethodLimitHandler.class, new ReentrantLockMethodLimitHandler());
//            log.debug("MethodLimitHandlerReentrantLockImpl Configured");
//        }
//        return methodLimitAspect;
//    }
//
//
//}
