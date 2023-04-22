package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.ConverForm;
import com.epu.oop.myshop.model.CreateSQL;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class setDataAppController implements Initializable {

    @FXML
    private ImageView imgLoading;



    private void setData() {
        CreateSQL cr = new CreateSQL();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
               try{
                   if(cr.createDatabase()) {
                       cr.restoreData();
                   }
               }catch (SQLException e){
                   e.printStackTrace();
               }
                System.out.println("hello");
                Thread.sleep(2000);
               Platform.runLater(()->{
                   try {
                       ConverForm.showForm((Stage) imgLoading.getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
               return null;
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoading.setImage(new Image("C:\\Users\\84374\\Downloads\\ezgif.com-resize.gif"));
        imgLoading.setFitWidth(1000);
        imgLoading.setPreserveRatio(true);

        CreateSQL cr = new CreateSQL();
        try{
            if(cr.createDatabase()) {
                cr.restoreData();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(()->{
            try {
                ConverForm.showForm((Stage) imgLoading.getScene().getWindow(),"/com/epu/oop/myshop/GUI/PageHome.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
