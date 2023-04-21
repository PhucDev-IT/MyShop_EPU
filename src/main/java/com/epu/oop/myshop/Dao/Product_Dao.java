package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.rowset.*;


public class Product_Dao implements Dao_Interface<Product>{
    private static Product_Dao instance;

    private Connection connection;

    public synchronized static Product_Dao getInstance() {
        if (instance == null) {
            instance = new Product_Dao();
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = JDBCUtil.getConnection();
        }
    }

    private void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public boolean Insert(Product t) throws SQLException {
        boolean results = false;
        try {
            openConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Product(TenSP,Quantity,Price,MoTa,SrcImg,Category_ID)" +
                    " VALUES (?,?,?,?,?,?)";
            String sqlProductSeller = "INSERT INTO ProductSeller (Product_ID,Users_ID) " +
                    "VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1,t.getTenSP());
            statement.setInt(2,t.getQuantity());
            statement.setBigDecimal(3,t.getPrice());
            statement.setString(4,t.getMoTa());
            statement.setString(5,t.getSrcImg());
            statement.setInt(6,t.getCategory());
            statement.executeUpdate();

            int index ;

            try(ResultSet rs= statement.getGeneratedKeys()){
                if(rs.next()){
                    index = rs.getInt(1);
                }else {
                    throw new SQLException("Không thể thm dữ liệu bảng product");
                }
            }
            statement = connection.prepareStatement(sqlProductSeller);
            statement.setInt(1,index);
            statement.setInt(2,t.getUser().getID());

            statement.executeUpdate();

            connection.commit();
            statement.close();
            results = true;
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
            }
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
        return results;
    }


    @Override
    public List<Product> SelectAll() throws SQLException {
        List<Product> list = new ArrayList<>();

            String sql = "SELECT * FROM Product" +
                    " WHERE Activity = 'ON' ";
        openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,null));
            }
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }finally {
            closeConnection();
        }

        return list;
    }

    //Phân trang, truy vấn dữ liệu luưu vào bộ nhớ đệm cachedrowset
    //Lấy số lượng trang cho danh mục
    public int getNumbersPages(int idCate) throws SQLException {
        int result = 0;
        String sql = "SELECT COUNT(*) as total FROM Product WHERE Category_ID = ?";
        openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,idCate);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
              result = rs.getInt("total");
            }
            statement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return result;
    }
    public synchronized List<Product> getProductsByPage(int category,int page,int maxProduct) {
        List<Product> list = new ArrayList<>();
        int startIndex = (page-1)*maxProduct;
        String sql = "SELECT p.*, u.FullName FROM Product p JOIN ProductSeller ps " +
                " ON p.MaSP = ps.Product_ID " +
                " JOIN Users u ON ps.Users_ID = u.Account_ID" +
                " WHERE Category_ID = ? " +
                " AND p.Activity = 'ON' " +
                " ORDER BY MaSP" +
                "  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try{
            openConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, category);
            pstmt.setInt(2, startIndex);
            pstmt.setInt(3, maxProduct);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                String nameSeller = rs.getString("FullName");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,new User(nameSeller)));
            }

            pstmt.close();
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra: "+e.getMessage());
        }finally {
            try{
                closeConnection();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }


    @Override
    public Product SelectByID(Product t) {
        Product products = null;

            String sql = "SELECT * FROM Product " +
                    " WHERE MaSP=? " +
                    "AND p.Activity = 'ON' ";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                products = new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,null);
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
    public int Update(Product t) throws SQLException {
        int result = 0;

                String sql = "UPDATE Product"
                        + " SET "
                        + "TenSP =?, "
                        + " Quantity=?,"
                        + " Price=?,"
                        + " MoTa=?,"
                        + " SrcImg=?,"
                        + "Activity=?"
                        + " WHERE MaSP = ?";
        openConnection();
        PreparedStatement statement = null;
        try{
            connection.setAutoCommit(false);
             statement = connection.prepareStatement(sql);
               statement.setString(1,t.getTenSP());
               statement.setInt(2,t.getQuantity());
               statement.setBigDecimal(3,t.getPrice());
               statement.setString(4,t.getMoTa());
               statement.setString(5,t.getSrcImg());
               statement.setString(6,t.getActivity());
               statement.setInt(7,t.getID());

               result = statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if(connection!=null){
                connection.rollback();
            }
            e.printStackTrace();
        }finally {
            if(statement!=null){
                statement.close();
            }
            connection.setAutoCommit(true);
            closeConnection();
        }
        return result;
    }

    @Override
    public int Delete(Product t) {
        int results = 0;

            String sql = "DELETE FROM Product" +
                    " WHERE MaSP = ?";

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

    //được sử dụng cho các tác vụ đồng bộ hóa trên các giá trị kiểu nguyên (integer) trong các ứng dụng đa luồng (multithreaded).
    public synchronized List<Product> SearchProducts(String keyword, AtomicInteger lastIndex, int maxProduct)
    {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.*, u.FullName FROM Product p JOIN ProductSeller ps " +
                " ON p.MaSP = ps.Product_ID " +
                " JOIN Users u ON ps.Users_ID = u.Account_ID" +
                " WHERE p.TenSP LIKE ? " +
                " AND p.Activity = 'ON' " +
                " ORDER BY MaSP" +
                "  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,"%"+keyword+"%");
            statement.setInt(2,lastIndex.get());
            statement.setInt(3,maxProduct);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                String nameSeller = rs.getString("FullName");

                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,new User(nameSeller)));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
            lastIndex.set(lastIndex.get() + 15); // thay đổi giá trị của biến lastIndex
        }catch (SQLException e)
        {
            System.out.println("Không thể tìm sản phẩm: "+e.getMessage());
        }
        return list;
    }



}

