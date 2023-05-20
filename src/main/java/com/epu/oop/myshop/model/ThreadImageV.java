package com.epu.oop.myshop.model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class ThreadImageV {
    private int currentIndex = 0;
    private ImageView imageView;
    private Timeline timeline = new Timeline();

    public ThreadImageV(ImageView img){
        //Image[] =
        imageView = img;
    }
    private Image[] images = {
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/voucher-3.png"))),
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/voucher-2.png"))),
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/voucher-1.jpg"))),
    };

    public void start() {

        imageView.setImage(images[0]);

        // set silder
        Slider slider = new Slider(0, images.length - 1, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentIndex = (int) newVal.doubleValue();
            updateImage();
        });

        // Set thời gian
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
