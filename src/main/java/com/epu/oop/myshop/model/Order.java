package com.epu.oop.myshop.model;



import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;


public class Order implements Serializable{

    private int MaHD;

    private Date NgayLapHD;

    private BigDecimal TongTien;


    private BigDecimal ThanhTien;

    private VoucherModel voucher;
    private User user;





    //-------------------------------- CONSTRUCTOR ------------------------------------------

    public Order() {}

    public Order(int id, Date ngayLapHD, BigDecimal TongTien, VoucherModel voucher, BigDecimal thanhTien, User user) {
        MaHD = id;
        NgayLapHD = ngayLapHD;
        ThanhTien = thanhTien;
        this.user = user;
        this.TongTien = TongTien;
        this.voucher = voucher;
    }
    //Mua hàng
    public Order(Date ngayLapHD, BigDecimal tongTien, VoucherModel voucher, BigDecimal ThanhTien, User u)
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
        return "Order [MaHD=" + MaHD + ", NgayLapHD=" + NgayLapHD + ", ThanhTien=" + ThanhTien + ", user=" + user
                + "]";
    }

}

