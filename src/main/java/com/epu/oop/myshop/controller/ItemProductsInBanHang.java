package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.OrderDetails_Dao;
import com.epu.oop.myshop.Dao.Order_Dao;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

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

    private Order_Dao hoaDon_Dao = new Order_Dao();

    private OrderDetails_Dao cthd_dao = new OrderDetails_Dao();

    private MyListener<Product> myListener;

    //Add sự kiện cho form mỗi khi nhấn
    public void click(MouseEvent e)
    {
        myListener.onClickListener(prod);
    }


    public void setData(MyListener<Product> mylistener, Product p)
    {

        this.prod = p;
        this.myListener = mylistener;

        image.setImage(new Image(p.getSrcImg()));
        tenHang_text.setText(p.getTenSP());
        SoLuong_label.setText(p.getQuantity()+"");

        DaBan_Label.setText(p.getSold()+"");
        Locale lc = new Locale("vi","VN");
        NumberFormat numf = NumberFormat.getInstance(lc);
        if(p.getTotalRevenue()!=null){
            DoanhThu_Label.setText(numf.format(p.getTotalRevenue()+"đ"));
        }else {
            DoanhThu_Label.setText("0đ");
        }
        GiaBan_label.setText(numf.format(p.getPrice())+ "");

    }


}

