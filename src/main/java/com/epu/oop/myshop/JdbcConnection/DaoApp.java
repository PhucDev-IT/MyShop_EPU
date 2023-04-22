package com.epu.oop.myshop.JdbcConnection;

import com.epu.oop.myshop.Dao.Account_Dao;

import java.sql.Connection;

public class DaoApp {
    private Connection conn;

    public static ConnectionPool connectionPool;

    public static Account_Dao account_dao;

    public DaoApp(){
        connectionPool = new ConnectionPool();
        account_dao = Account_Dao.getInstance(connectionPool);
    }


}
