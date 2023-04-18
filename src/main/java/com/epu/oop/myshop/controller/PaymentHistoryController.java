package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Dao.Account_Dao;
import com.epu.oop.myshop.Dao.UserDao;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.PaymentHistory;
import com.epu.oop.myshop.model.Temp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentHistoryController {

    @FXML
    private ImageView icon_RutTien_IMG;

    @FXML
    private Label tenGiaoDich_lb;

    @FXML
    private Label noidung;

    @FXML
    private Label ngayGiaoDich_Lb;

    @FXML
    private Label soTien_lb;
    private UserDao userDao = UserDao.getInstance();

    public void setData(PaymentHistory p)
    {
        icon_RutTien_IMG.setImage(new Image(getClass().getResourceAsStream(p.getImgSrcIcon())));
        ngayGiaoDich_Lb.setText(p.getNgayGiaoDich()+"");
        soTien_lb.setText(App.numf.format(p.getSoTien())+"đ");

        if(p.getAccount().getID()==0){
            tenGiaoDich_lb.setText(p.getTenGiaoDich());
            noidung.setText(p.getNoiDung());

        }else{
            if(Temp.account.getID()==p.getAccount().getID()){
                tenGiaoDich_lb.setText("Nhận tiền");
                String nguoiGui = userDao.searchPersonRemitters(p.getUser().getID());
                noidung.setText("Nhận từ: "+nguoiGui);
            }else {
                    tenGiaoDich_lb.setText(p.getTenGiaoDich());
                    noidung.setText("Người nhận: "+p.getNoiDung());
                }

        }

    }

}
