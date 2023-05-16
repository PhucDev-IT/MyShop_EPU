package com.epu.oop.myshop.model;

import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ConverForm{

    public static void showForm(Stage primaryStage, String fxmlPath,String title) throws IOException {
        Parent root = FXMLLoader.load(ConverForm.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
       // primaryStage.getIcons().add(new Image(ConverForm.class.getResourceAsStream("/com/epu/oop/myshop/image/logo-app.png")));
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
