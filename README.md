# bootystar-spring-boot-starter

[English](README_en.md) | 中文

一个Spring Boot starter，为常见的开发场景提供开箱即用的配置，包括类型转换、JSON序列化/反序列化、方法限流、Excel处理等。

## 功能特性

* 基于注解的方法限流，支持自定义处理器
* Spring参数转换器，支持日期、时间及高精度数字
* JSON序列化/反序列化，支持字段级加密/脱敏
* MyBatis-Plus插件，包括分页、防全表更新、防SQL注入
* Redis序列化/反序列化配置
* 为EasyExcel和FastExcel添加额外的转换器（日期、时间、高精度数字）

## 快速开始

### 添加Maven依赖

```xml
<dependency>
    <groupId>io.github.bootystar</groupId>
    <artifactId>bootystar-spring-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```

### 启用功能

添加依赖后，所有功能将自动配置并启用。您可以通过在`application.yml`中配置属性来定制行为：

```yaml
bootystar:
  # 日期时间格式配置
  date-format: yyyy-MM-dd
  date-time-format: yyyy-MM-dd HH:mm:ss
  time-format: HH:mm:ss
  time-zone-id: GMT+8
  
  # 参数转换器配置
  converter:
    enabled: true
    string-to-date: true
    string-to-local-date: true
    string-to-local-date-time: true
    string-to-local-time: true
  
  # Jackson序列化配置
  jackson:
    enabled: true
    long-to-string: true
    big-decimal-to-string: true
    big-integer-to-string: true
  
  # Excel转换器配置
  excel:
    init-fast-excel-converter: true
    init-easy-excel-converter: false
    converter:
      big-decimal-to-string: true
      big-integer-to-string: true
      long-to-string: true
      boolean-to-string: true
      float-to-string: true
      double-to-string: true
      sql-timestamp-to-string: true
      sql-date-to-string: true
      sql-time-to-string: true
      local-date-time-to-string: true
      local-date-to-string: true
      local-time-to-string: true
      date-to-string: true
```

## 核心功能详解

### 1. 方法限流

通过 [@MethodLimit](src/main/java/io/github/bootystar/starter/spring/annotation/MethodLimit.java) 注解实现方法级别的限流控制。

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

### 2. JSON字段加密/脱敏

使用 [@JsonMask](src/main/java/io/github/bootystar/starter/jackson/annotation/JsonMask.java) 注解实现JSON字段的序列化和反序列化处理。

```java
@JsonMask(serialize = DateOut.class) // 序列化时使用DateOut类处理
private LocalDate date;

@JsonMask(deserialize = DateIn.class) // 反序列化时使用DateIn类处理
private LocalDate date;

@JsonMask(serialize = DateOut.class, deserialize = DateIn.class) // 序列化和反序列化时使用DateOut和DateIn类处理
private LocalDate date3;
```

### 3. 参数转换器

自动注册多种字符串到日期/时间类型的转换器：
- String → Date
- String → LocalDate
- String → LocalDateTime
- String → LocalTime
- String → java.sql.Date
- String → java.sql.Time
- String → java.sql.Timestamp

### 4. Excel转换器

为EasyExcel和FastExcel提供额外的转换器支持：
- BigDecimal ↔ String
- BigInteger ↔ String
- Long ↔ String
- Boolean ↔ String
- Float ↔ String
- Double ↔ String
- java.sql.Timestamp ↔ String
- java.sql.Date ↔ String
- java.sql.Time ↔ String
- LocalDateTime ↔ String
- LocalDate ↔ String
- LocalTime ↔ String
- Date ↔ String

## 配置属性

完整的配置属性请参考 [BootystarProperties.java](src/main/java/io/github/bootystar/starter/prop/BootystarProperties.java) 及其相关子属性类。

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。