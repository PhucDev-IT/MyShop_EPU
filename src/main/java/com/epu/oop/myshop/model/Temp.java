package com.epu.oop.myshop.model;

import com.epu.oop.myshop.Dao.HoaDon_Dao;
import com.epu.oop.myshop.Dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class Temp {

    public static Account account;

    public static User user;

    public static Bank bank;
    public static List<Category> Listcategory = new ArrayList<>();
   // public static List<Product> Listproducts = new ArrayList<>();

    public static UserDao userDao;
    public static HoaDon_Dao hoaDon_dao = HoaDon_Dao.getInstance();
    public Temp()
    {
        userDao = UserDao.getInstance();
        //hoaDon_dao = HoaDon_Dao.getInstance();
    }

}