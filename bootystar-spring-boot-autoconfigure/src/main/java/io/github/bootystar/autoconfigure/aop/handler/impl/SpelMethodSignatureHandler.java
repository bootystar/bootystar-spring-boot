package io.github.bootystar.autoconfigure.aop.handler.impl;

import io.github.bootystar.autoconfigure.aop.handler.MethodSignatureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 基于 SpEL (Spring表达式语言) 的方法签名处理器实现。
 *
 * @author bootystar
 */
@RequiredArgsConstructor
public class SpelMethodSignatureHandler implements MethodSignatureHandler {

    /**
     * SpEL表达式解析器。这是一个重量级且线程安全的对象，因此声明为静态常量以供重用。
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    /**
     * 方法参数名发现器。这也是一个可重用的线程安全对象。
     */
    private static final ParameterNameDiscoverer PND = new DefaultParameterNameDiscoverer();

    /**
     * 签名（锁键）的前缀。
     */
    private final String prefix;

    @Override
    public String signature(Object target, Method method, Object[] args, String expression) {
        String methodString = method.toGenericString();
        StringBuilder keyBuilder = new StringBuilder(prefix).append(methodString);

        // 如果SpEL表达式不为空，则解析表达式并附加结果
        if (StringUtils.hasText(expression)) {
            MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(target, method, args, PND);
            Object value = PARSER.parseExpression(expression).getValue(context);
            keyBuilder.append(':').append(value);
            return keyBuilder.toString();
        }

        // 如果表达式为空，则拼接所有参数作为键的一部分
        if (args != null && args.length > 0) {
            keyBuilder.append(':');
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    keyBuilder.append(',');
                }
                // StringBuilder.append(Object) 会自动处理 null 并附加字符串 "null"
                keyBuilder.append(args[i]);
            }
        }

        return keyBuilder.toString();
    }

}
