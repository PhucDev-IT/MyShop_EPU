package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Bank;
import com.epu.oop.myshop.model.User;

import java.sql.*;
import java.util.List;
public class Bank_Dao implements Dao_Interface<Bank> {

    private static Bank_Dao instance;

    public static Bank_Dao getInstance() {
        if (instance == null) {
            instance = new Bank_Dao();
        }
        return instance;
    }


    @Override
    public int Insert(Bank t) {
        int results = 0;

            String sql = "INSERT INTO Bank(SoTaiKhoan,TenNH,ChuSoHuu,Users_ID)" +
                    " VALUES (?,?,?,?)";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getSoTaiKhoan());
            statement.setString(2,t.getTenNH());
            statement.setString(3,t.getChuSoHuu());
            statement.setInt(4,t.getUser().getID());

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

    @Override
    public List<Bank> SelectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bank SelectByID(Bank t) {
        Bank bank = null;

            String sql = "SELECT * FROM Bank WHERE Users_ID=?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getUser().getID());

            //Bước 3:Thực thi câu lệnh
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String SoTaiKhoan = rs.getString("SoTaiKhoan");
                String TenNH = rs.getString("TenNH");
                String ChuSoHuu = rs.getString("ChuSoHuu");
                int user_id = rs.getInt("Users_ID");

                bank = new Bank(SoTaiKhoan,TenNH,ChuSoHuu,new User(user_id));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return bank;

    }

    @Override
    public int Update(Bank t) {
        int results = 0;

            String sql = "UPDATE Bank SET " +
                    "SoTaiKhoan=?," +
                    " TenNH=?," +
                    " ChuSoHuu=?," +
                    " WHERE Users_ID=?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getSoTaiKhoan());
            statement.setString(2,t.getTenNH());
            statement.setString(3,t.getChuSoHuu());
            statement.setInt(4,t.getUser().getID());

            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int Delete(Bank t) {
        int results = 0;

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "DELETE FROM Bank" +
                    " WHERE Users_ID = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getUser().getID());

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

}
