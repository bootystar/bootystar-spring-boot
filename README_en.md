# bootystar-spring-boot-starter

English | [中文](README.md)

A Spring Boot starter that provides out-of-the-box configuration for common development scenarios including type conversion, JSON serialization/deserialization, method limiting, Excel processing, and more.

## Features

* Annotation-based method limiting with customizable handlers
* Spring parameter conversion for dates, times, and high-precision numbers
* JSON serialization/deserialization with field-level encryption/masking support
* MyBatis-Plus plugins for pagination, full-table update protection, and SQL injection prevention
* Redis serialization/deserialization configuration
* Extra converters for EasyExcel and FastExcel (dates, times, high-precision numbers)

## Quick Start

### Add Maven Dependency

```xml
<dependency>
    <groupId>io.github.bootystar</groupId>
    <artifactId>bootystar-spring-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Enable Features

After adding the dependency, all features will be automatically configured and enabled. You can customize the behavior by configuring properties in `application.yml`:

```yaml
bootystar:
  # Date/time format configuration
  date-format: yyyy-MM-dd
  date-time-format: yyyy-MM-dd HH:mm:ss
  time-format: HH:mm:ss
  time-zone-id: GMT+8
  
  # Parameter converter configuration
  converter:
    enabled: true
    string-to-date: true
    string-to-local-date: true
    string-to-local-date-time: true
    string-to-local-time: true
  
  # Jackson serialization configuration
  jackson:
    enabled: true
    long-to-string: true
    big-decimal-to-string: true
    big-integer-to-string: true
  
  # Excel converter configuration
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

## Core Features

### 1. Method Limiting

Implement method-level flow control through the [@MethodLimit](src/main/java/io/github/bootystar/starter/spring/annotation/MethodLimit.java) annotation.

```java
@MethodLimit // Limit based on all parameters' toString() values
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}

@MethodLimit("#dto.id") // Limit based on dto.id property
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}

@MethodLimit(value = "#userId", message = "Too many requests, please try again later", handler = CustomLimitHandler.class)
public Boolean update(UpdateDTO dto, Long userId) {
    return baseService.update(dto);
}
```

### 2. JSON Field Encryption/Masking

Use the [@JsonMask](src/main/java/io/github/bootystar/starter/jackson/annotation/JsonMask.java) annotation to implement serialization and deserialization processing of JSON fields.

```java
@JsonMask(serialize = DateOut.class) // Use DateOut class for serialization
private LocalDate date;

@JsonMask(deserialize = DateIn.class) // Use DateIn class for deserialization
private LocalDate date;

@JsonMask(serialize = DateOut.class, deserialize = DateIn.class) // Use different handlers for serialization and deserialization
private LocalDate date3;
```

### 3. Parameter Converters

Automatically register multiple string to date/time type converters:
- String → Date
- String → LocalDate
- String → LocalDateTime
- String → LocalTime
- String → java.sql.Date
- String → java.sql.Time
- String → java.sql.Timestamp

### 4. Excel Converters

Provide additional converter support for EasyExcel and FastExcel:
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

## Configuration Properties

For complete configuration properties, please refer to [BootystarProperties.java](src/main/java/io/github/bootystar/starter/prop/BootystarProperties.java) and its related sub-property classes.

## License

This project is licensed under the [Apache License 2.0](LICENSE).