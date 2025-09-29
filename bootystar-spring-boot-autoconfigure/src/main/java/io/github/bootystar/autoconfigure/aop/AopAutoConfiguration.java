package io.github.bootystar.autoconfigure.aop;

import io.github.bootystar.autoconfigure.aop.aspectj.MethodLimitAspect;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.SpelMethodSignatureHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Advice;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * aop配置
 * @author bootystar
 * @see org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass({Advice.class})
@ConditionalOnProperty(value = "bootystar.aop.auto", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({AopProperties.class})
public class AopAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(MethodLimitAspect.class)
    @ConditionalOnBean(MethodLimitHandler.class)
    public MethodLimitAspect methodLimitAspect(MethodLimitHandler methodLimitHandler) {
        MethodLimitAspect methodLimitAspect = new MethodLimitAspect(new SpelMethodSignatureHandler(), methodLimitHandler);
        log.debug("MethodLimitAspect Configured");
        return methodLimitAspect;
    }


}
