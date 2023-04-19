package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class HoaDon_Dao implements Dao_Interface<HoaDon> {

    private static HoaDon_Dao instance;

    public static HoaDon_Dao getInstance() {
        if (instance == null) {
            instance = new HoaDon_Dao();
        }
        return instance;
    }


    @Override
    public int Insert(HoaDon t) {
        int results = 0;

            String sql = "INSERT INTO HoaDon(NgayLapHD,TongTien,MaVoucher,ThanhTien,Users_ID)" +
                    " VALUES (?,?,?,?,?)";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1,t.getNgayLapHD());
            statement.setBigDecimal(2,t.getTongTien());
            statement.setString(3,t.getVoucher().getMaVoucher());
            statement.setBigDecimal(4,t.getThanhTien());
            statement.setInt(5,t.getUser().getID());

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


    //Thanh toán hóa đơn băn ngân hàng
    public boolean OrderDetailsPayByBank(HoaDon hoaDon, CTHoaDon cthd,PaymentHistory paymentHistory,Account account) {
        PreparedStatement preOrder = null;
        PreparedStatement preDetails = null;
        PreparedStatement prePaymentHistory = null;
        PreparedStatement preAccount = null;

        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction
            String sqlOrder = null;
            if(hoaDon.getVoucher()!=null) {
                sqlOrder = "INSERT INTO HoaDon(NgayLapHD,TongTien,ThanhTien,Users_ID,MaVoucher)" +
                        " VALUES (?,?,?,?,?)";
                preOrder = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
                preOrder.setString(5,hoaDon.getVoucher().getMaVoucher());
            }else{
                sqlOrder = "INSERT INTO HoaDon(NgayLapHD,TongTien,ThanhTien,Users_ID)" +
                        " VALUES (?,?,?,?)";
                //để thiết lập cho PreparedStatement trả về giá trị của khóa chính được tạo ra
                preOrder = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
            }

            String sqlOrderDtails = "INSERT INTO CTHoaDon(Product_ID,HoaDon_ID,Quantity,Price)" +
                    " VALUES (?,?,?,?)";

            String sqlPayment = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID)" +
                    " VALUES (?,?,?,?,?,?)";

            String sqlAccount = "UPDATE Account SET " +
                    "Passwords=?," +
                    " Currency=?," +
                    " Statuss=?," +
                    " PhanQuyen=?" +
                    " WHERE UserName=?";


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
            preDetails.setInt(1, cthd.getProduct().getID());
            preDetails.setInt(2, hoaDonId);
            preDetails.setFloat(3, cthd.getQuantity());
            preDetails.setBigDecimal(4, new BigDecimal(cthd.getPrice() + ""));

            preDetails.executeUpdate();

            prePaymentHistory = connection.prepareStatement(sqlPayment);
            prePaymentHistory.setString(1, paymentHistory.getTenGiaoDich());
            prePaymentHistory.setString(2, paymentHistory.getNoiDung());
            prePaymentHistory.setBigDecimal(3, paymentHistory.getSoTien());
            prePaymentHistory.setDate(4, paymentHistory.getNgayGiaoDich());
            prePaymentHistory.setString(5, paymentHistory.getImgSrcIcon());
            prePaymentHistory.setInt(6, paymentHistory.getUser().getID());

            prePaymentHistory.executeUpdate();

            preAccount = connection.prepareStatement(sqlAccount) ;
                preAccount.setString(1, account.getPassword());
                preAccount.setBigDecimal(2, account.getMoney());
                preAccount.setString(3, account.getStatus());
                preAccount.setString(4, account.getPhanQuyen());
                preAccount.setString(5, account.getUserName());
            preAccount.executeUpdate();

                connection.commit();
                return true;
            } catch(SQLException ex){
                if (connection != null) {
                    try {
                        connection.rollback();
                        System.out.println("Rolled back.");
                    } catch (SQLException exrb) {
                        exrb.printStackTrace();
                    }
                }
            } finally{
                try {
                    if (preOrder != null) {
                        preOrder.close();
                    }

                    if (preDetails != null) {
                        preDetails.close();
                    }
                    if(prePaymentHistory!=null){
                        prePaymentHistory.close();
                    }

                    if(preAccount!=null){
                        preAccount.close();
                    }

                    connection.setAutoCommit(true);
                } catch (SQLException excs) {
                    excs.printStackTrace();
                }
                JDBCUtil.CloseConnection(connection);
            }

        return false;
    }

        //Hóa đơn có tại nhà
    public boolean OrderDetailsPayAtHome(HoaDon hoaDon,CTHoaDon cthd)
    {
        PreparedStatement orderDetails = null;
        PreparedStatement order = null;
        int checkOrder = 0;
        int checkDetails=0;
        Connection connection = null;
        try {
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Bắt đầu transaction
            String sqlOrder = null;

            if(hoaDon.getVoucher()!=null) {
                sqlOrder = "INSERT INTO HoaDon(NgayLapHD,TongTien,ThanhTien,Users_ID,MaVoucher)" +
                        " VALUES (?,?,?,?,?)";
                order = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
                order.setString(5,hoaDon.getVoucher().getMaVoucher());
            }else{
                sqlOrder = "INSERT INTO HoaDon(NgayLapHD,TongTien,ThanhTien,Users_ID)" +
                        " VALUES (?,?,?,?)";
                order = connection.prepareStatement(sqlOrder,PreparedStatement.RETURN_GENERATED_KEYS);
            }

            String sqlOrderDtails = "INSERT INTO CTHoaDon(Product_ID,HoaDon_ID,Quantity,Price)" +
                    " VALUES (?,?,?,?)";

            //để thiết lập cho PreparedStatement trả về giá trị của khóa chính được tạo ra

            order.setDate(1,hoaDon.getNgayLapHD());
            order.setBigDecimal(2,hoaDon.getTongTien());
            order.setBigDecimal(3,hoaDon.getThanhTien());
            order.setInt(4,hoaDon.getUser().getID());

            checkOrder = order.executeUpdate();

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
            orderDetails = connection.prepareStatement(sqlOrderDtails);
            orderDetails.setInt(1,cthd.getProduct().getID());
            orderDetails.setInt(2,hoaDonId);
            orderDetails.setFloat(3,cthd.getQuantity());
            orderDetails.setBigDecimal(4,new BigDecimal(cthd.getPrice()+""));


            checkDetails = orderDetails.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Rolled back.");
                } catch (SQLException exrb) {
                    exrb.printStackTrace();
                }
            }
        } finally {
            try {
                if (order != null) {
                    order.close();
                }

                if (orderDetails != null) {
                    orderDetails.close();
                }

                connection.setAutoCommit(true);
            } catch (SQLException excs) {
                excs.printStackTrace();
            }
            JDBCUtil.CloseConnection(connection);
        }
        return checkOrder>0 && checkDetails>0;
    }

    @Override
    public List<HoaDon> SelectAll() {
        List<HoaDon> list = new ArrayList<>();

            String sql = "SELECT * FROM HoaDon";

        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()){
            while (rs.next()){
                int ID = rs.getInt("ID");
                Date NgayLapHD = rs.getDate("NgayLapHD");
                BigDecimal TongTien = rs.getBigDecimal("TongTien");
                String MaVoucher = rs.getString("MaVoucher");
                BigDecimal ThanhTien = rs.getBigDecimal("ThanhTien");
                int Users_ID = rs.getInt("Users_ID");

                list.add(new HoaDon(ID,NgayLapHD,TongTien,new VoucherModel(MaVoucher),ThanhTien,new User(Users_ID)));
            }
            statement.close();
            JDBCUtil.CloseConnection(connection);
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra "+e.getMessage());
        }

        return list;
    }

    @Override
    public HoaDon SelectByID(HoaDon t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int Update(HoaDon t) {
        return 1;
    }

    @Override
    public int Delete(HoaDon t) {
        // TODO Auto-generated method stub
        return 0;
    }

    //Tính tổng đơn hàng đã bán và tổng tiền của người bán
    public Object[] SumOrder(int ID)
    {
        Object[] obj = new Object[2];
            String sql = "select count(HoaDon.ID)  , sum(ThanhTien) from CTHoaDon join HoaDon " +
                    "                    ON CTHoaDon.HoaDon_ID = HoaDon.ID " +
                    "                    Join Product " +
                    "                    ON CTHoaDon.Product_ID = Product.ID " +
                    "                    JOIN ProductSeller ON Product.ID = ProductSeller.Product_ID " +
                    "                    AND ProductSeller.Users_ID = ?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,ID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                obj[0] = rs.getInt(1);
                obj[1] = rs.getBigDecimal(2);
            }
            rs.close();
            statement.close();
            JDBCUtil.CloseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    //Tính tổng hóa đơn đã bán trong ngày hôm nay
    public Object[] OrderToDay(Date date,int ID)
    {
        Object[] obj = new Object[2];

            String sql = "select count(HoaDon.ID) as Numbers , sum(ThanhTien) as Total from CTHoaDon join HoaDon " +
                    "ON CTHoaDon.HoaDon_ID = HoaDon.ID " +
                    "AND NgayLapHD = ? " +
                    "Join Product " +
                    "ON CTHoaDon.Product_ID = Product.ID " +
                    "JOIN ProductSeller ON Product.ID = ProductSeller.Product_ID " +
                    "AND ProductSeller.Users_ID = ?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1,date);
            statement.setInt(2,ID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                obj[0] = rs.getInt("Numbers");
                obj[1] = rs.getBigDecimal("Total");
            }
            statement.close();
            rs.close();
            JDBCUtil.CloseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    //-------------------------------- Tính tổng tiền người dùng đã bán hàng - set profile--------------------------
    public BigDecimal TotalMoney(User u)
    {
        BigDecimal result = new BigDecimal("0");


            String sql = "SELECT CAST(SUM(Quantity*Price) AS DECIMAL) FROM CTHoaDon" +
                    " WHERE Product_ID=?";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,u.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                result = BigDecimal.valueOf(rs.getInt(1));
            }
            statement.close();
            rs.close();
            JDBCUtil.CloseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //-------------------------- lỊCH SỬ MUA HÀNG ---------------------------------------------------
    public List<Object[]> getPurchaseProducts(User u)
    {
        List<Object[]> results = new ArrayList<>();


            String sql = "SELECT p.TenSP,p.SrcImg,cthd.Quantity,cthd.Price,HoaDon.NgayLapHD,Users.FullName " +
                    "FROM Product p INNER JOIN CTHoaDon cthd " +
                    "ON p.ID = cthd.Product_ID " +
                    "INNER JOIN HoaDon ON cthd.HoaDon_ID = HoaDon.ID " +
                    "JOIN ProductSeller ON p.ID = ProductSeller.Product_ID " +
                    "JOIN Users ON ProductSeller.Users_ID = Users.ID " +
                    "AND HoaDon.Users_ID = ?" +
                    " ORDER BY HoaDon.NgayLapHD DESC";
        try(Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,u.getID());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Object[] obj = new Object[4];
                Product product = new Product();
                product.setTenSP(rs.getString("TenSP"));
                product.setSrcImg(rs.getString("SrcImg"));
                obj[0] = product;
                CTHoaDon cthd = new CTHoaDon();
                cthd.setQuantity(rs.getFloat("Quantity"));
                cthd.setPrice(rs.getBigDecimal("Price"));
                obj[1] = cthd;
                obj[2] = rs.getDate("NgayLapHD");
                obj[3] = rs.getString("FullName");
                results.add(obj);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return results;
    }

    public int IndexNewInsert()
    {
        int maxID = 0;

            String sql = "SELECT MAX(ID) FROM HoaDon";
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
            System.out.println("Có lỗi xảy ra,Không thể lấy index new: "+e.getMessage());
        }
        return maxID;
    }
}
