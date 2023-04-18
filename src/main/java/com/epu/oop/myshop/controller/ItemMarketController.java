package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.CTHoaDon_Dao;
import com.epu.oop.myshop.Dao.HoaDon_Dao;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class ItemMarketController implements Initializable{


    //------------------------------- ITEM SEARCH PRODUCT-------------------------------------------
    @FXML
    private ImageView img_Product;

    @FXML
    private Text nameProduct_SearchForm;
    //-------------------------------------------------------------
    @FXML
    private Label nameProduct;

    @FXML
    private ImageView imgProducts;

    @FXML
    private Label price;

    @FXML
    private Label sold;		//Đã bán

    private Product product;

    private HoaDon_Dao hoaDon_Dao = new HoaDon_Dao();
    private CTHoaDon_Dao cthd_dao;
    //Interface mylistener để bắt sự kiện khi nhấn vào các sản phẩm
    private MyListener<Product> mylistener;

    @FXML
    private Text nameProduct_txt;

    @FXML
    private void click(MouseEvent mouseEvent) {
        mylistener.onClickListener(product);
    }
    public void setData(MyListener<Product> myListener, Product prod)
    {
        this.product = prod;
        this.mylistener = myListener;

        this.imgProducts.setImage(new Image(prod.getSrcImg()));
        this.nameProduct.setText(prod.getTenSP());


        Object[] resul = cthd_dao.SoLuongVaTongTienDaBan(prod);
        float daban = 0;
        if(resul!=null && resul[0]!=null ){
            daban = (float) resul[0];
        }

        sold.setText(daban+"");

        //Định dạng tiền tệ việt nam
        Locale lc = new Locale("vi","VN");
        NumberFormat numf = NumberFormat.getInstance(lc);
        price.setText(numf.format(prod.getDonGia())+ "");

    }

    public void setDataSearchProduct(MyListener<Product> myListener, Product prod)
    {
        this.product = prod;
        this.mylistener = myListener;

        this.nameProduct_SearchForm.setText(prod.getTenSP());
        this.img_Product.setImage(new Image(prod.getSrcImg()));

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cthd_dao =  new CTHoaDon_Dao();

    }


}
