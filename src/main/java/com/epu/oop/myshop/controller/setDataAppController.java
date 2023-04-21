package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.model.ConverForm;
import com.epu.oop.myshop.model.CreateSQL;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class setDataAppController implements Initializable {

    @FXML
    private ImageView imgLoading;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoading.setImage(new Image("C:\\Users\\84374\\Downloads\\ezgif.com-resize.gif"));
        imgLoading.setFitWidth(1000);
        imgLoading.setPreserveRatio(true);




    }
}
