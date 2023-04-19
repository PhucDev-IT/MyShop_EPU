package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.CTHoaDon;
import com.epu.oop.myshop.model.Product;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class CTHoaDon_Dao implements Dao_Interface<CTHoaDon> {

    private static CTHoaDon_Dao instance;

    public static CTHoaDon_Dao getInstance() {
        if (instance == null) {
            instance = new CTHoaDon_Dao();
        }
        return instance;
    }

    @Override
    public int Insert(CTHoaDon t) {
        int results = 0;

            int index = HoaDon_Dao.getInstance().IndexNewInsert();

            String sql = "INSERT INTO CTHoaDon(Product_ID,HoaDon_ID,Quantity,Price)" +
                    " VALUES (?,?,?,?)";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getProduct().getID());
            statement.setInt(2,index);
            statement.setFloat(3,t.getQuantity());
            statement.setBigDecimal(4,new BigDecimal(t.getPrice()+""));

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
    public List<CTHoaDon> SelectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CTHoaDon SelectByID(CTHoaDon t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int Update(CTHoaDon t) {
        return 0;
    }

    @Override
    public int Delete(CTHoaDon t) {
        // TODO Auto-generated method stub
        return 0;
    }

    //----------------------- Đếm số lượng hàng A đã được bán và tổng tiền  ----------------------------------------------
    public Object[] SoLuongVaTongTienDaBan(Product t) {
        Object[] result = new Object[2];
            String sql = "SELECT CAST(SUM(quantity) AS float),SUM(quantity*price) FROM CTHoaDon WHERE Product_ID=?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, t.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result[0] = (float) rs.getInt(1);
                result[1] = rs.getBigDecimal(2);
            }

            statement.close();
            JDBCUtil.CloseConnection(connection);
        } catch (SQLException e) {
            System.out.println("Không thể truy vấn số lượng đã bán và tổng tiên:\n"+e.getMessage());
            result[0] = null;
            result[1] = BigDecimal.ZERO;
        }
        return result;
    }


    //Chi tiết hóa đơn, sử dụng transaction
    public boolean insertVoucher(CTHoaDon t, int IDHoaDon){
        int results = 0;

        String sql = "INSERT INTO CTHoaDon(Product_ID,HoaDon_ID,Quantity,Price)" +
                " VALUES (?,?,?,?)";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,t.getProduct().getID());
            statement.setInt(2,IDHoaDon);
            statement.setFloat(3,t.getQuantity());
            statement.setBigDecimal(4,new BigDecimal(t.getPrice()+""));

            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Thêm thành công bảng chi tiết hóa đơn");
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results>0;
    }

}