package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.User;

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
    public boolean Insert(Account t) throws SQLException {
        int results = 0;
        String sql = "INSERT INTO Account(UserName,Passwords,Currency,Activity,PhanQuyen)" +
                " VALUES (?,?,?,?,?)";
            //Try - catch - resources : giúp tự động close, tránh rò rĩ tài nguyên
        Connection connection = null;
           try{
               connection = JDBCUtil.getConnection();
               connection.setAutoCommit(false);
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1,t.getUserName());
               statement.setString(2,t.getPassword());
               statement.setBigDecimal(3,new BigDecimal(0));
               statement.setString(4,"ON");
               statement.setString(5,"Member");

               //Bước 3:Thực thi câu lệnh
               results =  statement.executeUpdate();
               statement.close();
               connection.commit();
       }catch (SQLException e) {
               if(connection!=null){
                   connection.rollback();
               }
           e.printStackTrace();
       }finally {
              connection.setAutoCommit(true);
              connection.close();
           }
        return results>0;
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
                String Statuss = rs.getString("Activity");
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
                String Statuss = rs.getString("Activity");
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
                    " Activity=?," +
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
        int check = 0;
        String sql = "SELECT COUNT(*) AS number FROM Account WHERE ID = ?";
        try(Connection conn = JDBCUtil.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1,account.getID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                check = resultSet.getInt("number");
            }

            statement.close();
            JDBCUtil.CloseConnection(conn);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra: "+e.getMessage());
        }
        return  check>0;
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
                String Statuss = rs.getString("Activity");
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

    //Đăng ký thông tin người dùng
    public boolean signUpUser(Account account, User user) throws SQLException {
        int check = 0;
        Connection connection = null;
        PreparedStatement preAccount = null;
        PreparedStatement preUser = null;
        try{

            String sql = "INSERT INTO Account(UserName,Passwords,Currency,Activity,PhanQuyen)" +
                    " VALUES (?,?,?,?,?)";
            String sqlUs = "INSERT INTO Users(Account_ID,FullName,Email)" +
                    " VALUES (?,?,?)";

            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false);
            preAccount = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preAccount.setString(1,account.getUserName());
            preAccount.setString(2,account.getPassword());
            preAccount.setBigDecimal(3,new BigDecimal(0));
            preAccount.setString(4,"ON");
            preAccount.setString(5,"Member");
            preAccount.executeUpdate();

            int index;
            try(ResultSet rs = preAccount.getGeneratedKeys()){
                if(rs.next()){
                   index =  rs.getInt(1);
                }else {
                    throw new SQLException("Không thể đăng ký");
                }
            }
            preUser = connection.prepareStatement(sqlUs);
            preUser.setInt(1,index);
            preUser.setString(2,user.getFullName());
            preUser.setString(3,user.getEmail());
            check = preUser.executeUpdate();

            connection.commit();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
            }
            e.printStackTrace();
        }finally {
            if(preUser!=null)   preUser.close();

            if(preAccount!=null) preAccount.close();
            connection.setAutoCommit(true);
            JDBCUtil.CloseConnection(connection);
        }
        return check>0;
    }


}
