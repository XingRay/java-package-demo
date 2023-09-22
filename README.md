# java-package-demo
java-package-demo

演示各种常见场景下的 java 项目打包成可执行文件流程, 包括
cli程序
javafx ui程序
springboot 服务端程序

打包方式包括:
jar:
可执行 jar 包, 通过 java -jar xxx.jar 运行

jpackage:
可独立执行的 exe (windows平台) 的可执行文件
可独立执行的 (windows平台) 的安装包, 包括 msi 和 exe 版本

native-image:
可独立执行的 native-image

主要测试环境为windows, Linux和Mac平台以及 docker 待补充
