package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.OrderDetails;
import com.epu.oop.myshop.model.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class OrderDetails_Dao implements Dao_Interface<OrderDetails> {

    private final ConnectionPool jdbcUtil;
    private static OrderDetails_Dao instance;

    private OrderDetails_Dao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public static OrderDetails_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new OrderDetails_Dao(jdbcUtil);
        }
        return instance;
    }

    @Override
    public boolean Insert(OrderDetails t) {
       return false;
    }

    @Override
    public List<OrderDetails> SelectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrderDetails SelectByID(OrderDetails t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int Update(OrderDetails t) {
        return 0;
    }

    @Override
    public int Delete(OrderDetails t) {
        // TODO Auto-generated method stub
        return 0;
    }

    //----------------------- Đếm số lượng hàng A đã được bán và tổng tiền  ----------------------------------------------
    public Object[] SoLuongVaTongTienDaBan(Product t) {
        Object[] result = new Object[2];
            String sql = "SELECT CAST(SUM(quantity) AS float),SUM(quantity*price) FROM OrderDetails WHERE Product_ID=?";
        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result[0] = (float) rs.getInt(1);
                result[1] = rs.getBigDecimal(2);
            }

        } catch (SQLException e) {
            System.out.println("Không thể truy vấn số lượng đã bán và tổng tiên:\n"+e.getMessage());
            result[0] = null;
            result[1] = BigDecimal.ZERO;
        }
        return result;
    }


    //Chi tiết hóa đơn, sử dụng transaction
    public boolean insertVoucher(OrderDetails t, int IDHoaDon){
        int results = 0;

        String sql = "INSERT INTO OrderDetails(Product_ID,HoaDon_ID,Quantity,Price)" +
                " VALUES (?,?,?,?)";
        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getProduct().getID());
            statement.setInt(2,IDHoaDon);
            statement.setFloat(3,t.getQuantity());
            statement.setBigDecimal(4,new BigDecimal(t.getPrice()+""));

            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Thêm thành công bảng chi tiết hóa đơn");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results>0;
    }

}