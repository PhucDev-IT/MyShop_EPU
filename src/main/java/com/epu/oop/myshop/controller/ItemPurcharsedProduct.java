package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.model.OrderDetails;
import com.epu.oop.myshop.model.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;


public class ItemPurcharsedProduct implements Initializable {

    @FXML
    private ImageView img_ThanhTien;
    @FXML
    private ImageView imgProduct;

    @FXML
    private Text nameProduct_lb;

    @FXML
    private Label SellerProduct_label;

    @FXML
    private Label price_label;

    @FXML
    private Label Quantity_label;

    @FXML
    private Label ToTal_label;

    @FXML
    private Label ngayMua_label;

    private OrderDetails cthd_dao;

    public Locale lc = new Locale("vi", "VN");
    public NumberFormat numf = NumberFormat.getInstance(lc);

    public void setData(Object[] obj)
    {

            OrderDetails cthd = (OrderDetails) obj[1];
            Product product = (Product) obj[0];
            imgProduct.setImage(new Image(product.getSrcImg()));
            nameProduct_lb.setText(product.getTenSP());
            price_label.setText(numf.format(cthd.getPrice())+"đ");
            Quantity_label.setText(cthd.getQuantity()+"");
            ngayMua_label.setText(obj[2]+"");
            SellerProduct_label.setText(obj[3]+"");
            ToTal_label.setText(numf.format((cthd.getPrice().multiply(new BigDecimal(cthd.getQuantity()))))+"đ");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cthd_dao = new OrderDetails();
        img_ThanhTien.setImage(new Image("C:\\Users\\84374\\OneDrive\\Pictures\\thanhtien.png"));
    }
}
