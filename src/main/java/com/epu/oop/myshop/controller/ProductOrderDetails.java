package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class ProductOrderDetails {

    @FXML
    private ImageView imgProduct;

    @FXML
    private Label maSP;

    @FXML
    private Label nameProduct;

    @FXML
    private Label price;

    @FXML
    private Label quantity;


    public void setData(Product pro, int number) {

        nameProduct.setText(pro.getTenSP());
        maSP.setText("0" + pro.getID());
        price.setText(App.numf.format(pro.getPrice()) + "đ");
        quantity.setText(String.valueOf(number));

        try {
            if (pro.getSrcImg().contains(":")) {
                imgProduct.setImage(new Image(pro.getSrcImg()));
            } else {
                imgProduct.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(pro.getSrcImg()))));
            }
        } catch (Exception e) {
            imgProduct.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
            System.out.println("Không load được ảnh: " + e.getMessage());
        }
    }
}
