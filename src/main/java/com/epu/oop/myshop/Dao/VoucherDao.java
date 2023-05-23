package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.User;
import com.epu.oop.myshop.model.VoucherModel;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDao implements Dao_Interface<VoucherModel> {

    private final ConnectionPool jdbcUtil;
    private static VoucherDao instance;

    private VoucherDao(ConnectionPool jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public static VoucherDao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new VoucherDao(jdbcUtil);
        }
        return instance;
    }
    @Override
    public boolean Insert(VoucherModel voucherModel) {
        int results = 0;
        String sql = "INSERT INTO Voucher(MaVoucher,TiLeGiamGia,SoLuong,NoiDung,NgayBatDau,NgayKetThuc,ImgVoucher,SotienGiamGia)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        try(Connection connection = jdbcUtil.getConnection();
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

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results>0;
    }

    @Override
    public List<VoucherModel> SelectAll() {
        List<VoucherModel> list = new ArrayList<>();

        String sql = "SELECT Voucher.*, Users.Email" +
                " FROM Voucher" +
                " LEFT JOIN VoucherUser ON Voucher.MaVoucher = VoucherUser.MaVoucher" +
                " LEFT JOIN Users ON VoucherUser.ID_User = Users.Account_ID" +
                " WHERE SoLuong>0";


        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String MaVoucher = rs.getString("MaVoucher");
                float TiLeGiamGia = rs.getFloat("TiLeGiamGia");
                int SoLuong = rs.getInt("SoLuong");
                BigDecimal SotienGiamGia = rs.getBigDecimal("SotienGiamGia");
                String NoiDung = rs.getString("NoiDung");
                String ImgVoucher = rs.getString("ImgVoucher");
                Date NgayThem = rs.getDate("NgayBatDau");
                Date NgayKetThuc = rs.getDate("NgayKetThuc");
                String email = rs.getString("Email");
                list.add(new VoucherModel(MaVoucher,TiLeGiamGia,SotienGiamGia,SoLuong,NoiDung,ImgVoucher,NgayThem,NgayKetThuc,new User(email)));
            }

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
    public int Update(VoucherModel voucherModel) throws SQLException {
        String sql = "UPDATE Voucher" +
                " SET TileGiamGia = ? ," +
                " SoTienGiamGia = ? ," +
                " SoLuong = ?, " +
                " NoiDung = ?," +
                " ImgVoucher = ?," +
                " NgayBatDau = ?," +
                " NgayKetThuc = ? " +
                " WHERE MaVoucher = ?";
        Connection connection = null;
        try{
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1,voucherModel.getTiLeGiamGia());
            statement.setBigDecimal(2,voucherModel.getSoTienGiam());
            statement.setInt(3,voucherModel.getSoLuong());
            statement.setString(4,voucherModel.getNoiDung());
            statement.setString(5,voucherModel.getImgVoucher());
            statement.setDate(6,voucherModel.getNgayBatDau());
            statement.setDate(7,voucherModel.getNgayKetThuc());
            statement.setString(8,voucherModel.getMaVoucher());
            statement.executeUpdate();

            connection.commit();
            statement.close();

        } catch (SQLException e) {
            if(connection!=null)
            {
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
            }
            return 0;
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }

        return 1;
    }

    @Override
    public int Delete(VoucherModel voucherModel)  {
        int resul = 0;
        String sql = "BEGIN TRANSACTION " +
                " IF EXISTS(SELECT * FROM orders WHERE MaVoucher = ?)" +
                " BEGIN" +
                "    UPDATE Voucher SET SoLuong = -1 WHERE MaVoucher = ?" +
                " END" +
                " IF EXISTS (SELECT * FROM VoucherUser WHERE MaVoucher = ?)" +
                "   BEGIN" +
                "       UPDATE Voucher SET SoLuong = -1 WHERE MaVoucher = ?" +
                "   END" +
                " ELSE" +
                " BEGIN" +
                "    DELETE FROM Voucher WHERE MaVoucher = ?" +
                " END" +
                " COMMIT TRANSACTION";
        try{
            Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,voucherModel.getMaVoucher());
            statement.setString(2,voucherModel.getMaVoucher());
            statement.setString(3,voucherModel.getMaVoucher());
            statement.setString(4,voucherModel.getMaVoucher());
            statement.setString(5,voucherModel.getMaVoucher());
            resul = statement.executeUpdate();

            statement.close();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return resul;
    }

    //Select tất cả voucher trừ những voucher người dùng đã sử dụng khi còn thời hạn (CHO NGUỜI DÙNG)
    public List<VoucherModel> getVoucherConTime(int IDUser) {
        List<VoucherModel> list = new ArrayList<>();

        String sql = "SELECT * FROM voucher " +
                "WHERE NgayKetThuc >= GETDATE() " +
                "AND NgayBatDau <= GETDATE()" +
                " AND SoLuong > 0 " +
                "AND ( MaVoucher NOT IN (SELECT MaVoucher FROM orders " +
                "                       WHERE Users_ID = ?" +
                "                       AND MaVoucher IS NOT NULL) " +
                " OR MaVoucher IN (SELECT Mavoucher FROM VoucherUser WHERE ID_User = ?))";

        try(Connection connection = jdbcUtil.getConnection();
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
                Date NgayThem = rs.getDate("NgayBatDau");
                Date NgayKetThuc = rs.getDate("NgayKetThuc");

                list.add(new VoucherModel(MaVoucher,TiLeGiamGia,SotienGiamGia,SoLuong,NoiDung,ImgVoucher,NgayThem,NgayKetThuc));
            }
            rs.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Tặng voucher cho ngươời dùng
    public int GiftVoucher(VoucherModel voucherModel,String email) throws SQLException {

        String sqluser = "INSERT INTO VoucherUser(MaVoucher,ID_User)"+
                " VALUES (?,(SELECT Account_ID FROM Users WHERE Email = ?))";

        String sql = "INSERT INTO Voucher(MaVoucher,TiLeGiamGia,SoLuong,NoiDung,NgayBatDau,NgayKetThuc,ImgVoucher,SotienGiamGia)" +
                " VALUES (?,?,?,?,?,?,?,?)";

        Connection connection = null;
        try{
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement1 = connection.prepareStatement(sql);
            statement1.setString(1,voucherModel.getMaVoucher());
            statement1.setFloat(2,voucherModel.getTiLeGiamGia());
            statement1.setInt(3,voucherModel.getSoLuong());
            statement1.setString(4,voucherModel.getNoiDung());
            statement1.setDate(5,voucherModel.getNgayBatDau());
            statement1.setDate(6,voucherModel.getNgayKetThuc());
            statement1.setString(7,voucherModel.getImgVoucher());
            statement1.setBigDecimal(8,voucherModel.getSoTienGiam());
             statement1.executeUpdate();

            PreparedStatement statement = connection.prepareStatement(sqluser);
            statement.setString(1,voucherModel.getMaVoucher());
            statement.setString(2,email);

            statement.executeUpdate();
            connection.commit();
            statement.close();
            statement1.close();

        }catch (SQLException e) {
            if(connection!=null)
            {
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
            }
            return 0;
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return 1;
    }

    //Kiểm tra mã voucher đã tồn tại hay chưa
    public int checkMaVoucher(String ID){
        int result = 0;

        String sql = "SELECT Count(*) FROM Voucher WHERE Mavoucher = ?";

        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,ID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
               result = rs.getInt(1);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //Laay tat ca voucher dang con thoi gian (admin)
    public List<VoucherModel> selectAllVoucherLeftTime(){
        List<VoucherModel> list = new ArrayList<>();

        String sql = "SELECT Voucher.*, Users.Email" +
                " FROM Voucher" +
                " LEFT JOIN VoucherUser ON Voucher.MaVoucher = VoucherUser.MaVoucher" +
                " LEFT JOIN Users ON VoucherUser.ID_User = Users.Account_ID" +
                " WHERE NgayKetThuc >= GETDATE() " +
                " AND NgayBatDau <= GETDATE()" +
                " AND SoLuong > 0 " ;


        try(Connection connection = jdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                String MaVoucher = rs.getString("MaVoucher");
                float TiLeGiamGia = rs.getFloat("TiLeGiamGia");
                int SoLuong = rs.getInt("SoLuong");
                BigDecimal SotienGiamGia = rs.getBigDecimal("SotienGiamGia");
                String NoiDung = rs.getString("NoiDung");
                String ImgVoucher = rs.getString("ImgVoucher");
                Date NgayThem = rs.getDate("NgayBatDau");
                Date NgayKetThuc = rs.getDate("NgayKetThuc");
                String email = rs.getString("Email");
                list.add(new VoucherModel(MaVoucher,TiLeGiamGia,SotienGiamGia,SoLuong,NoiDung,ImgVoucher,NgayThem,NgayKetThuc,new User(email)));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
