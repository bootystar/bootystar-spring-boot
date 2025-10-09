package io.github.bootystar.autoconfigure.aop;

import io.github.bootystar.autoconfigure.aop.aspectj.MethodLimitAspect;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.SignatureProvider;
import io.github.bootystar.autoconfigure.aop.handler.impl.RedissonMethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.ReentrantLockMethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.SpelSignatureProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Advice;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AOP（面向切面编程）自动配置
 *
 * @author bootystar
 * @see org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({Advice.class})
@ConditionalOnProperty(prefix = "bootystar.aop", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({AopProperties.class})
public class AopAutoConfiguration {

    /**
     * 注入方法限流切面。
     * <p>
     * 仅当容器中存在 MethodLimitHandler 的 Bean 时，此切面才会生效。
     *
     * @param signatureProvider 方法签名处理器
     * @param methodLimitHandler     方法限流处理器
     * @return 方法限流切面
     */
    @Bean
    @ConditionalOnMissingBean(MethodLimitAspect.class)
    @ConditionalOnBean({SignatureProvider.class, MethodLimitHandler.class})
    public MethodLimitAspect methodLimitAspect(SignatureProvider signatureProvider, MethodLimitHandler methodLimitHandler) {
        MethodLimitAspect methodLimitAspect = new MethodLimitAspect(signatureProvider, methodLimitHandler);
        log.debug("方法限流切面(MethodLimitAspect)已装配");
        return methodLimitAspect;
    }

    /**
     * 注入方法签名处理器，默认使用SpEL表达式处理器。
     * <p>
     * 允许用户通过定义自己的 SignatureProvider Bean 来覆盖此默认实现。
     *
     * @param aopProperties AOP配置属性
     * @return SpEL方法签名处理器
     */
    @Bean
    @ConditionalOnMissingBean(SignatureProvider.class)
    public SignatureProvider spelMethodSignatureHandler(AopProperties aopProperties) {
        return new SpelSignatureProvider(aopProperties.getMethodLimitPrefix());
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(RedissonClient.class) // 条件1: classpath下有RedissonClient类
    @ConditionalOnBean(RedissonClient.class)  // 条件2: Spring容器中已存在一个RedissonClient的Bean
    public class RedissonAopAutoConfiguration {
        /**
         * 注入基于 Redisson 的分布式方法限流处理器。
         * <p>
         * 仅当容器中不存在任何其他 MethodLimitHandler 类型的 Bean 时才会生效。
         * 这为用户提供了最高优先级，允许他们通过定义自己的 Bean 来覆盖此实现。
         *
         * @param redissonClient Spring 容器中已配置的 RedissonClient 实例
         * @return 基于 Redisson 的分布式方法限流处理器
         */
        @Bean
        @ConditionalOnMissingBean(MethodLimitHandler.class) // 条件3: 容器中没有其他MethodLimitHandler类型的Bean
        public MethodLimitHandler redissonMethodLimitHandler(RedissonClient redissonClient) {
            log.debug("已装配基于Redisson的分布式方法限流处理器(RedissonMethodLimitHandler)");
            return new RedissonMethodLimitHandler(redissonClient);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass({"RedissonClient.class"}) 
    @ConditionalOnMissingBean(RedissonClient.class)  
    public class ReentrantLockMethodLimitHandlerConfiguration {
        /**
         * 注入默认的方法限流处理器（基于本地内存的ReentrantLock）。
         * <p>
         * 这是一个后备选项，仅当容器中不存在任何其他 MethodLimitHandler 类型的 Bean 时才会生效。
         * 这使得用户可以轻松地通过提供自己的实现（例如基于Redis的分布式锁）来替换默认行为。
         *
         * @return 基于ReentrantLock的本地方法限流处理器
         */
        @Bean
        @ConditionalOnMissingBean(MethodLimitHandler.class)
        public MethodLimitHandler reentrantLockMethodLimitHandler() {
            log.debug("已装配默认的本地内存方法限流处理器(ReentrantLockMethodLimitHandler)");
            return new ReentrantLockMethodLimitHandler();
        }
    }


 
}