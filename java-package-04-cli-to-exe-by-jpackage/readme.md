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
输出: java.base,java.logging,java.naming,java.xml , 依赖项要作为 jpackage 命令 --add-modules 选项参数 


## 3 在 windows 上生成可执行文件
cli 程序注意加上 --win-console 选项, 否则无法在控制台输出, javafx等ui程序不需要添加

```shell
jpackage --type app-image --name list-dir-app --dest target/exe --module com.xingray.javapackage.clitoexebyjpackage/com.xingray.javapackage.clitoexebyjpackage.Main --add-modules java.base,java.logging,java.naming,java.xml --module-path target/lib --module-path target/classes --verbose --win-console --icon src/main/resources/images/icon.ico --app-version "1.0.0" --copyright "xingray.com" --description "list dir app" --vendor "xingray" --resource-dir src/main/resources  
```
生成的可执行文件在 /target/exe/list-dir-app/list-dir-app.exe

## 4 在 windows 平台生成安装包

### msi 格式
```shell
jpackage --type msi --name list-dir-app --dest target/msi --module com.xingray.javapackage.clitoexebyjpackage/com.xingray.javapackage.clitoexebyjpackage.Main --add-modules java.base,java.logging,java.naming,java.xml --module-path target/lib --module-path target/classes --verbose --win-console --icon src/main/resources/images/icon.ico --app-version "1.0.1" --copyright "xingray.com" --description "list dir app" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group xingray --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com"
```
生成的安装包在 target/msi/list-dir-app-1.0.1.msi


### exe 格式
```shell
jpackage --type exe --name list-dir-app --dest target/ms-installer --module com.xingray.javapackage.clitoexebyjpackage/com.xingray.javapackage.clitoexebyjpackage.Main --add-modules java.base,java.logging,java.naming,java.xml --module-path target/lib --module-path target/classes --verbose --win-console --icon src/main/resources/images/icon.ico --app-version "1.0.1" --copyright "xingray.com" --description "list dir app" --vendor "xingray" --resource-dir src/main/resources --win-dir-chooser --win-help-url "https://www.baidu.com" --win-menu --win-menu-group xingray --win-per-user-install --win-shortcut --win-shortcut-prompt --win-update-url "https://www.baidu.com" --win-upgrade-uuid "edba04c6-7ae0-669a-c09f-b63afd9264a1" --about-url "https://www.baidu.com"
```
生成的安装包在 target/ms-installer/list-dir-app-1.0.1.exe
