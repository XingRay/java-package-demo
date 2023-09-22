package com.xingray.javapackage.javafxtoexebyjpackage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication extends Application {

    private int num = 0;

    final static Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        Label text = new Label();
        AnchorPane.setLeftAnchor(text, 100.0);
        AnchorPane.setTopAnchor(text, 100.0);

        Button button = new Button("click me");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                num++;
                text.setText("click " + num);
                logger.info("click " + num);
            }
        });
        root.getChildren().addAll(button, text);
        Scene scene = new Scene(root, 240.0, 220.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
