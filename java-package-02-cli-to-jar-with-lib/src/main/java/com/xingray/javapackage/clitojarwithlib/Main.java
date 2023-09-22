package com.xingray.javapackage.clitojarwithlib;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String path = ".";
        if (args != null && args.length > 0) {
            path = args[0];
        }
        System.out.println(new FileList(new File(path)).list());
    }
}
