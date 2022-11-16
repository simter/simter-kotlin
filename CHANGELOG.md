# simter-kotlin changelog

## 3.2.0 - 2022-11-16

- Upgrade to simter-dependencies-3.4.0
- Add json extension for convert native value to JsonElement
    - `fromNativeMap(...)` 
    - `fromNativeCollection(...)` 
    - `fromNativeValue(...)` 

## 3.1.0 - 2022-09-14

- Upgrade to simter-dependencies-3.2.0
- Add Enum extension for find it by parameter value
- Add json extension for convert JsonElement to native value
- Add property config 'simter.kotlinx-serialization.encode-defaults'
    > default value set to false.

## 3.0.0 - 2022-06-21

- Upgrade to simter-dependencies-3.0.0 (jdk-17)

## 3.0.0-M4 - 2021-08-26

- Upgrade to simter-3.0.0-M4
- Add CommonLocalDateTimeToMinuteSerializer
    > yyyy-MM-dd HH:mm

## 3.0.0-M3 - 2021-08-18

- Upgrade to simter-3.0.0-M3 (kotlin-1.5.21)
- Add convenient `Id` data class
- Set `SIMTER_CLASS_DISCRIMINATOR` to `"#class"`
- Set default `javaTimeSerializersModule` to `IsoJavaTimeSerialModule`
- Add convinience emthod `Json.encodeToString(value: T, vararg excludeProperties: String): String`

## 3.0.0-M2 - 2021-04-28

- Upgrade to simter-3.0.0-M2 (kotlin-1.4.32)
- Make Page interface serializable
- Set default classDiscriminator to '#class'
- Change `Page.offset` to `Long` type
- Enhance `Page.toMap` method
- Remove JsonUtils class
    > Prefer to use `tech.simter.kotlin.serialization.KotlinJsonAutoConfiguration#kotlinJson`

## 3.0.0-M1 - 2021-01-18

- Upgrade to simter-3.0.0-M1
- Upgrade to kotlinx-serialization-1.0.1
    - Add a [KotlinJsonAutoConfiguration] for spring-boot auto configuration
- Upgrade javatime serializer code
- Add nullable BigDecimal serializer
    - deserializer empty string to null value
    - smart serialize to integer or decimal number
- Add strict type BigDecimal serializer (for String or Number)
    - `BigDecimalAsStringSerializer`
    - `BigDecimalAsNumberSerializer`

[KotlinJsonAutoConfiguration]: https://github.com/simter/simter-kotlin/blob/master/src/main/kotlin/tech/simter/kotlin/serialization/KotlinJsonAutoConfiguration.kt

## 2.0.0 - 2020-11-19

- Upgrade to simter-2.0.0

## 2.0.0-M2 - 2020-07-27

- Upgrade to simter-2.0.0-M2
- Add IntYearMonthIntSerializer
- Rename `Year|MonthSerializer` to `IntYear|MonthSerializer`
- Fixed IsoYearMonth pattern

## 2.0.0-M1 - 2020-06-02

- Upgrade to simter-2.0.0-M1

## 1.4.0-M5 - 2020-04-16

- Support a simple [Page] interface for common usage
- Encapsulate a share kotlin json instance for common usage
- Update custom serializer code after upgrade to `kotlinx-serialization-runtime-0.20.0` (with kotlin-1.3.70+)
- Upgrade to simter-dependencies-1.3.0-M14

[Page]: https://github.com/simter/simter-kotlin/blob/master/src/main/kotlin/tech/simter/kotlin/data/Page.kt

## 1.4.0-M4 - 2020-03-01

- Upgrade to simter-1.3.0-M13

## 1.4.0-M3 - 2020-01-08

- Upgrade to simter-build-1.3.0-M11
- Support nullable javatime

## 1.4.0-M2 - 2019-12-05

- Upgrade to simter-1.3.0-M9

## 1.4.0-M1 - 2019-12-03

- Upgrade to simter-1.3.0-M8
- Add KSerializer implementation for iso-year-month-day-time (such as 2019-12-01T10:20:30)
- Add KSerializer implementation for common-year-month-day-time (such as 2019-12-01 10:20:30)
- Add kotlinx-serialization dependency
- Remove deprecated classes: tech.simter.kotlin.properties.*

## 1.3.1 - 2019-07-27

- Simplify kotlin config

## 1.3.0 - 2019-07-03

No code changed, just polishing maven config and unit test.

- Change parent to simter-dependencies-1.2.0
- Simplify JUnit5 config
- Add Deprecated to some old classes

## 1.2.0 - 2019-02-21

- Add authorization role properties config bean `AuthorizeModuleOperations`, `AuthorizeOperations` and `AuthorizeRole`

## 1.1.0 - 2019-01-16

- Add `Comment` annotation

## 1.0.0 - 2019-01-15

- Add `DynamicBean` class