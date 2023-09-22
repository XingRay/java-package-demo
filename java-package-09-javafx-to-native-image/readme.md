# 流程
1 使用 maven-dependency-plugin 插件将项目依赖的所有包复制到指定的目录, 如 target/lib
2 列举 target/lib 中的所有文件, 作为 native-image 工具的 add-modules 参数
3 通过 native-image 工具生成原生镜像

## 1 打包
maven 打包
```shell
mvn clean package
```

## 2 生成 Reachability Metadata
通过 agent 生成 Reachability Metadata 到 src/main/resources/META-INF/native-image
```shell
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image --class-path target/java-package-09-javafx-to-native-image-1.0.0-jar-with-dependencies.jar com.xingray.javapackage.javafxtonativeimage.Launcher
```

## 3 再打包
再打包, 将生成的 metadata 也打入 target/classes 目录中
```shell
mvn clean package
```

## 4 通过 native-image 创建原生可执行文件
```shell
native-image --class-path target/classes --add-modules org.slf4j,ch.qos.logback.classic,javafx.base,javafx.graphics,javafx.controls,javafx.fxml --module-path target/classes;target/lib/logback-classic-1.4.11.jar;target/lib/logback-core-1.4.11.jar;target/lib/slf4j-api-2.0.9.jar;target/lib/javafx-base-20.0.2.jar;target/lib/javafx-base-20.0.2-win.jar;target/lib/javafx-controls-20.0.2.jar;target/lib/javafx-controls-20.0.2-win.jar;target/lib/javafx-fxml-20.0.2.jar;target/lib/javafx-fxml-20.0.2-win.jar;target/lib/javafx-graphics-20.0.2.jar;target/lib/javafx-graphics-20.0.2-win.jar -H:Class=com.xingray.javapackage.javafxtonativeimage.Launcher -H:Path=target -H:Name=javafx-app --no-fallback -H:+ReportExceptionStackTraces -H:+TraceNativeToolUsage
```

### 5 修改为无窗口模式
修改镜像为widows程序, 以无窗口模式运行, 注意将日志设置为文件
```bash
EDITBIN /SUBSYSTEM:WINDOWS target/javafx-app.exe
```


日志无法输出问题解决办法:
```java
public class Launcher {
    public static void main(String[] args) {
        // ...
        // 解决 slf4j 无法输出日志的问题
        System.setProperty("slf4j.provider", LogbackServiceProvider.class.getCanonicalName());
        // ...
    }
}
```


目前部分框架不支持, 如 slf4j , 以 jvm 启动可以正常运行, 但是以 native-image 启动时不能正常使用日志功能, 运行时输出如下:
[]
SLF4J: No SLF4J providers were found.
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See https://www.slf4j.org/codes.html#noProviders for further details.
app.exe

原因:
org.slf4j.LoggerFactory#getLogger(java.lang.Class<?>)
org.slf4j.LoggerFactory#getLogger(java.lang.String)
org.slf4j.LoggerFactory#getILoggerFactory
org.slf4j.LoggerFactory#getProvider
org.slf4j.LoggerFactory#performInitialization
org.slf4j.LoggerFactory#bind
org.slf4j.LoggerFactory#findServiceProviders
org.slf4j.LoggerFactory#getServiceLoader
java.util.ServiceLoader#load(java.lang.Class<S>, java.lang.ClassLoader)

findServiceProviders 方法返回为空, 是由于 ServiceLoader#load 在 native-image 中无法正常加载, 但是在 org.slf4j.LoggerFactory#findServiceProviders 方法中提供了显式加载 SLF4JServiceProvider 的方法
org.slf4j.LoggerFactory#loadExplicitlySpecified

读取系统属性指定的类为 SLF4JServiceProvider
```java
String explicitlySpecified = System.getProperty(PROVIDER_PROPERTY_KEY);
Class<?> clazz = classLoader.loadClass(explicitlySpecified);
```

所以可以在程序的入口通过:
```java
System.setProperty("slf4j.provider", LogbackServiceProvider.class.getCanonicalName());
```

显示设置 SLF4JServiceProvider 为 LogbackServiceProvider 即可正常使用 slf4j + logback

添加了显示设置 SLF4JServiceProvider 代码之后,重新运行 agent , 可以看到在 reflect-config 中生成的反射配置:
```json
{
  "name":"ch.qos.logback.classic.spi.LogbackServiceProvider",
  "methods":[{"name":"<init>","parameterTypes":[] }]
}
```
