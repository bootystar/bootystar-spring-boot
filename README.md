# bootystar-spring-boot-starter

* 注解方法限流
* spring参数转化解析器(时间/日期/高精度数字)
* json序列化/反序列化
* json字段注解加解密
* mybatis-plus防全表更新/分页/防注入插件
* redis序列化/反序列化
* easyexcel及fastexcel添加额外转化器(时间/日期/高精度数字)

## maven依赖
```xml
<dependency>
    <groupId>io.github.bootystar</groupId>
    <artifactId>bootystar-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 通过属性定制功能并控制开关
在`application.yml`中配置
```yaml
bootystar:
  date-format: yyyy-MM-dd
  date-time-format: yyyy-MM-dd HH:mm:ss
  time-format: HH:mm:ss
  time-zone: GMT+8
  converter:
    enabled: true
    string-to-date: true
    string-to-local-date: true
    string-to-local-date-time: true
    string-to-local-time: true
  jackson:
    enabled: true
    long-to-string: true
    big-decimal-to-string: true
    big-integer-to-string: true
  excel:
    init-fast-excel: true
    init-easy-excel: true
    long-to-string: true
    big-decimal-to-string: true
    big-integer-to-string: true
    local-date-time-to-string: true

```
可配置项:[BootystarProperties.java](src/main/java/io/github/bootystar/starter/prop/BootystarProperties.java)

## JSON注解加密/解密/脱敏
* 注解:[JsonMask.java](src/main/java/io/github/bootystar/starter/jackson/annotation/JsonMask.java)
* 该功能基于`jackson`实现, 若自定义配置了其他序列化框架, 该功能无效
* 支持非`String`类型的属性
* 可单独指定序列化/反序列化的处理逻辑
* 在属性上添加`@JsonMask`注解
* 使用`serialize`指定序列化时的处理类
* 使用`deserialize`指定反序列化时的处理类
* 可以单独指定`serialize`或`deserialize`其中一项, 仅指定项生效
* 若`serialize`或`deserialize`均未指定, 该功能不会生效
* `@JsonMask`优先级高于`@JsonFormat`, 若同时存在, `@JsonMask`优先生效, 当`@JsonMask`无效时, `@JsonFormat`生效
```java
@JsonMask(serialize = DateOut.class) // 序列化时使用DateOut类处理
private LocalDate date;

@JsonMask(deserialize = DateIn.class) // 反序列化时使用DateIn类处理
private LocalDate date;

@JsonMask(serialize = DateOut.class, deserialize = DateIn.class) // 序列化和反序列化时使用DateOut和DateIn类处理
private LocalDate date3;
```

## 方法限流
* 注解: [MethodLimit.java](src/main/java/io/github/bootystar/starter/spring/annotation/MethodLimit.java)
* 基于`spring`和`aop`, 被限流方法所在类需要为`spring`管理的`bean`, 并引入了`aop`相关依赖
* 支持根据方法参数限流
* 支持配置消息提示, 默认达到限流时抛出[MethodLimitException.java](src/main/java/io/github/bootystar/starter/exception/MethodLimitException.java)异常
* 支持自定义限流逻辑
* 通过`value`指定要限流的参数, 格式为`spEL表达式`
* 通过`message`指定触发限流时抛出的异常提示信息
* 通过`handler`指定自定义的限流逻辑
```java
@MethodLimit // 根据判断所有参数toString()后的值是否相同限流
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}

@MethodLimit("#dto.id") // 根据dto的id属性限流
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}

@MethodLimit(value = "#userId", message = "请求过快, 请稍后再试", handler = CustomLimitHandler.class) // 使用userId限流, 并自定义错误信息, 限流逻辑
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}
```