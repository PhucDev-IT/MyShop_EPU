package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.PaymentHistory;
import com.epu.oop.myshop.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentHistory_Dao implements Dao_Interface<PaymentHistory>{
    private final ConnectionPool jdbcUtil;
    private static PaymentHistory_Dao instance;

    private PaymentHistory_Dao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public static PaymentHistory_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new PaymentHistory_Dao(jdbcUtil);
        }
        return instance;
    }
    //Dành cho chuyển tiền
    @Override
    public boolean Insert(PaymentHistory paymentHistory) {
        int results = 0;
        try {
            Connection connection = jdbcUtil.getConnection();

            String sql = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID,Account_ID)" +
                    " VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,paymentHistory.getTenGiaoDich());
            statement.setString(2,paymentHistory.getNoiDung());
            statement.setBigDecimal(3,paymentHistory.getSoTien());
            statement.setDate(4,paymentHistory.getNgayGiaoDich());
            statement.setString(5,paymentHistory.getImgSrcIcon());
            statement.setInt(6,paymentHistory.getUser().getID());
            statement.setInt(7,paymentHistory.getAccount().getID());


            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Rút tiền: Có "+results +" thay đổi");
            statement.close();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results>0;
    }


    //Thanh toán cho myshop, mua hàng,nạp tiền
    public int PaymentMyShop(PaymentHistory paymentHistory) throws SQLException {
        int results = 0;
        Connection connection = null;
        try {
             connection = jdbcUtil.getConnection();
             connection.setAutoCommit(false);
            String sql = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID)" +
                    " VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,paymentHistory.getTenGiaoDich());
            statement.setString(2,paymentHistory.getNoiDung());
            statement.setBigDecimal(3,paymentHistory.getSoTien());
            statement.setDate(4,paymentHistory.getNgayGiaoDich());
            statement.setString(5,paymentHistory.getImgSrcIcon());
            statement.setInt(6,paymentHistory.getUser().getID());

            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Rút tiền: Có "+results +" thay đổi");
            statement.close();
            connection.commit();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("Roll back");
            }
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return results;
    }

    @Override
    public List<PaymentHistory> SelectAll() {
        return null;
    }
    @Override
    public PaymentHistory SelectByID(PaymentHistory paymentHistory) {
        return null;
    }

    @Override
    public int Update(PaymentHistory paymentHistory) {
        return 0;
    }

    @Override
    public int Delete(PaymentHistory paymentHistory) {
        return 0;
    }



    //Lịch sử giao dịch
    public List<Object[]> listPaymentHistory(Account a, AtomicInteger lastindex,int maxressul){
        List<Object[]> list = new ArrayList<>();
        try {
            Connection connection = jdbcUtil.getConnection();
            String sql = "{call proc_PaymentHistory(?,?,?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setInt(1,a.getID());
            statement.setInt(2,lastindex.get());
            statement.setInt(3,maxressul);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Object[] obj = new Object[2];
                int ID = rs.getInt("ID");
                String tenGiaoDich = rs.getString("TenGiaoDich");
                String NoiDung = rs.getString("NoiDung");
                BigDecimal soTien = rs.getBigDecimal("SoTien");
                Date ngayGiaoDich = rs.getDate("NgayGiaoDich");
                String src = rs.getString("SrcImgIcon");
                int User_ID = rs.getInt("Users_ID");
                int Account_ID = rs.getInt("Account_ID");
                String nguoiNhan = rs.getString("NguoiNhan");

                obj[0] = new PaymentHistory(ID,tenGiaoDich,NoiDung,soTien,ngayGiaoDich,src,new User(User_ID,""),new Account(Account_ID));
                obj[1] = nguoiNhan;
                list.add(obj);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
