package com.epu.oop.myshop.controller;


import com.epu.oop.myshop.Dao.UserDao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;

import com.epu.oop.myshop.model.PaymentHistory;
import com.epu.oop.myshop.model.Temp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


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



    public void setData(Object[] obj)
    {
        PaymentHistory p = (PaymentHistory) obj[0];
        String nguoiNhan = (String) obj[1];

        icon_RutTien_IMG.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(p.getImgSrcIcon()))));
        soTien_lb.setText(App.numf.format(p.getSoTien())+"đ");
        ngayGiaoDich_Lb.setText(p.getNgayGiaoDich()+"");

        //Nếu là mua hàng thì account sẽ là NULL
        if(p.getAccount().getID()==0){
            tenGiaoDich_lb.setText(p.getTenGiaoDich());
            noidung.setText(p.getNoiDung());
        }else if(Temp.account.getID() == p.getAccount().getID()){   //Nếu là nhận tiền khi chuyển tiền có ghi ID ng nhận
            tenGiaoDich_lb.setText("Nhận tiền");
            noidung.setText("Nhận từ: "+p.getNoiDung());    //Nội dung sẽ là tên người chuyển
        }else if(Temp.account.getID() == p.getUser().getID() && (p.getUser().getID()!=p.getAccount().getID())){
            tenGiaoDich_lb.setText("Chuyển tiền");
            noidung.setText("Chuyển đến: "+nguoiNhan);
        }
    }

}
