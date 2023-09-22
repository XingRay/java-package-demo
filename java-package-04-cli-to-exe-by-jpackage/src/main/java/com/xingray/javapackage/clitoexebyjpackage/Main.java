package com.xingray.javapackage.clitoexebyjpackage;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        String path = ".";
        if (args != null && args.length > 0) {
            path = args[0];
        }
        String list = new FileList(new File(path)).list();
        System.out.println(list);
    }
}
