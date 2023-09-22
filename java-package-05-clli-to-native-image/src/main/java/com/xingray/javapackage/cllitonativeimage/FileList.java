package com.xingray.javapackage.cllitonativeimage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileList {
    private final File dir;

    final static Logger logger = LoggerFactory.getLogger(FileList.class);

    public FileList(File dir) {
        this.dir = dir;
    }

    public String list() {
        if (dir.isFile()) {
            logger.debug("dir is file");
            return dir.getName();
        } else {
            logger.debug("dir is {}", dir.getAbsolutePath());
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                logger.debug("dir is empty");
                return "";
            }
            logger.debug("dir has files count: {}", files.length);
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
