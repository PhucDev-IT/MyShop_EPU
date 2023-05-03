package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.controller.UserProfileController;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;



public class Product_Dao implements Dao_Interface<Product>{
    private final ConnectionPool jdbcUtil;
    private static Product_Dao instance;

    private Connection connection;

    public Product_Dao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public synchronized static Product_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new Product_Dao(jdbcUtil);
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = jdbcUtil.getConnection();
        }
    }

    private void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public synchronized boolean Insert(Product t) throws SQLException {
        boolean results = false;
        PreparedStatement preProduct = null;
        PreparedStatement preProSeller = null;
        try {
            openConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO Product(TenSP,Quantity,Price,MoTa,SrcImg,Category_ID)" +
                    " VALUES (?,?,?,?,?,?)";
            String sqlProductSeller = "INSERT INTO ProductSeller(Product_ID,Users_ID) " +
                    "VALUES (?,?)";
            preProduct = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

            preProduct.setString(1,t.getTenSP());
            preProduct.setInt(2,t.getQuantity());
            preProduct.setBigDecimal(3,t.getPrice());
            preProduct.setString(4,t.getMoTa());
            preProduct.setString(5,t.getSrcImg());
            preProduct.setInt(6,t.getCategory());
            preProduct.executeUpdate();

            int index ;

            try(ResultSet rs= preProduct.getGeneratedKeys()){
                if(rs.next()){
                    index = rs.getInt(1);
                }else {
                    throw new SQLException("Không thể thm dữ liệu bảng product");
                }

            }
            preProSeller = connection.prepareStatement(sqlProductSeller);
            preProSeller.setInt(1,index);
            preProSeller.setInt(2,t.getUser().getID());

            preProSeller.executeUpdate();

            connection.commit();
            results = true;
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("Roll Back: "+e.getMessage());
            }
        }finally {
            if(preProduct!=null)    preProduct.close();

            if(preProSeller!=null)  preProSeller.close();
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
            rs.close();
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
        String sql = "{call proc_getProductOfPages(?,?,?)}";

        try{
            openConnection();
            CallableStatement statement = connection.prepareCall(sql);
            statement.setInt(1, category);
            statement.setInt(2, startIndex);
            statement.setInt(3, maxProduct);
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
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,new User(0,nameSeller)));
            }
            rs.close();
            statement.close();
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
        Product product = new Product();

        String sql = "SELECT p.MaSP,p.TenSP,p.Price,p.Sold,p.TotalRevenue,p.SrcImg,u.Account_ID,u.FullName " +
                " FROM Product p JOIN ProductSeller ps" +
                " ON p.MaSP = ps.Product_ID" +
                " AND Activity = 'ON'" +
                " AND MaSP = ?" +
                " JOIN Users u ON ps.Users_ID = u.Account_ID" ;
        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                product.setID(rs.getInt("MaSP"));
                product.setTenSP(rs.getString("TenSP"));
                product.setPrice(rs.getBigDecimal("Price"));
                product.setSold(rs.getInt("sold"));
                product.setTotalRevenue(rs.getBigDecimal("TotalRevenue"));
                product.setSrcImg(rs.getString("SrcImg"));

                User u = new User();
                u.setID(rs.getInt("Account_ID"));
                u.setFullName(rs.getString("FullName"));

                product.setUser(u);
            }
            rs.close();

        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }

        return product;
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

        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getID());


            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    //được sử dụng cho các tác vụ đồng bộ hóa trên các giá trị kiểu nguyên (integer) trong các ứng dụng đa luồng (multithreaded).
    public synchronized List<Product> SearchProducts(String keyword, AtomicInteger lastIndex, int maxProduct)
    {
        List<Product> list = new ArrayList<>();

        String sql = "{call proc_SearchProduct(?,?,?)}";
        try(Connection connection = jdbcUtil.getConnection();
            CallableStatement statement = connection.prepareCall(sql)){
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

                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,new User(0,nameSeller)));
            }
            rs.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Không thể tìm sản phẩm: "+e.getMessage());
            return null;
        }

        return list;
    }

    //Tổng doanh thu người dùng đã bán
    public synchronized Object[] sumTotalOrder(User u) throws SQLException {
        System.out.println(u);
        Object[] obj = new Object[4];
        openConnection();
        String sql = "SELECT CAST(SUM(Sold) AS INT) AS sold , CAST(SUM(TotalRevenue) AS DECIMAL) as total " +
                "FROM Product p JOIN ProductSeller ps " +
                "ON p.MaSP = ps.Product_ID " +
                "AND ps.Users_ID = ?";

        String sqlToday = "select COUNT(od.Order_ID) as totalorder,CAST(SUM(TongTien) AS DECIMAL) as totalMoney FROM orders od " +
                "WHERE NgayLapHD = CONVERT(date, GETDATE()) " +
                "AND od.Order_ID IN( " +
                "SELECT Order_ID FROM OrderDetails odd JOIN ProductSeller ps " +
                "ON odd.Product_ID = ps.Product_ID " +
                "AND ps.Users_ID = ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql);
        PreparedStatement stmOderToday = connection.prepareStatement(sqlToday)){
            statement.setInt(1,u.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                obj[0] = rs.getInt("sold");
                obj[1] = rs.getBigDecimal("total");
            }


            stmOderToday.setInt(1,u.getID());
            ResultSet resultSet = stmOderToday.executeQuery();
            while (resultSet.next()){
                obj[2] = resultSet.getInt("totalorder");
                obj[3] = resultSet.getBigDecimal("totalMoney");
            }

            rs.close();
            resultSet.close();
        }finally {
            closeConnection();
        }
        return obj;

    }

    //Lấy các mặt hàng người dùng đang bán
    public synchronized List<Product> selectProductOfUser(User u,AtomicInteger lastIndex,int maxProduct) throws SQLException {
        List<Product> list = new ArrayList<>();
        openConnection();
        String sql = "{call proc_getProduct_ofSeller(?,?,?)}";
        try(CallableStatement statement = connection.prepareCall(sql)){
            statement.setInt(1,u.getID());
            statement.setInt(2,lastIndex.get());
            statement.setInt(3,maxProduct);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("totalrevenue");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,u));
            }
            statement.close();
            rs.close();
        }catch (SQLException e){
            System.out.println("Có lỗi: "+e.getMessage());
        }finally {
            closeConnection();
        }
        System.out.println("Success");
        return list;
    }

    //Tìm product đang bán
    public synchronized List<Product> SearchProductOfSeller(User u,String keyword, AtomicInteger lastIndex, int maxProduct)
    {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT Product.* FROM Product INNER JOIN ProductSeller " +
                "                ON Product.MaSP = ProductSeller.Product_ID" +
                "                 AND ProductSeller.Users_ID =?" +
                "                AND Product.TenSP LIKE ?" +
                "                 AND Product.Activity = 'ON'" +
                "                 ORDER BY MaSP" +
                "                 OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,u.getID());
            statement.setString(2,"%"+keyword+"%");
            statement.setInt(3,lastIndex.get());
            statement.setInt(4,maxProduct);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("MaSP");
                String TenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("Quantity");
                BigDecimal donGia = rs.getBigDecimal("Price");
                String MoTa = rs.getString("MoTa");
                String SrcImg = rs.getString("SrcImg");
                int DanhMuc = rs.getInt("Category_ID");
                int sold = rs.getInt("sold");
                BigDecimal totalrevenue = rs.getBigDecimal("totalrevenue");
                list.add(new Product(ID,TenSP,soLuong,donGia,MoTa,SrcImg,sold,totalrevenue,DanhMuc,u));
            }
            statement.close();
            rs.close();
        }catch (SQLException e)
        {
            System.out.println("Không thể tìm sản phẩm: "+e.getMessage());
        }
        return list;
    }

    //Lấy tất cả product có phân trang
    public List<Product> selectAllProductPage(AtomicInteger lastIndex) throws SQLException {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT p.MaSP,p.TenSP,p.Price,p.Sold,p.TotalRevenue,p.SrcImg,u.Account_ID,u.FullName " +
                " FROM Product p JOIN ProductSeller ps" +
                " ON p.MaSP = ps.Product_ID" +
                " AND Activity = 'ON'" +
                " JOIN Users u ON ps.Users_ID = u.Account_ID" +
                " ORDER BY TotalRevenue DESC" +
                " OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY  ";
        openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,lastIndex.get());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setID(rs.getInt("MaSP"));
                product.setTenSP(rs.getString("TenSP"));
                product.setPrice(rs.getBigDecimal("Price"));
                product.setSold(rs.getInt("sold"));
                product.setTotalRevenue(rs.getBigDecimal("TotalRevenue"));
                product.setSrcImg(rs.getString("SrcImg"));

                User u = new User();
                u.setID(rs.getInt("Account_ID"));
                u.setFullName(rs.getString("FullName"));

                product.setUser(u);

                list.add(product);
            }
            rs.close();
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }finally {
            closeConnection();
        }

        return list;
    }

    //Lấy top 3 sản phẩm bán chạy
    public Vector<Product> selectTopThreeProduct() throws SQLException {
        Vector<Product> list = new Vector<>();

        String sql = "select top(3) TenSP,Price,Sold,SrcImg from product " +
                "order by Sold desc";
        openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery()){

            while (rs.next()){
                Product product = new Product();
                product.setTenSP(rs.getString("TenSP"));
                product.setPrice(rs.getBigDecimal("Price"));
                product.setSold(rs.getInt("Sold"));
                product.setSrcImg(rs.getString("SrcImg"));
                list.add(product);
            }
        }finally {
            closeConnection();
        }
        return list;
    }

}

