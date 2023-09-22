package com.xingray.javapackage.javafxtonativeimage;

import ch.qos.logback.classic.spi.LogbackServiceProvider;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Launcher#main: " + String.join(" ", args));
        // 解决 slf4j + logback 在native-image 中无法输出日志的问题
        System.setProperty("slf4j.provider", LogbackServiceProvider.class.getCanonicalName());
        Application.launch(MainApplication.class, args);
    }
}
