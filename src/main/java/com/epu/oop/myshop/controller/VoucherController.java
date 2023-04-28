package com.epu.oop.myshop.controller;

import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.MyListener;
import com.epu.oop.myshop.model.VoucherModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.math.BigDecimal;


public class VoucherController {
    @FXML
    private ImageView imgVoucher;

    @FXML
    private Label maVoucher_lb;

    @FXML
    private Label giamGia_lb;

    @FXML
    private Text NoiDung_lb;

    @FXML
    private Label soLuong_lb;

    @FXML
    private Label ngayBatDau_lb;

    @FXML
    private Label ngayKetThuc_lb;

    private VoucherModel voucher;
    private MyListener<VoucherModel> myListener;

    @FXML
    public void click(MouseEvent event){
        myListener.onClickListener(voucher);
    }

    public void setData(MyListener<VoucherModel> myListener, VoucherModel vc){
        this.myListener = myListener;
        this.voucher = vc;

        try{
            if(vc.getImgVoucher().equals(":")){
                imgVoucher.setImage(new Image(vc.getImgVoucher()));
            }else{
                imgVoucher.setImage(new Image(getClass().getResourceAsStream(vc.getImgVoucher())));
            }
        }catch (Exception e){
            imgVoucher.setImage(new Image(getClass().getResourceAsStream("/com/epu/oop/myshop/image/imgError.png")));
            System.out.println("Không load được ảnh: "+e.getMessage());
        }

        maVoucher_lb.setText(vc.getMaVoucher());
        soLuong_lb.setText(vc.getSoLuong()+"");
        NoiDung_lb.setText(vc.getNoiDung());
        ngayBatDau_lb.setText(App.formatter.format(vc.getNgayBatDau()));
        ngayKetThuc_lb.setText(App.formatter.format(vc.getNgayKetThuc()));

        if(vc.getTiLeGiamGia()>0.0){
            giamGia_lb.setText(vc.getTiLeGiamGia()+"%");
        }else if(vc.getSoTienGiam()!=null || vc.getSoTienGiam().compareTo(BigDecimal.ZERO)>0){
            giamGia_lb.setText(App.numf.format(vc.getSoTienGiam())+"đ");
        }
    }

}
