## 流程 
1 使用 maven-assembly-plugin 插件将项目打包成一个普通的jar包和一个可运行的jar包
2 打包后直接使用 java -jar xxx-jar-with-dependencies.jar 即可启动, 所有的依赖都已经集成到jar中了

可运行的jar包比普通的jar包多了 -with-dependencies.jar 后缀, 注意区分

# 1 maven 打包
```shell
mvn clean package
```

# 2 执行
```shell
java -jar target/java-package-03-cli-to-jar-with-dependency-1.0.0-jar-with-dependencies.jar
```
