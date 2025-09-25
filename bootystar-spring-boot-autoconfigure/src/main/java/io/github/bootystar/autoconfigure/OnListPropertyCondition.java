package io.github.bootystar.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;
import java.util.Map;

/**
 * “在列表上” 属性条件
 *
 * @author bootystar
 */
public class OnListPropertyCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        // 获取注解中的属性
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnListProperty.class.getName());
        if (attributes == null) {
            return ConditionOutcome.noMatch("No ConditionalOnListProperty annotation found");
        }
        String propertyName = (String) attributes.get("value");
        boolean matchIfEmpty = (boolean) attributes.get("matchIfEmpty");

        // 检查属性是否存在
        if (!env.containsProperty(propertyName)) {
            if (matchIfEmpty) {
                return ConditionOutcome.match("Property " + propertyName + " is missing and matchIfEmpty=true");
            } else {
                return ConditionOutcome.noMatch("Property " + propertyName + " is missing and matchIfEmpty=false");
            }
        }

        // 尝试获取List配置
        List<?> propertyList = env.getProperty(propertyName, List.class);

        if (propertyList != null && !propertyList.isEmpty()) {
            return ConditionOutcome.match("Property " + propertyName + " is a non-empty list");
        }

        if (matchIfEmpty) {
            return ConditionOutcome.match("Property " + propertyName + " is a not a list or is empty and matchIfEmpty=true");
        } else {
            return ConditionOutcome.noMatch("Property " + propertyName + " not a list or is empty and matchIfEmpty=false");
        }
    }
}