package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Category;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.*;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;

public class Product_Dao implements Dao_Interface<Product>{
    private static Product_Dao instance;

    public static Product_Dao getInstance() {
        if (instance == null) {
            instance = new Product_Dao();
        }
        return instance;
    }
    @Override
    public int Insert(Product t) {
        int results = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

            String sql = "INSERT INTO Product(TenSP,SoLuong,DonGia,MoTa,SrcImg,Category_ID)" +
                    " VALUES (?,?,?,?,?,?)";
            String sqlProductSeller = "INSERT INTO ProductSeller (Product_ID,Users_ID) " +
                    "VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,t.getTenSP());
            statement.setFloat(2,t.getSoLuong());
            statement.setBigDecimal(3,t.getDonGia());
            statement.setString(4,t.getMoTa());
            statement.setString(5,t.getSrcImg());
            statement.setInt(6,t.getCategory());

            results += statement.executeUpdate();

            int index = IndexNewInsert();
            statement = connection.prepareStatement(sqlProductSeller);
            statement.setInt(1,index);
            statement.setInt(2,t.getUser().getID());

            results += statement.executeUpdate();
            //Bước 3:Thực thi câu lệnh

            System.out.println("Product_Dao - Có "+results +" thay đổi");
            statement.close();
            JDBCUtil.CloseConnection(connection);

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<Product> SelectAll() {
        List<Product> list = new ArrayList<>();

            String sql = "SELECT * FROM Product" +
                    " WHERE Statuss = 'ON' ";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }

        return list;
    }

    //Phân trang, truy vấn dữ liệu luưu vào bộ nhớ đệm cachedrowset
    public synchronized CachedRowSet CachedProduct(int idCate)
    {
        try (Connection conn = JDBCUtil.getConnection()) {
            CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedRowSet.setCommand("SELECT * FROM Product WHERE Category_ID = ?");
            cachedRowSet.setInt(1, idCate);

            cachedRowSet.execute(conn);
            JDBCUtil.CloseConnection(conn);

            return cachedRowSet;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Product SelectByID(Product t) {
        Product products = null;

            String sql = "SELECT * FROM Product " +
                    " WHERE ID=? " +
                    "AND p.Statuss = 'ON' ";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                products = new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc);
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }

        return products;
    }

    @Override
    public int Update(Product t) {
        int result = 0;

                String sql = "UPDATE Product"
                        + " SET "
                        + "TenSP =?, "
                        + " SoLuong=?,"
                        + " DonGia=?,"
                        + " MoTa=?,"
                        + " SrcImg=?,"
                        + "Status=?"
                        + " WHERE ID = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
               statement.setString(1,t.getTenSP());
               statement.setFloat(2,t.getSoLuong());
               statement.setBigDecimal(3,t.getDonGia());
               statement.setString(4,t.getMoTa());
               statement.setString(5,t.getSrcImg());
               statement.setString(6,t.getStatus());
               statement.setInt(7,t.getID());

               result = statement.executeUpdate();
            statement.close();
            JDBCUtil.CloseConnection(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int Delete(Product t) {
        int results = 0;

            String sql = "DELETE FROM Product" +
                    " WHERE ID = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());


            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");

            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Product> SearchProducts(String keyword)
    {
        List<Product> list = new ArrayList<>();

            String sql = "SELECT * FROM Product" +
                    " WHERE TenSP LIKE ? " +
                    "AND Statuss = 'ON'";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,"%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc));
            }
        }catch (SQLException e)
        {
            System.out.println("Không thể tìm sản phẩm: "+e.getMessage());
        }
        return list;
    }


    //Lấy thông tin người bán sản phẩm có ID để hiển thị thông tin
    public User selectSeller(Product p)
    {
        User user = null;


            String sql = "SELECT u.* FROM Users u JOIN ProductSeller ps" +
                    " ON u.ID = ps.Users_ID" +
                    " AND ps.Product_ID = ?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,p.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                int ID = rs.getInt("ID");
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
        }catch (SQLException e)
        {
            System.out.println("Có lỗi xảy ra,không lấy được thông tin người bán: "+e.getMessage());
        }
        return user;
    }

    //Select top 3 sản phẩm bán nhiều nhất
    public List<Product> topProducts()
    {
        List<Product> list = new ArrayList<>();

            String sql = "SELECT Top(3) p.* " +
                    "FROM Product p " +
                    "INNER JOIN (" +
                    "    SELECT ct.Product_ID, SUM(ct.Quantity) AS TotalQuantity" +
                    "    FROM CTHoaDon ct" +
                    "    GROUP BY ct.Product_ID" +
                    ") subq " +
                    "ON p.ID = subq.Product_ID " +
                    "AND Statuss = 'ON' " +
                    "ORDER BY subq.TotalQuantity DESC";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }

        return list;
    }

    public int IndexNewInsert()
    {
        int maxID = 0;

            String sql = "SELECT MAX(ID) FROM Product";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery()){

            if (result.next()) {
                maxID = result.getInt(1);
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e)
        {
            System.out.println("Có lỗi xảy ra,Không thể lấy index Product: "+e.getMessage());
        }
        return maxID;
    }

    //Select tất cả product thuộc category
    public List<Product> getListProduct(int idCategory) {
        List<Product> list = new ArrayList<>();


            String sql = "SELECT * " +
                    "FROM Product p " +
                    "WHERE p.Category_ID = ? " +
                    " AND p.Statuss = 'ON' " ;

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,idCategory);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("ID");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("SoLuong");
                BigDecimal donGia = rs.getBigDecimal("DonGia");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,DanhMuc));
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }


}

