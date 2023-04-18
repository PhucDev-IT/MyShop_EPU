package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.PaymentHistory;
import com.epu.oop.myshop.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistory_Dao implements Dao_Interface<PaymentHistory>{
    private static PaymentHistory_Dao instance;

    public static PaymentHistory_Dao getInstance() {
        if (instance == null) {
            instance = new PaymentHistory_Dao();
        }
        return instance;
    }
    //Dành cho chuyển tiền
    @Override
    public int Insert(PaymentHistory paymentHistory) {
        int results = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

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
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


    //Thanh toán cho myshop, mua hàng
    public int PaymentMyShop(PaymentHistory paymentHistory) {
        int results = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

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
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
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
    public List<PaymentHistory> listPaymentHistory(Account a){
        List<PaymentHistory> list = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT pm.* from PaymentHistory pm " +
                    "WHERE Users_ID = ? or Account_ID = ? " +
                    "Order by NgayGiaoDich DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,a.getID());
            statement.setInt(2,a.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int ID = rs.getInt("ID");
                String tenGiaoDich = rs.getString("TenGiaoDich");
                String NoiDung = rs.getString("NoiDung");
                BigDecimal soTien = rs.getBigDecimal("SoTien");
                Date ngayGiaoDich = rs.getDate("NgayGiaoDich");
                String src = rs.getString("SrcImgIcon");
                int User_ID = rs.getInt("Users_ID");
                int Account_ID = rs.getInt("Account_ID");
                list.add(new PaymentHistory(ID,tenGiaoDich,NoiDung,soTien,ngayGiaoDich,src,new User(User_ID),new Account(Account_ID)));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
