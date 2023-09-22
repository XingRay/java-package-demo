package com.xingray.javapackage.javafxtojarwithlib;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Launcher#main: " + String.join(" ", args));
        Application.launch(MainApplication.class, args);
    }
}
