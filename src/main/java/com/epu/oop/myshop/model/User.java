package com.epu.oop.myshop.model;


import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable {


    private int ID;


    private String FullName;


    private String Gender;


    private Date dateOfBirth;


    private String Address;

    private String CanCuocCongDan;


    private String Email;


    private String numberPhone;


    private String SrcAvatar;


    private Account account;


    //----------------------- CONSTRUCTOR ------------------------------------------------------------
    public User() {
    }

    public User(int ID) {
        this.ID = ID;
    }

    public User(Account a) {
        this.account = a;

    }

    public User(String fullName, String gender, Date dateOfBirth, String address, String canCuocCongDan,
                String email, String numberPhone, String srcAvatar, Account account) {
        FullName = fullName;
        Gender = gender;
        this.dateOfBirth = dateOfBirth;
        Address = address;
        CanCuocCongDan = canCuocCongDan;
        Email = email;
        this.numberPhone = numberPhone;
        SrcAvatar = srcAvatar;
        this.account = account;
    }

    public User(int ID, String fullName, String gender, Date dateOfBirth, String address, String canCuocCongDan,
                String email, String numberPhone, String srcAvatar) {
        FullName = fullName;
        this.Gender = gender;
        this.dateOfBirth = dateOfBirth;
        Address = address;
        CanCuocCongDan = canCuocCongDan;
        Email = email;
        this.numberPhone = numberPhone;
        SrcAvatar = srcAvatar;
        this.ID = ID;
    }


    //----------------------- GETTER - SETTER ---------------------------------------------
    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCanCuocCongDan() {
        return CanCuocCongDan;
    }

    public void setCanCuocCongDan(String canCuocCongDan) {
        CanCuocCongDan = canCuocCongDan;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSrcAvatar() {
        return SrcAvatar;
    }

    public void setSrcAvatar(String srcAvatar) {
        SrcAvatar = srcAvatar;
    }

    @Override
    public String toString() {
        return "User [ID=" + ID + ", FullName=" + FullName + ", Gender=" + Gender + ", dateOfBirth=" + dateOfBirth
                + ", Address=" + Address + ", CanCuocCongDan=" + CanCuocCongDan + ", Email=" + Email + ", numberPhone="
                + numberPhone + ", SrcAvatar=" + SrcAvatar + "]";
    }



}

