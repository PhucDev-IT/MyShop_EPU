package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Account implements Serializable {

    private int ID;

    private String UserName;


    private String Password;


    private BigDecimal money;


    private String Status;


    private String PhanQuyen;




    //----------------------- CONSTRUCTOR ------------------------------------------------------------
    public Account() {
        this.Status = "ON";
        this.PhanQuyen = "Thành Viên";
        this.money = new BigDecimal("0.0");
    }

    public Account(int ID){
        this.ID = ID;
    }

    public Account(int ID, String userName, String password, BigDecimal money, String status, String PhanQuyen) {
        this.ID = ID;
        UserName = userName;
        Password = password;
        this.money = money;
        Status = status;
        this.PhanQuyen = PhanQuyen;
    }

    //Cập nhật thông tin (thêm ngân hàng là thành ng bán)
    public Account(String userName, String password, String status, String PhanQuyen) {
        UserName = userName;
        Password = password;
        Status = status;
        this.PhanQuyen = PhanQuyen;
    }

    //Dành cho đăng nhập - đăng ký
    public Account(int ID,String User, String pass)
    {
        this.ID = ID;
        this.UserName = User;
        this.Password = pass;
        this.Status = "ON";
        this.money = new BigDecimal(0);
        this.PhanQuyen = "Thành Viên";
    }

    //----------------------- GETTER - SETTER ---------------------------------------------
    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }



    public String getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        PhanQuyen = phanQuyen;
    }

    @Override
    public String toString() {
        return "Account [ID=" + ID + ", UserName=" + UserName + ", Password=" + Password + ", money=" + money
                + ", Status=" + Status + ", PhanQuyen=" + PhanQuyen + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getID() == account.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
