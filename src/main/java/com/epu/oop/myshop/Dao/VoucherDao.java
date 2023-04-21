package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.VoucherModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDao implements Dao_Interface<VoucherModel> {

    private static VoucherDao instance;

    public static VoucherDao getInstance() {
        if (instance == null) {
            instance = new VoucherDao();
        }
        return instance;
    }
    @Override
    public boolean Insert(VoucherModel voucherModel) {
        int results = 0;
        String sql = "INSERT INTO Voucher(MaVoucher,TiLeGiamGia,SoLuong,NoiDung,NgayThem,NgayKetThuc,ImgVoucher,SotienGiamGia)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,voucherModel.getMaVoucher());
            statement.setFloat(2,voucherModel.getTiLeGiamGia());
            statement.setInt(3,voucherModel.getSoLuong());
            statement.setString(4,voucherModel.getNoiDung());
            statement.setDate(5,voucherModel.getNgayBatDau());
            statement.setDate(6,voucherModel.getNgayKetThuc());
            statement.setString(7,voucherModel.getImgVoucher());
            statement.setBigDecimal(8,voucherModel.getSoTienGiam());
            //Bước 3:Thực thi câu lệnh
            results =  statement.executeUpdate();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results>0;
    }

    @Override
    public List<VoucherModel> SelectAll() {
        List<VoucherModel> list = new ArrayList<>();

        String sql = "SELECT * FROM Voucher";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String MaVoucher = rs.getString("MaVoucher");
                float TiLeGiamGia = rs.getFloat("TiLeGiamGia");
                int SoLuong = rs.getInt("SoLuong");
                BigDecimal SotienGiamGia = rs.getBigDecimal("SotienGiamGia");
                String NoiDung = rs.getString("NoiDung");
                String ImgVoucher = rs.getString("ImgVoucher");
                Date NgayThem = rs.getDate("NgayThem");
                Date NgayKetThuc = rs.getDate("NgayKetThuc");

                list.add(new VoucherModel(MaVoucher,TiLeGiamGia,SotienGiamGia,SoLuong,NoiDung,ImgVoucher,NgayThem,NgayKetThuc));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public VoucherModel SelectByID(VoucherModel voucherModel) {
        return null;
    }

    @Override
    public int Update(VoucherModel voucherModel) {
        return 0;
    }

    @Override
    public int Delete(VoucherModel voucherModel) {
        return 0;
    }

    //Select tất cả voucher trừ những voucher người dùng đã sử dụng khi còn thời hạn
    public List<VoucherModel> getVoucherConTime(int IDUser) {
        List<VoucherModel> list = new ArrayList<>();

        String sql = "select * from voucher " +
                "where NgayKetThuc >= GETDATE() " +
                "AND NgayThem <=GETDATE() " +
                "AND MaVoucher  NOT IN (SELECT MaVoucher FROM orders WHERE Users_ID = ?) " +
                "AND MaVoucher NOT IN (SELECT MaVoucher FROM VoucherUser)" +
                "OR MaVoucher IN (SELECT Mavoucher FROM VoucherUser WHERE ID_User = ?)";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,IDUser);
            statement.setInt(2,IDUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                String MaVoucher = rs.getString("MaVoucher");
                float TiLeGiamGia = rs.getFloat("TiLeGiamGia");
                int SoLuong = rs.getInt("SoLuong");
                BigDecimal SotienGiamGia = rs.getBigDecimal("SotienGiamGia");
                String NoiDung = rs.getString("NoiDung");
                String ImgVoucher = rs.getString("ImgVoucher");
                Date NgayThem = rs.getDate("NgayThem");
                Date NgayKetThuc = rs.getDate("NgayKetThuc");

                list.add(new VoucherModel(MaVoucher,TiLeGiamGia,SotienGiamGia,SoLuong,NoiDung,ImgVoucher,NgayThem,NgayKetThuc));
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Tặng voucher cho ngươời dùng
    public int GiftVoucher(VoucherModel voucherModel,String email) {
        int results = 0;
        String sql = "INSERT INTO VoucherUser(MaVoucher,ID_User)"+
                " VALUES (?,(SELECT Account_ID FROM Users WHERE Email = ?))";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,voucherModel.getMaVoucher());
            statement.setString(2,email);

            //Bước 3:Thực thi câu lệnh
            results =  statement.executeUpdate();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
           e.printStackTrace();
        }
        return results;
    }

    //Kiểm tra mã voucher đã tồn tại hay chưa
    public int checkMaVoucher(String ID){
        int result = 0;

        String sql = "SELECT Count(*) FROM Voucher WHERE Mavoucher = ?";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,ID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
               result = rs.getInt(1);
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
