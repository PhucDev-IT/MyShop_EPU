package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.Objects;

public class ItemProductsInBanHang {

    @FXML
    private ImageView image;

    @FXML
    private Text tenHang_text;

    @FXML
    private Label SoLuong_label;

    @FXML
    private Label GiaBan_label;

    @FXML
    private Label DaBan_Label;

    @FXML
    private Label DoanhThu_Label;

    private Product prod;

    private MyListener<Product> myListener;

    //Add sự kiện cho form mỗi khi nhấn
    public void click(MouseEvent e) {
        myListener.onClickListener(prod);
    }


    public void setData(MyListener<Product> mylistener, Product p) {

        this.prod = p;
        this.myListener = mylistener;

        try {
            if (!p.getSrcImg().contains(":")) {
                image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(p.getSrcImg()))));
            } else
                image.setImage(new Image(p.getSrcImg()));
        } catch (Exception e) {
            image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png"))));
            System.out.println("Lỗi: " + e.getMessage());
        }
        tenHang_text.setText(p.getTenSP());
        SoLuong_label.setText(String.valueOf(p.getQuantity()));

        DaBan_Label.setText(String.valueOf(p.getSold()));
        DoanhThu_Label.setText(App.numf.format(p.getTotalRevenue()) + "đ");
        GiaBan_label.setText(App.numf.format(p.getPrice()) + "đ");

    }


}

