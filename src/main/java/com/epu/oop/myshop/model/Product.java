package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;




public class Product implements Serializable {


    private int ID;

    private String TenSP;

    private float quantity;

    private BigDecimal price;

    private String MoTa;

    private String SrcImg;

    private String Activity;

    private float sold;

    private BigDecimal TotalRevenue;

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
        this.quantity = soLuong;
        this.price = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        Activity = "ON";
        this.idCategory = category;
        this.user = user;
    }

    public Product(int iD, String tenSP, float soLuong, BigDecimal donGia, String srcImg) {
        ID = iD;
        TenSP = tenSP;
        this.quantity = soLuong;
        this.price = donGia;
        SrcImg = srcImg;

    }


    //Sellect Các mặt hàng đang bán của User
    public Product(int id ,String tenSP, float soLuong, BigDecimal donGia, String moTa, String srcImg,float sold,
                   BigDecimal TotalRevenue, int category) {
        this.ID = id;
        TenSP = tenSP;
        this.quantity = soLuong;
        this.price = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        this.idCategory = category;
        Activity = "ON";
        this.sold = sold;
        this.TotalRevenue = TotalRevenue;
    }

    //Select hóa đơn đã mua của user
    public Product(int id, String tenSP, float soLuong, BigDecimal donGia, String moTa, String srcImg, String statuss) {
        this.ID = id;
        TenSP = tenSP;
        this.quantity = soLuong;
        this.price = donGia;
        MoTa = moTa;
        SrcImg = srcImg;
        Activity = statuss;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public float getSold() {
        return sold;
    }

    public void setSold(float sold) {
        this.sold = sold;
    }

    public BigDecimal getTotalRevenue() {
        return TotalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        TotalRevenue = totalRevenue;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "\nProducts [ID=" + ID + ", TenSP=" + TenSP + ", SoLuong=" + quantity + ", DonGia=" + price + ", MoTa="
                + MoTa + ", SrcImg=" + SrcImg+", "+ Activity;
    }


}
