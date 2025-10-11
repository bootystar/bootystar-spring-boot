package io.github.bootystar.autoconfigure.aop;

import io.github.bootystar.autoconfigure.aop.aspectj.RateLimitAspect;
import io.github.bootystar.autoconfigure.aop.handler.RateLimiter;
import io.github.bootystar.autoconfigure.aop.handler.SignatureProvider;
import io.github.bootystar.autoconfigure.aop.handler.impl.RedisRateLimiter;
import io.github.bootystar.autoconfigure.aop.handler.impl.SpelSignatureProvider;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Advice;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Bean
    @ConditionalOnMissingBean(RateLimitAspect.class)
    @ConditionalOnBean({SignatureProvider.class, RateLimiter.class})
    public RateLimitAspect methodLimitAspect(BeanFactory beanFactory) {
        RateLimitAspect rateLimitAspect = new RateLimitAspect(beanFactory);
        log.debug("RateLimitAspect Configured");
        return rateLimitAspect;
    }

    @Bean
    @ConditionalOnMissingBean(SignatureProvider.class)
    public SignatureProvider spelMethodSignatureHandler(AopProperties aopProperties) {
        log.debug("SpelSignatureProvider Configured");
        return new SpelSignatureProvider(aopProperties.getMethodLimitPrefix());
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnBean(RedisTemplate.class)
    static class RedisAopAutoConfiguration {
        @Bean
        @ConditionalOnMissingBean(RateLimiter.class)
        public RateLimiter redisRateLimiter(RedisTemplate<Object, Object> redisTemplate) {
            log.debug("RedisRateLimiter Configured");
            return new RedisRateLimiter(redisTemplate);
        }
    }

}