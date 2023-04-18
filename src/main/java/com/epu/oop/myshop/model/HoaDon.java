package com.epu.oop.myshop.model;



import com.epu.oop.myshop.Dao.HoaDon_Dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


public class HoaDon implements Serializable{

    private int MaHD;

    private Date NgayLapHD;

    private BigDecimal TongTien;


    private BigDecimal ThanhTien;

    private VoucherModel voucher;
    private User user;





    //-------------------------------- CONSTRUCTOR ------------------------------------------

    public HoaDon() {}

    public HoaDon(int id,Date ngayLapHD, BigDecimal TongTien,VoucherModel voucher, BigDecimal thanhTien, User user) {
        MaHD = id;
        NgayLapHD = ngayLapHD;
        ThanhTien = thanhTien;
        this.user = user;
        this.TongTien = TongTien;
        this.voucher = voucher;
    }
    //Mua hàng
    public HoaDon(Date ngayLapHD,BigDecimal tongTien,VoucherModel voucher,BigDecimal ThanhTien,User u)
    {
        this.NgayLapHD = ngayLapHD;
        this.user = u;
        this.TongTien = tongTien;
        this.voucher = voucher;
        this.ThanhTien = ThanhTien;
    }



    //-----------------------GETTER - SETTER -----------------------------------------------


    public VoucherModel getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherModel voucher) {
        this.voucher = voucher;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        TongTien = tongTien;
    }


    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public Date getNgayLapHD() {
        return NgayLapHD;
    }

    public void setNgayLapHD(Date ngayLapHD) {
        NgayLapHD = ngayLapHD;
    }

    public BigDecimal getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        ThanhTien = thanhTien;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    //----------------------------------------------------------------------------------------------------------------



    @Override
    public String toString() {
        return "HoaDon [MaHD=" + MaHD + ", NgayLapHD=" + NgayLapHD + ", ThanhTien=" + ThanhTien + ", user=" + user
                + "]";
    }

}

