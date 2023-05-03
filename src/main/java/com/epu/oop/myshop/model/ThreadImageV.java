package com.epu.oop.myshop.model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ThreadImageV {
    private int currentIndex = 0;
    private ImageView imageView;
    private Timeline timeline = new Timeline();

    public ThreadImageV(ImageView img){
        //Image[] =
        imageView = img;
    }
    private Image[] images = {
            new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/voucher.png")),
            new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/voucher-1.jpg")),
            //new Image("C:\\Users\\84374\\OneDrive\\Pictures\\iconFreeShip.jpg"),

    };

    public void start() {
        // Set up the ImageView
        imageView.setImage(images[0]);

        // Set up the Slider
        Slider slider = new Slider(0, images.length - 1, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentIndex = (int) newVal.doubleValue();
            updateImage();
        });

        // Set up the Timeline for the Slideshow
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), event -> {
            currentIndex = (currentIndex + 1) % images.length;
            updateImage();
            slider.setValue(currentIndex);
        }));
        timeline.play();

    }
    public void stop(){
        timeline.stop();
    }

    private void updateImage() {
        // Apply a fade transition to the ImageView when updating the image
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), imageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            imageView.setImage(images[currentIndex]);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), imageView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }
}
