package com.xingray.javapackage.cllitonativeimage;

import ch.qos.logback.classic.spi.LogbackServiceProvider;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        String path = ".";
        if (args != null && args.length > 0) {
            path = args[0];
        }
        // 修复 slf4j + logback 在 native-image 中无法输出日志的问题
        System.setProperty("slf4j.provider", LogbackServiceProvider.class.getCanonicalName());
        String list = new FileList(new File(path)).list();
        System.out.println(list);
    }
}
