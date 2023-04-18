package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Account;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Account_Dao implements Dao_Interface<Account> {

    private static Account_Dao instance;

    public static Account_Dao getInstance() {
        if (instance == null) {
            instance = new Account_Dao();
        }
        return instance;
    }

    @Override
    public int Insert(Account t) {
        int results = 0;
        String sql = "INSERT INTO Account(UserName,Passwords,Currency,Statuss,PhanQuyen)" +
                " VALUES (?,?,?,?,?)";
            //Try - catch - resources : giúp tự động close, tránh rò rĩ tài nguyên

           try(Connection connection = JDBCUtil.getConnection();
               PreparedStatement statement = connection.prepareStatement(sql)){

               statement.setString(1,t.getUserName());
               statement.setString(2,t.getPassword());
               statement.setBigDecimal(3,new BigDecimal(0));
               statement.setString(4,"ON");
               statement.setString(5,"Member");

               //Bước 3:Thực thi câu lệnh
               results =  statement.executeUpdate();
               statement.close();
               JDBCUtil.CloseConnection(connection);
       }catch (SQLException e) {
           e.printStackTrace();
       }
        return results;
    }

    @Override
    public List<Account> SelectAll() {

        List<Account> list = new ArrayList<>();

            String sql = "SELECT * FROM Account";

            try(Connection connection = JDBCUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()){
                 while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Statuss");
                String PhanQuyen = rs.getString("PhanQuyen");

                list.add(new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen));

            }
                 statement.close();
                JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Account SelectByID(Account t) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE UserName=?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getUserName());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Statuss");
                String PhanQuyen = rs.getString("PhanQuyen");

                account =  new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen);
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public int Update(Account t) {
        int results = 0;
            String sql = "UPDATE Account SET " +
                    "Passwords=?," +
                    " Currency=?," +
                    " Statuss=?," +
                    " PhanQuyen=?" +
                    " WHERE UserName=?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getPassword());
            statement.setBigDecimal(2,t.getMoney());
            statement.setString(3,t.getStatus());
            statement.setString(4,t.getPhanQuyen());
            statement.setString(5,t.getUserName());

            results = statement.executeUpdate();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int Delete(Account t) {
        int results = 0;
            String sql = "DELETE FROM Account" +
                    " WHERE UserName = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,t.getUserName());
            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    //---------------Kiểm tra đăng ký tài khoản---------------------

    public boolean checkRegister(Account account)
    {
        Account a = SelectByID(account);

        if(a!=null)
        {
            return true;
        }else
            return false;
    }

    //Kiểm tra đăng nhập
    public Account checkLogin(Account account)
    {
        Account a = null;

            String sql = "SELECT * FROM Account " +
                    "WHERE UserName=? " +
                    "AND Passwords = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,account.getUserName());
            statement.setString(2,account.getPassword());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Statuss");
                String PhanQuyen = rs.getString("PhanQuyen");

                a = new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen);
            }
            statement.close();
            rs.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra: "+e.getMessage());
        }

       return a;
    }

    //Lấy giá trị lớn nhất (ID mới được thêm) trong bảng Account để đặt khóa ngoại cho Bảng User
    public int IndexNewInsert()
    {
        int maxID = 0;

            String sql = "SELECT MAX(ID) FROM Account";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                maxID = result.getInt(1);
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e)
        {
            System.out.println("Có lỗi xảy ra, Không thể lấy index Account mới thêm: "+e.getMessage());
        }
        return maxID;
    }


}
