package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ItemSearchProduct {

    @FXML
    private ImageView img_Product;

    @FXML
    private Text nameProduct;

    private Product products;

    public void setData(Product product)
    {
        this.products = product;

        img_Product.setImage(new Image(product.getSrcImg()));
        nameProduct.setText(product.getTenSP());
    }

}
