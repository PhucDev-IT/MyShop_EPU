package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Bank;
import com.epu.oop.myshop.model.User;

import java.sql.*;
import java.util.List;
public class Bank_Dao implements Dao_Interface<Bank> {

    private final ConnectionPool jdbcUtil;
    private static Bank_Dao instance;

    public Bank_Dao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public static Bank_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new Bank_Dao(jdbcUtil);
        }
        return instance;
    }


    @Override
    public boolean Insert(Bank t) throws SQLException {
        int results = 0;

            String sql = "INSERT INTO Bank(SoTaiKhoan,TenNH,TenChiNhanh,ChuSoHuu,SoCCCD,Users_ID)" +
                    " VALUES (?,?,?,?,?,?)";
        Connection connection = null;
        try {
             connection = jdbcUtil.getConnection();
             connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,t.getSoTaiKhoan());
            statement.setString(2,t.getTenNH());
            statement.setString(3,t.getTenChiNhanh());
            statement.setString(4,t.getChuSoHuu());
            statement.setString(5,t.getSoCCCD());
            statement.setInt(6,t.getUser().getID());

            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");
            connection.commit();
            statement.close();
        }catch (SQLException e) {
            if(connection!=null) {
                connection.rollback();
                System.out.println("roll back: "+e.getMessage());
            }

        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return results>0;
    }

    @Override
    public List<Bank> SelectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bank SelectByID(Bank t) throws SQLException {
        Bank bank = null;

            String sql = "SELECT * FROM Bank WHERE Users_ID=?";
        Connection connection = null;
        try {
             connection = jdbcUtil.getConnection();
             connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,t.getUser().getID());

            //Bước 3:Thực thi câu lệnh
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String SoTaiKhoan = rs.getString("SoTaiKhoan");
                String TenNH = rs.getString("TenNH");
                String ChuSoHuu = rs.getString("ChuSoHuu");
                int user_id = rs.getInt("Users_ID");
                String tenChiNhanh = rs.getString("TenChiNhanh");
                String SoCCCD = rs.getString("SoCCCD");

<<<<<<< Updated upstream
                bank = new Bank(SoTaiKhoan,TenNH,tenChiNhanh,ChuSoHuu,SoCCCD,new User(user_id));
=======
                bank = new Bank(SoTaiKhoan,tenChiNhanh,SoCCCD,TenNH,ChuSoHuu,new User(user_id,""));
>>>>>>> Stashed changes
            }
            statement.close();
            rs.close();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("roll back: "+e.getMessage());
            }

        }finally {

            connection.close();
        }

        return bank;

    }

    @Override
    public int Update(Bank t) throws SQLException {
        int results = 0;

            String sql = "UPDATE Bank SET " +
                    " SoTaiKhoan=?," +
                    " TenNH=?," +
                    " ChuSoHuu=?," +
                    " TenChiNhanh = ?," +
                    " SoCCCD = ?" +
                    " WHERE Users_ID=?";
        Connection connection = null;
        try {
             connection = jdbcUtil.getConnection();
             connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,t.getSoTaiKhoan());
            statement.setString(2,t.getTenNH());
            statement.setString(3,t.getChuSoHuu());
            statement.setString(4,t.getTenChiNhanh());
            statement.setString(5,t.getSoCCCD());
            statement.setInt(6,t.getUser().getID());

            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");
            statement.close();
           connection.commit();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("roll back: "+e.getMessage());
                e.printStackTrace();
            }
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return results;
    }

    @Override
    public int Delete(Bank t) {
        int results = 0;

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "DELETE FROM Bank" +
                    " WHERE Users_ID = ?";

        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getUser().getID());

            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
