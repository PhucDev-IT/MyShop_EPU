package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class ProductsOfMarketController {

    @FXML
    private ImageView imgProducts;

    @FXML
    private Label nameProduct_txt;

    @FXML
    private Label price;

    @FXML
    private Label maSP_lb;

    @FXML
    private Label sold;

    private Product product;

    //Interface mylistener để bắt sự kiện khi nhấn vào các sản phẩm
    private MyListener<Product> mylistener;

    @FXML
    private void click() {
        mylistener.onClickListener(product);
    }

    public void setData(MyListener<Product> myListener, Product prod) {
        this.product = prod;
        this.mylistener = myListener;

        try {
            if (!prod.getSrcImg().contains(":")) {
                imgProducts.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(prod.getSrcImg()))));
            } else
                imgProducts.setImage(new Image(product.getSrcImg()));
        } catch (Exception e) {
            imgProducts.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
        }
        nameProduct_txt.setText((prod.getTenSP()));
        sold.setText(String.valueOf(prod.getSold()));
        maSP_lb.setText("0" + prod.getID());
        price.setText(App.numf.format(prod.getPrice()));

    }


}
