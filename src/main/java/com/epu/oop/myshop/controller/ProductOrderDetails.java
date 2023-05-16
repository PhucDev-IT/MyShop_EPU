package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ProductOrderDetails {

    @FXML
    private ImageView imgProduct;

    @FXML
    private Label maSP;

    @FXML
    private Text nameProduct;

    @FXML
    private Label price;

    @FXML
    private Label quantity;


    public void setData(Product pro,int number)
    {

        nameProduct.setText(pro.getTenSP());
        maSP.setText("0"+pro.getID());
        price.setText(App.numf.format(pro.getPrice()) +"đ");
        quantity.setText(number+"");

        try{
            if(pro.getSrcImg().equals(":")){
                imgProduct.setImage(new Image(pro.getSrcImg()));
            }else{
                imgProduct.setImage(new Image(getClass().getResourceAsStream(pro.getSrcImg())));
            }
        }catch (Exception e){
            imgProduct.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
            System.out.println("Không load được ảnh: "+e.getMessage());
        }
    }
}
