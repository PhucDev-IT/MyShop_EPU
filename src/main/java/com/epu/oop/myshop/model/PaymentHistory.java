package com.epu.oop.myshop.model;


import java.math.BigDecimal;
import java.sql.Date;


public class PaymentHistory {

   private int ID;


    private String tenGiaoDich;

    private String NoiDung;
    private BigDecimal soTien;

    private Date ngayGiaoDich;

    private String imgSrcIcon;


    private User user;


    private Account account;

    public PaymentHistory(){}

    public PaymentHistory(int ID,String tenGiaoDich,String NoiDung, BigDecimal soTien, Date ngayGiaoDich, String imgSrcIcon,User u,Account account) {
       this.ID = ID;
        this.tenGiaoDich = tenGiaoDich;
        this.soTien = soTien;
        this.ngayGiaoDich = ngayGiaoDich;
        this.imgSrcIcon = imgSrcIcon;
        this.user = u;
        this.account = account;
        this.user = u;
        this.NoiDung = NoiDung;
    }
    public PaymentHistory(String tenGiaoDich,String NoiDung, BigDecimal soTien, Date ngayGiaoDich, String imgSrcIcon, User user, Account account) {
        this.tenGiaoDich = tenGiaoDich;
        this.soTien = soTien;
        this.ngayGiaoDich = ngayGiaoDich;
        this.imgSrcIcon = imgSrcIcon;
        this.user = user;
        this.account = account;
        this.NoiDung = NoiDung;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenGiaoDich() {
        return tenGiaoDich;
    }

    public void setTenGiaoDich(String tenGiaoDich) {
        this.tenGiaoDich = tenGiaoDich;
    }

    public BigDecimal getSoTien() {
        return soTien;
    }

    public void setSoTien(BigDecimal soTien) {
        this.soTien = soTien;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getImgSrcIcon() {
        return imgSrcIcon;
    }

    public void setImgSrcIcon(String imgSrcIcon) {
        this.imgSrcIcon = imgSrcIcon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
