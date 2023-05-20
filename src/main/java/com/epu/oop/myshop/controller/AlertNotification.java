package com.epu.oop.myshop.controller;


import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Optional;

public class AlertNotification {

    public static boolean showAlertConfirmation(String header, String content) {
        Alert alertConfimation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfimation.setTitle("MyShop");
        alertConfimation.setHeaderText(header);
        alertConfimation.setContentText(content);

        ButtonType btnYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        //Trả về 1 obsevableList của all buttonType hiện được đặt trong cảnh báo
        alertConfimation.getButtonTypes().setAll(btnYes, btnNo);

        //Xem yêu cầu người dùng
        Optional<ButtonType> result = alertConfimation.showAndWait();

        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            return true;
        } else {
            return false;
        }
    }

    public static void showAlertSucces(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MyShop");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setWidth(60);
        alert.setHeight(15);
        Image image = new Image(Objects.requireNonNull(AlertNotification.class.getResourceAsStream("/com/epu/oop/myshop/image/icon_check.png")));
        ImageView icon = new ImageView(image);
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        alert.showAndWait();
    }

    public static void showAlertWarning(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("MyShop");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setWidth(60);
        alert.setHeight(15);

        alert.showAndWait();
    }

    public static void showAlertError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("MyShop");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setWidth(60);
        alert.setHeight(15);

        alert.showAndWait();
    }

    //----------------------------------------- Dialog------------------------------------------------

    public static String inputPassword(String title) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Nhập mật khẩu");


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Vui lòng nhập mật khẩu của bạn");
        alert.getDialogPane().setContent(passwordField);

        Image image = new Image(Objects.requireNonNull(AlertNotification.class.getResourceAsStream("/com/epu/oop/myshop/image/icon-lock.png")));
        ImageView icon = new ImageView(image);
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);

        // Hiển thị hộp thoại và chờ người dùng nhập mật khẩu
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra xem người dùng đã nhập mật khẩu hay chưa
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String password = passwordField.getText();
            return password;
        }
        return null;
    }
}
