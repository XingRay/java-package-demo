## 流程 
1 使用 maven-dependency-plugin 插件将项目额所有依赖输出到 target/lib 目录
2 使用 jdeps 工具分析项目的所有依赖, 结果作为 japckage 打包时的 --add-modules 参数
3 使用 jpackage 工具将项目打包成平台的可执行文件或者安装包

# 1 maven 打包
```shell
mvn clean package
```

# 2 检查依赖
```shell
jdeps --multi-release 21 --print-module-deps --ignore-missing-deps --add-modules ALL-MODULE-PATH --class-path target/lib --module-path target/lib;target/classes
```
输出: java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported , 依赖项要作为 jpackage 命令 --add-modules 选项参数 


## 3 在 windows 上生成可执行文件
cli 程序注意加上 --win-console 选项, 否则无法在控制台输出, javafx等ui程序不需要添加

```shell
jpackage --type app-image --name javafx-app --dest target/exe --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app" --vendor "xingray" --resource-dir src/main/resources  
```

保留debug信息, 可以让log中正确输出行号,但是程序包体积会增大
```shell
jpackage --type app-image --name javafx-app --dest target/exe --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app, just for demo" --vendor "xingray" --resource-dir src/main/resources --jlink-options "--strip-native-commands --no-man-pages --no-header-files"
```
生成的可执行文件在 /target/exe/list-dir-app/list-dir-app.exe
注意: 使用默认的配置,生成的 .exe 文件中没有 debug 信息, 会导致日志中没有文件名和行号, (%F:%L) 会输出 (null:-1)
https://stackoverflow.com/questions/29750470/why-doesnt-slf4j-logback-log-filename-and-row-number
japckage 会调用 jlink, 默认的 jlink 参数为 "--strip-native-commands --strip-debug --no-man-pages --no-header-files" , 
其中 "--strip-debug" 为删除 debug 信息, 这样可以减少应用的体积, 但是如果要保留 debug 信息以显示文件名和行号, 可以给 jpackage 添加参数
--jlink-options "--strip-native-commands --no-man-pages --no-header-files" 修改 jlink 的参数


## 4 在 windows 平台生成安装包

### msi 格式
```shell
jpackage --type msi --name javafx-app --dest target/installer-msi --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app, just for demo" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group "xingray" --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com"
```
生成的安装包在 target/installer-msi/javafx-app-1.0.0.msi

保留debug信息:
```shell
jpackage --type msi --name javafx-app --dest target/installer-msi --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app, just for demo" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group "xingray" --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com" --jlink-options "--strip-native-commands --no-man-pages --no-header-files"
```

### exe 格式
```shell
jpackage --type exe --name javafx-app --dest target/installer-exe --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app, just for demo" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group "xingray" --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com"
```
生成的安装包在 target/installer-exe/javafx-app-1.0.0.exe

保留debug信息
```shell
jpackage --type exe --name javafx-app --dest target/installer-exe --module com.xingray.javapackage.javafxtoexebyjpackage/com.xingray.javapackage.javafxtoexebyjpackage.Launcher --add-modules java.base,java.desktop,java.logging,java.naming,java.scripting,jdk.jfr,jdk.unsupported --module-path target/lib --module-path target/classes --verbose --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "javafx app, just for demo" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group "xingray" --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com" --jlink-options "--strip-native-commands --no-man-pages --no-header-files"
```
