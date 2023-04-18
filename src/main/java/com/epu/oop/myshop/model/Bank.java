package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.util.Objects;



public class Bank implements Serializable {

    private String SoTaiKhoan;


    private String TenNH;


    private String ChuSoHuu;

    private User user;


    public Bank() {}


    public Bank(String soTaiKhoan, String tenNH, String chuSoHuu, User user) {
        SoTaiKhoan = soTaiKhoan;
        TenNH = tenNH;
        ChuSoHuu = chuSoHuu;
        this.user = user;
    }
    public Bank(User u)
    {
        this.user = u;
    }

    public String getSoTaiKhoan() {
        return SoTaiKhoan;
    }


    public void setSoTaiKhoan(String soTaiKhoan) {
        SoTaiKhoan = soTaiKhoan;
    }


    public String getTenNH() {
        return TenNH;
    }


    public void setTenNH(String tenNH) {
        TenNH = tenNH;
    }


    public String getChuSoHuu() {
        return ChuSoHuu;
    }


    public void setChuSoHuu(String chuSoHuu) {
        ChuSoHuu = chuSoHuu;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Bank [SoTaiKhoan=" + SoTaiKhoan + ", TenNH=" + TenNH + ", ChuSoHuu=" + ChuSoHuu + "]";
    }


    @Override
    public int hashCode() {
        return Objects.hash(ChuSoHuu, SoTaiKhoan, TenNH, user);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bank other = (Bank) obj;
        return Objects.equals(ChuSoHuu, other.ChuSoHuu) && Objects.equals(SoTaiKhoan, other.SoTaiKhoan)
                && Objects.equals(TenNH, other.TenNH) && Objects.equals(user, other.user);
    }



}
