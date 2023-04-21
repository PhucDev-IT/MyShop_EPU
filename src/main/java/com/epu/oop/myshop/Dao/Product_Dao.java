package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

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
            statement.setFloat(2,t.getQuantity());
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
                float soLuong = rs.getFloat("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc));
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
        String sql = "SELECT * FROM Product WHERE Category_ID = ?" +
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
                float soLuong = rs.getFloat("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc));
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
                    " WHERE MaSP=? " +
                    "AND p.Activity = 'ON' ";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                products = new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc);
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
                        + " Quantity=?,"
                        + " Price=?,"
                        + " MoTa=?,"
                        + " SrcImg=?,"
                        + "Activity=?"
                        + " WHERE MaSP = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
               statement.setString(1,t.getTenSP());
               statement.setFloat(2,t.getQuantity());
               statement.setBigDecimal(3,t.getPrice());
               statement.setString(4,t.getMoTa());
               statement.setString(5,t.getSrcImg());
               statement.setString(6,t.getActivity());
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

    public List<Product> SearchProducts(String keyword)
    {
        List<Product> list = new ArrayList<>();

            String sql = "SELECT * FROM Product" +
                    " WHERE TenSP LIKE ? " +
                    "AND Activity = 'ON'";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,"%"+keyword+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc));
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
                    " ON u.Account_ID = ps.Users_ID" +
                    " AND ps.Product_ID = ?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,p.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
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
        }catch (SQLException e)
        {
            System.out.println("Có lỗi xảy ra,không lấy được thông tin người bán: "+e.getMessage());
        }
        return user;
    }

    //Select top 3 sản phẩm bán nhiều nhất

    public int IndexNewInsert()
    {
        int maxID = 0;

            String sql = "SELECT MAX(MaSP) FROM Product";
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
                    " AND p.Activity = 'ON' " ;

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,idCategory);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                float soLuong = rs.getFloat("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                float sold = rs.getFloat("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("TotalRevenue");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc));
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

