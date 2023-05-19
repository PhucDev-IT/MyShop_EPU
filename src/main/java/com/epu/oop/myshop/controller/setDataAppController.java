package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.model.ConverForm;
import com.epu.oop.myshop.model.CreateSQL;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class setDataAppController implements Initializable {

    @FXML
    private ImageView imgLoading;


    private void setData() throws SQLException, ClassNotFoundException {
        CreateSQL cr = new CreateSQL();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);
                cr.autoCreate();
                Thread.sleep(500);
                Platform.runLater(() -> {
                    try {
                        ConverForm.showForm((Stage) imgLoading.getScene().getWindow(), "/com/epu/oop/myshop/GUI/PageHome.fxml", "Trang chủ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        task.setOnSucceeded(event -> {
            thread.interrupt();
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoading.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/ezgif.com-resize.gif"))));
        imgLoading.setFitWidth(1000);
        imgLoading.setPreserveRatio(true);

        try {
            setData();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
