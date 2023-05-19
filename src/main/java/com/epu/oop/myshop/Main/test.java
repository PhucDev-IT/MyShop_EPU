package com.epu.oop.myshop.Main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
public class test extends Application {
    public void start(Stage stage) {
        String text = "Tutorials Point originated from the idea that there exists a class of readers who respond better to online content and prefer to learn new skills at\n" +
                "        their own pace from the comforts of their drawing rooms.";
        //Creating a Label
        Label label = new Label(text);
        //wrapping the label
        label.setWrapText(true);
        //Setting the alignment to the label
        label.setTextAlignment(TextAlignment.JUSTIFY);
        //Setting the maximum width of the label
        label.setMaxWidth(200);
        label.setMaxHeight(70);
        //Setting the position of the label
        label.setTranslateX(25);
        label.setTranslateY(25);
        Pane pane = new Pane();
        pane.getChildren().add(label);
        //Setting the stage
        Scene scene = new Scene( pane,595, 150, Color.BEIGE);
        stage.setTitle("Label Example");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
