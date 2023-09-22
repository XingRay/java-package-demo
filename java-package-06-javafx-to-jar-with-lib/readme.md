## 流程 
1 使用 maven-dependency-plugin 插件将项目依赖的所有包复制到指定的目录, 如 target/lib 
2 使用 maven-jar-plugin 插件指定主类, 添加classpath 为指定路径, 如 lib  
3 打包后直接使用 java -jar xxx.jar 即可启动, 运行时会自动将 lib 目录添加到 classpath


# 1 maven 打包
```shell
mvn clean package
```

# 2 执行
```shell
java -jar target/java-package-06-javafx-to-jar-with-lib-1.0.0.jar
```
