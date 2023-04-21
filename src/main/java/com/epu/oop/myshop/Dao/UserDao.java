package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Category;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao_Interface<User> {

    private static UserDao instance;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
            System.out.println("Tạo đối tượng userDao");
        }
        return instance;
    }

    @Override
    public boolean Insert(User t) {
       return true;
    }

    @Override
    public List<User> SelectAll() {
        List<User> list = new ArrayList<>();

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "SELECT * FROM Users";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){

            while (rs.next()){
                int ID = rs.getInt("Account_ID");
                String UserName = rs.getString("FullName");
                String Gender = rs.getString("Gender");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                String Address = rs.getString("HomeTown");
                String CCCD = rs.getString("CCCD");
                String Email = rs.getString("Email");
                String Phone = rs.getString("Phone");
                String SrcAvatar = rs.getString("SrcAvatar");

                list.add(new User(ID,UserName,Gender,dateOfBirth,Address,CCCD,Email,Phone,SrcAvatar));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User SelectByID(User t) {
        User user = null;

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "SELECT * FROM Users WHERE Account_ID=?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());

            //Bước 3:Thực thi câu lệnh
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("Account_ID");
                String UserName = rs.getString("FullName");
                String Gender = rs.getString("Gender");
                Date dateOfBirth = rs.getDate("DateOfBirth");
                String Address = rs.getString("HomeTown");
                String CCCD = rs.getString("CCCD");
                String Email = rs.getString("Email");
                String Phone = rs.getString("Phone");
                String SrcAvatar = rs.getString("SrcAvatar");

                user = new User(ID,UserName,Gender,dateOfBirth,Address,CCCD,Email,Phone,SrcAvatar);
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public int Update(User t) {
        int results = 0;

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "UPDATE Users SET " +
                    "FullName=?," +
                    " Gender=?," +
                    " DateOfBirth=?," +
                    " HomeTown=?" +
                    ", CCCD=?" +
                    ", Phone=?" +
                    ", SrcAvatar=?" +
                    " WHERE Account_ID=?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getFullName());
            statement.setString(2,t.getGender());
            statement.setDate(3,t.getDateOfBirth());
            statement.setString(4,t.getAddress());
            statement.setString(5,t.getCanCuocCongDan());
            statement.setString(6,t.getNumberPhone());
            statement.setString(7,t.getSrcAvatar());
            statement.setInt(8,t.getID());

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
    public int Delete(User t) {
        int results = 0;

            //Bước 2:Tạo đối tượng PreparedStatemt
            String sql = "DELETE FROM Users" +
                    " WHERE Account_ID = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());

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

    //Tất cả mặt hàng mà người dùng đang bán
    public List<Product> listProducts(User t)
    {
        List<Product> list = new ArrayList<>();


            String sql = "SELECT Product.* FROM Product INNER JOIN ProductSeller " +
                    "ON Product.MaSP = ProductSeller.Product_ID " +
                    "AND Product.Activity = 'ON' " +
                    "AND ProductSeller.Users_ID = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("totalrevenue");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc));
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Lấy thông tin hóa đơn đã mua
    public List<Product> selectHoaDon(User us){
        List<Product> list = new ArrayList<>();

            String sql = "SELECT Product.* FROM Product JOIN CTHoaDon " +
                    "ON CTHoaDon.Product_ID = Product.MaSP " +
                    "INNER JOIN HoaDon ON HoaDon.ID = CTHoaDon.HoaDon_ID " +
                    "AND HoaDon.Users_ID = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,us.getID());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                String Statuss = rs.getString("Activity");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,Statuss));
            }
            statement.close();
            rs.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Select người chuyển tiên
    public String searchPersonRemitters(int ID){
        String email = null;

        //Bước 2:Tạo đối tượng PreparedStatemt
        String sql = "SELECT * FROM Users WHERE Account_ID=?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,ID);

            //Bước 3:Thực thi câu lệnh
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                email = rs.getString("Email");
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }
}
