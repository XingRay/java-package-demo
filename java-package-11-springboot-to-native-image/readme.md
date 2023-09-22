使用 native-maven-plugin 插件将springboot项目编译为 native-image
插件会自动为每一个依赖生成 metadata ,文件位于: target/graalvm-reachability-metadata/xxxxxxxxx/{groupId}/{artifactId}/{version}

插件会自动生成 native-image 的 args 文件, 位于 target/tmp/native-image-xxxxxxxx.args
插件会在 maven 打包时执行 native-image @target/tmp/native-image-xxxxxxxx.args 命令执行 native-image 的生成

## 1 编译
```shell
mvn -P native clean compile spring-boot:process-aot native:compile-no-fork
```

## 2 运行
```shell
target/java-package-11-springboot-to-native-image.exe
```

