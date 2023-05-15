package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.OrderDetails_Dao;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductsOfMarketController {

    @FXML
    private ImageView imgProducts;

    @FXML
    private Label nameProduct_txt;

    @FXML
    private Label price;

    @FXML
    private Label sold;

    private Product product;

    //Interface mylistener để bắt sự kiện khi nhấn vào các sản phẩm
    private MyListener<Product> mylistener;

    @FXML
    private void click(MouseEvent mouseEvent) {
        mylistener.onClickListener(product);
    }

    public void setData(MyListener<Product> myListener,Product prod)
    {
        this.product = prod;
        this.mylistener = myListener;

        try {
            if(!prod.getSrcImg().contains(":")){
                imgProducts.setImage(new Image(getClass().getResourceAsStream(prod.getSrcImg())));
            }else
                imgProducts.setImage(new Image(product.getSrcImg()));
        }catch (Exception e){
            imgProducts.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
        }
        nameProduct_txt.setText((prod.getTenSP()));
        sold.setText(prod.getSold()+"");

        //Định dạng tiền tệ việt nam
        Locale lc = new Locale("vi","VN");
        NumberFormat numf = NumberFormat.getInstance(lc);
        price.setText(numf.format(prod.getPrice())+ "");

    }


}
