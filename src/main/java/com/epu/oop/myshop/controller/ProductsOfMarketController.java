package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.CTHoaDon_Dao;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductsOfMarketController {

    @FXML
    private ImageView imgProducts;

    @FXML
    private Text nameProduct_txt;

    @FXML
    private Label price;

    @FXML
    private Label sold;
    private CTHoaDon_Dao cthd_dao  =  new CTHoaDon_Dao();

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

       this.imgProducts.setImage(new Image(prod.getSrcImg()));
        nameProduct_txt.setText((prod.getTenSP()));
       // Object[] resul = cthd_dao.SoLuongVaTongTienDaBan(prod);
        float daban = 0;
//        if(resul!=null && resul[0]!=null ){
//            daban = (float) resul[0];
//        }

        sold.setText(daban+"");

        //Định dạng tiền tệ việt nam
        Locale lc = new Locale("vi","VN");
        NumberFormat numf = NumberFormat.getInstance(lc);
        price.setText(numf.format(prod.getDonGia())+ "");

    }

}
