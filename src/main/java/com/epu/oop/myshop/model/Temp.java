package com.epu.oop.myshop.model;

import com.epu.oop.myshop.Dao.Order_Dao;
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
    public static Order_Dao hoaDon_dao = Order_Dao.getInstance();
    public Temp()
    {
        userDao = UserDao.getInstance();
        //hoaDon_dao = Order_Dao.getInstance();
    }

}