package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order_Dao implements Dao_Interface<Order> {
    private final ConnectionPool jdbcUtil;
    private static Order_Dao instance;

    private Connection connection;

    public Order_Dao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public synchronized static Order_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new Order_Dao(jdbcUtil);
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
    public boolean Insert(Order t) {

        return false;
    }


    //Thanh toán hóa đơn băng ngân hàng
    public  boolean OrderDetailsPayByBank(Order hoaDon, List<itemCartModel> list, PaymentHistory paymentHistory) throws SQLException {
        PreparedStatement preOrder = null;
        PreparedStatement preDetails = null;
        PreparedStatement prePaymentHistory = null;

        try {
            openConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction
            String sqlOrder = null;
            if(hoaDon.getVoucher()!=null) {
                sqlOrder = "INSERT INTO Orders(NgayLapHD,TongTien,ThanhTien,Users_ID,MaVoucher)" +
                        " VALUES (?,?,?,?,?)";
                preOrder = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
                preOrder.setString(5,hoaDon.getVoucher().getMaVoucher());
            }else{
                sqlOrder = "INSERT INTO orders(NgayLapHD,TongTien,ThanhTien,Users_ID)" +
                        " VALUES (?,?,?,?)";
                //để thiết lập cho PreparedStatement trả về giá trị của khóa chính được tạo ra
                preOrder = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
            }

            String sqlOrderDtails = "INSERT INTO orderDetails(Product_ID,Order_ID,Quantity,Price)" +
                    " VALUES (?,?,?,?)";

            String sqlPayment = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID)" +
                    " VALUES (?,?,?,?,?,?)";



            preOrder.setDate(1, hoaDon.getNgayLapHD());
            preOrder.setBigDecimal(2, hoaDon.getTongTien());
            preOrder.setBigDecimal(3, hoaDon.getThanhTien());
            preOrder.setInt(4, hoaDon.getUser().getID());

            preOrder.executeUpdate();

            int hoaDonId;
            //phương thức getGeneratedKeys() để lấy giá trị ID của bản ghi vừa được chèn vào.
            try (ResultSet generatedKeys = preOrder.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hoaDonId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            preDetails = connection.prepareStatement(sqlOrderDtails);
            for(itemCartModel it:list)
            {
                preDetails.setInt(1,it.getProduct().getID());
                preDetails.setInt(2,hoaDonId);
                preDetails.setFloat(3,it.getQuantity());
                preDetails.setBigDecimal(4,new BigDecimal(it.getProduct().getPrice()+""));
                preDetails.executeUpdate();
            }



            prePaymentHistory = connection.prepareStatement(sqlPayment);
            prePaymentHistory.setString(1, paymentHistory.getTenGiaoDich());
            prePaymentHistory.setString(2, paymentHistory.getNoiDung());
            prePaymentHistory.setBigDecimal(3, paymentHistory.getSoTien());
            prePaymentHistory.setDate(4, paymentHistory.getNgayGiaoDich());
            prePaymentHistory.setString(5, paymentHistory.getImgSrcIcon());
            prePaymentHistory.setInt(6, paymentHistory.getUser().getID());

            prePaymentHistory.executeUpdate();

            connection.commit();
            System.out.println("Theem hóa đơn thành công");
        } catch(SQLException ex){
                if (connection != null) {
                        connection.rollback();
                        System.out.println("Rolled back.");
                }
           // System.out.println("Lỗi: "+ ex.getMessage());
            ex.printStackTrace();
            return false;
            } finally{

                    if (preOrder != null) {
                        preOrder.close();
                    }

                    if (preDetails != null) {
                        preDetails.close();
                    }
                    if(prePaymentHistory!=null){
                        prePaymentHistory.close();
                    }
                    connection.setAutoCommit(true);
                closeConnection();
            }
        return true;
    }

        //Hóa đơn có tại nhà
    public boolean OrderDetailsPayAtHome(Order hoaDon,List<itemCartModel> list) throws SQLException {
        PreparedStatement orderDetails = null;
        PreparedStatement order = null;

        int checkOrder = 0;
        int checkDetails=0;

        try {
           openConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction
            String sqlOrder = null;

            if(hoaDon.getVoucher()!=null) {
                sqlOrder = "INSERT INTO orders(NgayLapHD,TongTien,ThanhTien,Users_ID,MaVoucher)" +
                        " VALUES (?,?,?,?,?)";
                order = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
                order.setString(5,hoaDon.getVoucher().getMaVoucher());
            }else{
                sqlOrder = "INSERT INTO orders(NgayLapHD,TongTien,ThanhTien,Users_ID)" +
                        " VALUES (?,?,?,?)";
                order = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
            }

            String sqlOrderDtails = "INSERT INTO orderDetails(Product_ID,Order_ID,Quantity,Price)" +
                    " VALUES (?,?,?,?)";

            order.setDate(1,hoaDon.getNgayLapHD());
            order.setBigDecimal(2,hoaDon.getTongTien());
            order.setBigDecimal(3,hoaDon.getThanhTien());
            order.setInt(4,hoaDon.getUser().getID());

            order.executeUpdate();

            int hoaDonId;
            //phương thức getGeneratedKeys() để lấy giá trị ID của bản ghi vừa được chèn vào.
            try (ResultSet generatedKeys = order.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hoaDonId = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            for(itemCartModel it:list)
            {
                orderDetails = connection.prepareStatement(sqlOrderDtails);
                orderDetails.setInt(1,it.getProduct().getID());
                orderDetails.setInt(2,hoaDonId);
                orderDetails.setFloat(3,it.getQuantity());
                orderDetails.setBigDecimal(4,new BigDecimal(it.getProduct().getPrice()+""));
                orderDetails.executeUpdate();
            }

            connection.commit();
            System.out.println("Thành công");
        } catch (SQLException ex) {
            if (connection != null) {
                connection.rollback();
                System.out.println("Rolled back.");
            }
            System.out.println("Lỗi: "+ ex.getMessage());
            return false;
        } finally {
                if (order != null) {
                    order.close();
                }

                if (orderDetails != null) {
                    orderDetails.close();
                }

                connection.setAutoCommit(true);
                closeConnection();
        }
        return true;
    }

    @Override
    public List<Order> SelectAll() throws SQLException {
        List<Order> list = new ArrayList<>();

            String sql = "SELECT * FROM orders";
        openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                int ID = rs.getInt("Order_ID");
                Date NgayLapHD = rs.getDate("NgayLapHD");
                BigDecimal TongTien = rs.getBigDecimal("TongTien");
                String MaVoucher = rs.getString("MaVoucher");
                BigDecimal ThanhTien = rs.getBigDecimal("ThanhTien");
                int Users_ID = rs.getInt("Users_ID");

                list.add(new Order(ID,NgayLapHD,TongTien,new VoucherModel(MaVoucher),ThanhTien,new User(Users_ID)));
            }
            statement.close();
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public Order SelectByID(Order t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int Update(Order t) {
        return 1;
    }

    @Override
    public int Delete(Order t) {
        // TODO Auto-generated method stub
        return 0;
    }



    //-------------------------- lỊCH SỬ MUA HÀNG ---------------------------------------------------
    public synchronized List<Object[]> getPurchaseProducts(User u, AtomicInteger lastIndex,int maxResult) throws SQLException {
        List<Object[]> results = new ArrayList<>();


            String sql = "SELECT p.TenSP,p.SrcImg,cthd.Quantity,cthd.Price,orders.NgayLapHD,Users.FullName " +
                    "FROM Product p INNER JOIN OrderDetails cthd " +
                    "ON p.MaSP = cthd.Product_ID " +
                    "INNER JOIN orders ON cthd.Order_ID = orders.Order_ID " +
                    "JOIN ProductSeller ON p.MaSP = ProductSeller.Product_ID " +
                    "JOIN Users ON ProductSeller.Users_ID = Users.Account_ID " +
                    "AND orders.Users_ID = ?" +
                    " ORDER BY orders.NgayLapHD DESC" +
                    " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            openConnection();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,u.getID());
            statement.setInt(2,lastIndex.get());
            statement.setInt(3,maxResult);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Object[] obj = new Object[4];
                Product product = new Product();
                product.setTenSP(rs.getString("TenSP"));
                product.setSrcImg(rs.getString("SrcImg"));
                obj[0] = product;
                OrderDetails cthd = new OrderDetails();
                cthd.setQuantity(rs.getFloat("Quantity"));
                cthd.setPrice(rs.getBigDecimal("Price"));
                obj[1] = cthd;
                obj[2] = rs.getDate("NgayLapHD");
                obj[3] = rs.getString("FullName");
                results.add(obj);
            }
            rs.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return results;
    }

}
