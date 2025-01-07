package io.github.bootystar.starter.autoconfigure;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.Collection;

/**
 * 自动配置类
 * spring自动配置的扫描顺序
 * 启动容器{@link SpringApplication#run(Class[], String[])}
 * 创建SpringApplication实例, 调用构造器 {@link SpringApplication#SpringApplication(Class[])}
 * 构造器中调用第二构造器{@link SpringApplication#SpringApplication(ResourceLoader, Class[])} ,其中ResourceLoader为null
 * 第二构造器调用区间, 将启动类作为primarySources, 设置this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
 * 第二构造器调用区间, 使用{@link WebApplicationType#deduceFromClasspath()} 获取创建的应用类型, 设置this.webApplicationType
 * 第二构造器调用区间, 使用{@link SpringApplication#getSpringFactoriesInstances(Class)}, 设置this.bootstrapRegistryInitializers
 * 第二构造器调用区间, 使用{@link SpringApplication#setListeners(Collection)}, 设置this.initializers = 预定义的ApplicationContextInitializer
 * 第二构造器调用区间, 使用{@link SpringApplication#setListeners(Collection)}, 设置this.listeners = 预定义的ApplicationListener
 * 第二构造器调用区间, 使用{@link SpringApplication#deduceMainApplicationClass()}通过抛出异常并捕获来获取主类所在包, this.mainApplicationClass
 * 实例创建完成后, 调用{@link SpringApplication#run(String...)}方法
 * 在run()方法中, 调用{@link SpringApplication#createBootstrapContext}方法, 并在其中触发所有的this.bootstrapRegistryInitializers的initialize()方法
 * 在run()方法中, 调用{@link SpringApplication#configureHeadlessProperty()}方法, 设置系统变量中的java.awt.headless=true
 * 在run()方法中, 调用{@link SpringApplication#getRunListeners(String[])}方法, 获取SpringApplicationRunListener, 在并逐个触发listeners.starting(bootstrapContext, this.mainApplicationClass);
 * 在run()方法中, 调用{@link SpringApplication#registerListeners(ApplicationContext)}方法, 在并逐个触发listeners.started(bootstrapContext, this.mainApplicationClass);
 * 在run()方法中, 进入try{}catch(){}块, 开始创建容器
 * 在run()方法中, 调用{@link SpringApplication#prepareEnvironment(SpringApplicationRunListeners, DefaultBootstrapContext, ApplicationArguments)} 准备创建容器
 * 在run()方法中, 调用{@link SpringApplication#printBanner(ConfigurableEnvironment)} 打印banner
 * 在run()方法中, 调用{@link SpringApplication#createApplicationContext()} 创建ApplicationContext容器,并setApplicationStartup(this.applicationStartup);
 * 在run()方法中, 调用{@link SpringApplication#prepareContext(DefaultBootstrapContext, ConfigurableApplicationContext, ConfigurableEnvironment, SpringApplicationRunListeners, ApplicationArguments, Banner)} 准备容器运行环境
 * 在run()-prepareContext()方法中,设置环境{@link ConfigurableApplicationContext#setEnvironment(ConfigurableEnvironment)}
 * 在run()-prepareContext()方法中,设置后置处理器{@link SpringApplication#postProcessApplicationContext(ConfigurableApplicationContext)}
 * 在run()-prepareContext()-postProcessApplicationContext()方法中,获取BeanFactory并设置用于类型转化的服务{@link ConfigurableBeanFactory#setConversionService(ConversionService)}
 * 在run()-prepareContext()方法中,添加初始化器{@link SpringApplication#addInitializers(ApplicationContextInitializer[])}
 * 在run()-prepareContext()方法中,向监听器发送容器准备事件listeners.contextPrepared(context);
 * 在run()-prepareContext()方法中,关闭DefaultBootstrapContext{@link DefaultBootstrapContext#close(ConfigurableApplicationContext)}
 * 在run()-prepareContext()方法中,打印启动信息和配置文件信息{@link SpringApplication#logStartupInfo(boolean)} {@link SpringApplication#logStartupProfileInfo(ConfigurableApplicationContext)}
 * 在run()-prepareContext()方法中,获取bean工厂{@link ConfigurableApplicationContext#getBeanFactory()}, 并调用bean工厂的{@link ConfigurableListableBeanFactory#registerSingleton(String, Object)}注册单例bean
 * 在run()-prepareContext()方法中,注册单例bean时,若为懒加载, 通过{@link ConfigurableApplicationContext#addBeanFactoryPostProcessor(BeanFactoryPostProcessor)}将{@link LazyInitializationBeanFactoryPostProcessor}注册到容器中
 * 在run()-prepareContext()方法中,通过{@link ConfigurableApplicationContext#addBeanFactoryPostProcessor(BeanFactoryPostProcessor)}将{@link SpringApplication.PropertySourceOrderingBeanFactoryPostProcessor}注册到容器中
 * 在run()-prepareContext()方法中,通过{@link SpringApplication#getAllSources()} 获取资源文件
 * 在run()-prepareContext()方法中,通过{@link SpringApplication#load(ApplicationContext, Object[])} 加载资源文件
 * 在run()-prepareContext()方法中,发送容器已加载的事件listeners.contextLoaded(context);
 * 在run()方法中, 刷新容器, 使用{@link SpringApplication#refreshContext(ConfigurableApplicationContext)},注册ShutdownHook, 之后调用{@link ConfigurableApplicationContext#refresh()}方法刷新容器
 * ---------核心:{@link AbstractApplicationContext#refresh()}------------------
 * 调用{@link AbstractApplicationContext#prepareRefresh()}
 * 在prepareRefresh()方法中, 设置上下文状态为活动, 重置关闭标志, 记录启动时间
 * 在prepareRefresh()方法中, 调用{@link AbstractApplicationContext#initPropertySources()}, 该方法用于子类初始化属性源
 * 在prepareRefresh()方法中, 调用{@link AbstractApplicationContext#getEnvironment()} ()}并调用{@link AbstractEnvironment#validateRequiredProperties()} 验证必要属性
 * 在prepareRefresh()方法中, 保存或重置早期应用监听器, 并创建一个集合来储存走起应用事件
 * todo 多余源码 过于复杂, 需要时再追, 略.......
 * 调用{@link AbstractApplicationContext#finishBeanFactoryInitialization(ConfigurableListableBeanFactory)}创建所有单例bean
 * 在finishBeanFactoryInitialization()方法中, 调用{@link ConfigurableBeanFactory#setConversionService(ConversionService)}设置类型转化器
 * 在finishBeanFactoryInitialization()方法中, 如果没有注册过嵌入值解析器，则使用环境变量解析占位符并注册
 * 在finishBeanFactoryInitialization()方法中, 初始化 LoadTimeWeaverAware 类型的 Bean：提前初始化这些Bean以便尽早注册它们的转换器
 * 在finishBeanFactoryInitialization()方法中, 停止使用临时类加载器, 将 BeanFactory 的临时类加载器设置为 null
 * 在finishBeanFactoryInitialization()方法中, 冻结配置, 允许缓存所有 Bean 定义元数据，防止进一步更改{@link DefaultListableBeanFactory#freezeConfiguration()}
 * 在finishBeanFactoryInitialization()方法中, 预实例化单例 Bean：实例化所有非懒加载的单例 Bean, {@link DefaultListableBeanFactory#preInstantiateSingletons}。
 * 在preInstantiateSingletons()方法中, 判断bean是否为FactoryBean, 之后使用{@link DefaultListableBeanFactory#getBean(String)}方法获取bean
 * 在getBean()方法中, 调用{@link DefaultListableBeanFactory#doGetBean(String, Class, Object[], boolean)}方法获取bean
 *
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
 * @author bootystar
 */
public class SourceHunter {
}
