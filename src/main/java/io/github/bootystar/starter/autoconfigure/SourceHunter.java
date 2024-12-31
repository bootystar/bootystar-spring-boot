package io.github.bootystar.starter.autoconfigure;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Collection;

/**
 * 自动配置类
 * <p>
 * spring自动配置的扫描顺序
 * <p>
 * 启动容器{@link SpringApplication#run(Class[], String[])}
 * <p>
 * 创建SpringApplication实例, 调用构造器 {@link SpringApplication#SpringApplication(Class[])}
 * <p>
 * 构造器中调用第二构造器{@link SpringApplication#SpringApplication(ResourceLoader, Class[])} ,其中ResourceLoader为null
 * <p>
 * 第二构造器调用区间, 将启动类作为primarySources, 设置this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
 * <p>
 * 第二构造器调用区间, 使用{@link WebApplicationType#deduceFromClasspath()} 获取创建的应用类型, 设置this.webApplicationType
 * <p>
 * 第二构造器调用区间, 使用{@link SpringApplication#getSpringFactoriesInstances(Class)}, 设置this.bootstrapRegistryInitializers
 * <p>
 * 第二构造器调用区间, 使用{@link SpringApplication#setListeners(Collection)}, 设置this.initializers = 预定义的ApplicationContextInitializer
 * <p>
 * 第二构造器调用区间, 使用{@link SpringApplication#setListeners(Collection)}, 设置this.listeners = 预定义的ApplicationListener
 * <p>
 * 第二构造器调用区间, 使用{@link SpringApplication#deduceMainApplicationClass()}通过抛出异常并捕获来获取主类所在包, this.mainApplicationClass
 * <p>
 * 实例创建完成后, 调用{@link SpringApplication#run(String...)}方法
 * <p>
 * 在run()方法中, 调用{@link SpringApplication#createBootstrapContext}方法, 并在其中触发所有的this.bootstrapRegistryInitializers的initialize()方法
 * <p>
 * 在run()方法中, 调用{@link SpringApplication#configureHeadlessProperty()}方法, 设置系统变量中的java.awt.headless=true
 * <p>
 * 在run()方法中, 调用{@link SpringApplication#getRunListeners(String[])}方法, 获取SpringApplicationRunListener, 在并逐个触发listeners.starting(bootstrapContext, this.mainApplicationClass);
 * <p>
 * 在run()方法中, 调用{@link SpringApplication#registerListeners(ApplicationContext)}方法, 在并逐个触发listeners.started(bootstrapContext, this.mainApplicationClass);
 * <p>
 * 在run()方法中, 进入try{}catch(){}块, 开始创建容器
 * 在run()方法中, 调用{@link SpringApplication#prepareEnvironment(SpringApplicationRunListeners, DefaultBootstrapContext, ApplicationArguments)} 准备创建容器
 * todo 继续追踪源码
 *
 *
 *
 *
 *
 * todo 自动导入包的逻辑
 * @see org.springframework.context.annotation.ConditionEvaluator#shouldSkip(AnnotatedTypeMetadata) 条件注解判断源码
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup#process(AnnotationMetadata, DeferredImportSelector) 处理需要导入的类
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup#getAutoConfigurationEntry(AnnotationMetadata) 获取所有自动配置类
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup#getCandidateConfigurations(AnnotationMetadata, AnnotationAttributes) 先获取所有非spring官方的自动配置类, 按jar包定义的顺序排序, 再获取spring的自动配置类
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup#selectImports()  处理配置类, 重新排序配置类的先后顺序, 按实现的order顺序排序(未指定时均为0), 若相同, 则按上一步的获取顺序返回
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup
 * @see AutoConfigurationImportSelector.AutoConfigurationGroup#selectImports(AnnotationMetadata)
 * @author bootystar
 */
public class SourceHunter {
}
