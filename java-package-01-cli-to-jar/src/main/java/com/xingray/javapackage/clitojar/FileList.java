package com.xingray.javapackage.clitojar;

import java.io.File;

public class FileList {
    private final File dir;

    public FileList(File dir) {
        this.dir = dir;
    }

    public String list() {
        if (dir.isFile()) {
            return dir.getName();
        } else {
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return "";
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0, size = files.length; i < size; i++) {
                File file = files[i];
                if (i > 0) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(file.getName());
            }
            return stringBuilder.toString();
        }
    }
}
