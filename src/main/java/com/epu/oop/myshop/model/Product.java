package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;




public class Product implements Serializable {


    private int ID;

    private String TenSP;

    private float SoLuong;

    private BigDecimal DonGia;

    private String MoTa;

    private String SrcImg;

    private String Status;

    private int  idCategory;

    private User user;

    public Product()
    {

    }

    public Product(int id, String tenSP)
    {
        this.ID = id;
        this.TenSP = tenSP;
    }
    public Product(String tenSP, float soLuong, BigDecimal donGia, String moTa, String srcImg, int category, User user) {
        TenSP = tenSP;
        SoLuong = soLuong;
        DonGia = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        Status = "ON";
        this.idCategory = category;
        this.user = user;
    }

    public Product(int iD, String tenSP, float soLuong, BigDecimal donGia, String srcImg) {
        ID = iD;
        TenSP = tenSP;
        SoLuong = soLuong;
        DonGia = donGia;
        SrcImg = srcImg;

    }


    //Sellect Các mặt hàng đang bán của User
    public Product(int id ,String tenSP, float soLuong, BigDecimal donGia, String moTa, String srcImg,
                    int category) {
        this.ID = id;
        TenSP = tenSP;
        SoLuong = soLuong;
        DonGia = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        this.idCategory = category;
        Status = "ON";
    }

    //Select hóa đơn đã mua của user
    public Product(int id, String tenSP, float soLuong, BigDecimal donGia, String moTa, String srcImg, String statuss) {
        this.ID = id;
        TenSP = tenSP;
        SoLuong = soLuong;
        DonGia = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        Status = statuss;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public float getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(float soLuong) {
        SoLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public void setDonGia(BigDecimal donGia) {
        DonGia = donGia;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getSrcImg() {
        return SrcImg;
    }

    public void setSrcImg(String srcImg) {
        SrcImg = srcImg;
    }


    public int getCategory() {
        return idCategory;
    }

    public void setCategory(int category) {
        this.idCategory = category;
    }



    public String getStatus() {
        return Status;
    }



    public void setStatus(String status) {
        Status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nProducts [ID=" + ID + ", TenSP=" + TenSP + ", SoLuong=" + SoLuong + ", DonGia=" + DonGia + ", MoTa="
                + MoTa + ", SrcImg=" + SrcImg+", "+ Status;
    }


}
