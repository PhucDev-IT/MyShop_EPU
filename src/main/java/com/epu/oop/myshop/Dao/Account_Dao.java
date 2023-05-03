package com.epu.oop.myshop.Dao;


import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.Main.App;
import com.epu.oop.myshop.model.Account;
import com.epu.oop.myshop.model.PaymentHistory;
import com.epu.oop.myshop.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Account_Dao implements Dao_Interface<Account> {

    private final ConnectionPool jdbcUtil;
    private static Account_Dao instance;

    public static Account_Dao getInstance(ConnectionPool jdbcUtil) {
        if (instance == null) {
            instance = new Account_Dao(jdbcUtil);
        }
        return instance;
    }

    public Account_Dao(ConnectionPool jdbcUtil){

        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public boolean Insert(Account t) throws SQLException {
        int results = 0;
        String sql = "INSERT INTO Account(UserName,Passwords,Currency,Activity,PhanQuyen)" +
                " VALUES (?,?,?,?,?)";

        Connection connection = null;
           try{
               connection = jdbcUtil.getConnection();
               connection.setAutoCommit(false);
               PreparedStatement statement = connection.prepareStatement(sql);
               statement.setString(1,t.getUserName());
               statement.setString(2,t.getPassword());
               statement.setBigDecimal(3,new BigDecimal(0));
               statement.setString(4,"ON");
               statement.setString(5,"Member");

               //Bước 3:Thực thi câu lệnh
               results =  statement.executeUpdate();
               statement.close();
               connection.commit();
       }catch (SQLException e) {
               if(connection!=null){
                   connection.rollback();
               }
           e.printStackTrace();
       }finally {
              connection.setAutoCommit(true);
              connection.close();
           }
        return results>0;
    }

    @Override
    public List<Account> SelectAll() {

        List<Account> list = new ArrayList<>();

            String sql = "SELECT * FROM Account";

            try(Connection connection = jdbcUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()){
                 while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Activity");
                String PhanQuyen = rs.getString("PhanQuyen");

                list.add(new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Account SelectByID(Account t) {
        Account account = null;
        String sql = "SELECT * FROM Account WHERE UserName=?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getUserName());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Activity");
                String PhanQuyen = rs.getString("PhanQuyen");

                account =  new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen);
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public int Update(Account t) {
        int results = 0;
            String sql = "UPDATE Account SET " +
                    "Passwords=?," +
                    " Currency=?," +
                    " Activity=?," +
                    " PhanQuyen=?" +
                    " WHERE UserName=?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,t.getPassword());
            statement.setBigDecimal(2,t.getMoney());
            statement.setString(3,t.getStatus());
            statement.setString(4,t.getPhanQuyen());
            statement.setString(5,t.getUserName());

            results = statement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public int Delete(Account t) {
        int results = 0;
            String sql = "DELETE FROM Account" +
                    " WHERE UserName = ?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,t.getUserName());
            //Bước 3:Thực thi câu lệnh
            results = statement.executeUpdate();
            System.out.println("Có "+results +" thay đổi");

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    //---------------Kiểm tra đăng ký tài khoản---------------------

    public boolean checkRegister(Account account)
    {
        int check = 0;
        String sql = "SELECT COUNT(*) AS number FROM Account WHERE ID = ?";
        try(Connection conn = jdbcUtil.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1,account.getID());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                check = resultSet.getInt("number");
            }

        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra: "+e.getMessage());
        }
        return  check>0;
    }

    //Kiểm tra đăng nhập
    public Account checkLogin(Account account)
    {
        Account a = null;
            String sql = "SELECT * FROM Account " +
                    "WHERE UserName=? " +
                    "AND Passwords = ?";
        try (Connection connection = jdbcUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,account.getUserName());
            statement.setString(2,account.getPassword());
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                int ID = rs.getInt("ID");
                String UserName = rs.getString("UserName");
                String Passwords = rs.getString("Passwords");
                BigDecimal money = rs.getBigDecimal("Currency");
                String Statuss = rs.getString("Activity");
                String PhanQuyen = rs.getString("PhanQuyen");

                a = new Account(ID,UserName,Passwords,money,Statuss,PhanQuyen);
            }
            statement.close();
            rs.close();
        }catch (SQLException e){
            System.out.println("Có lỗi xảy ra: "+e.getMessage());
        }
       return a;
    }

    //Đăng ký thông tin người dùng
    public boolean signUpUser(Account account, User user) throws SQLException {
        int check = 0;
        Connection connection = null;
        PreparedStatement preAccount = null;
        PreparedStatement preUser = null;
        PreparedStatement preMessenge = null;
        try{

            String sql = "INSERT INTO Account(UserName,Passwords,Currency,Activity,PhanQuyen)" +
                    " VALUES (?,?,?,?,?)";
            String sqlUs = "INSERT INTO Users(Account_ID,FullName,Email)" +
                    " VALUES (?,?,?)";
            String sqlMess = "INSERT INTO Messenger(Sender,Content,Statuss,SentDate,SrcIcon,Account_ID)" +
                    " VALUES (?,?,?,?,?,?)";
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);
            preAccount = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preAccount.setString(1,account.getUserName());
            preAccount.setString(2,account.getPassword());
            preAccount.setBigDecimal(3,new BigDecimal(0));
            preAccount.setString(4,"ON");
            preAccount.setString(5,"Member");
            preAccount.executeUpdate();

            int index;
            try(ResultSet rs = preAccount.getGeneratedKeys()){
                if(rs.next()){
                   index =  rs.getInt(1);
                }else {
                    throw new SQLException("Không thể đăng ký");
                }
            }
            preUser = connection.prepareStatement(sqlUs);
            preUser.setInt(1,index);
            preUser.setString(2,user.getFullName());
            preUser.setString(3,user.getEmail());
            check = preUser.executeUpdate();

            preMessenge = connection.prepareStatement(sqlMess);
            preMessenge.setString(1,"Tin nhắn từ hệ thống");
            preMessenge.setString(2,"Welcome to MyShop");
            preMessenge.setBoolean(3,false);
            preMessenge.setDate(4,new Date(System.currentTimeMillis()));
            preMessenge.setString(5,null);
            preMessenge.setInt(6,index);

            preMessenge.executeUpdate();


            connection.commit();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
            }
            e.printStackTrace();
        }finally {
            if(preUser!=null)   preUser.close();

            if(preAccount!=null) preAccount.close();

            if(preMessenge!=null) preMessenge.close();
            connection.setAutoCommit(true);
            connection.close();
        }
        return check>0;
    }

    //Chuyển tiền, k dùng update vì method này sử dụng transaction tránh mất tiền oan
    public boolean UpdatetransferMoney(Account sender, Account receiver, PaymentHistory paymentHistory) throws SQLException {
        String sqlPayment = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID,Account_ID)" +
                " VALUES (?,?,?,?,?,?,?)";

        String sql = "UPDATE Account SET " +
                " Currency=? " +
                " WHERE UserName=?";

        String sqlReceiver = "UPDATE Account " +
                " SET " +
                " Currency = Currency + ?" +
                " WHERE UserName = ?";

        String sqlMess = "INSERT INTO Messenger(Sender,Content,Statuss,SentDate,SrcIcon,Account_ID)" +
                " VALUES (?,?,?,?,?,(SELECT ID FROM Account WHERE UserName = ?))";

        Connection connection = null;
        try {
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement stmReceiver = connection.prepareStatement(sqlReceiver);
            PreparedStatement stmPayment = connection.prepareStatement(sqlPayment);
            PreparedStatement preMessenge = connection.prepareStatement(sqlMess);

            statement.setBigDecimal(1,sender.getMoney());
            statement.setString(2,sender.getUserName());
            statement.executeUpdate();

            stmReceiver.setBigDecimal(1,receiver.getMoney());
            stmReceiver.setString(2,receiver.getUserName());
            stmReceiver.executeUpdate();

            stmPayment.setString(1,paymentHistory.getTenGiaoDich());
            stmPayment.setString(2,paymentHistory.getNoiDung());
            stmPayment.setBigDecimal(3,paymentHistory.getSoTien());
            stmPayment.setDate(4,paymentHistory.getNgayGiaoDich());
            stmPayment.setString(5,paymentHistory.getImgSrcIcon());
            stmPayment.setInt(6,paymentHistory.getUser().getID());
            stmPayment.setInt(7,paymentHistory.getAccount().getID());
            stmPayment.executeUpdate();

            preMessenge.setString(1,"Tin nhắn từ hệ thống");
            preMessenge.setString(2,"Bạn vừa được nhận "+ App.numf.format(receiver.getMoney())+ " từ: "+sender.getUserName());
            preMessenge.setBoolean(3,false);
            preMessenge.setDate(4,new Date(System.currentTimeMillis()));
            preMessenge.setString(5,null);
            preMessenge.setString(6,sender.getUserName());

            preMessenge.executeUpdate();

            connection.commit();
            statement.close();
            stmReceiver.close();
            stmPayment.close();
            preMessenge.close();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
            }
            return false;
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return true;
    }

    //Rút tiền - nạp tiền
    public boolean transferMoney(Account account,PaymentHistory paymentHistory) throws SQLException {
        String sqlPayment = "INSERT INTO PaymentHistory(TenGiaoDich,NoiDung,SoTien,NgayGiaoDich,SrcImgIcon,Users_ID)" +
                " VALUES (?,?,?,?,?,?)";

        String sql = "UPDATE Account SET " +
                " Currency=? " +
                " WHERE UserName=?";
        Connection connection = null;
        try {
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement stmPayment = connection.prepareStatement(sqlPayment);

            statement.setBigDecimal(1,account.getMoney());
            statement.setString(2,account.getUserName());
            statement.executeUpdate();

            stmPayment.setString(1,paymentHistory.getTenGiaoDich());
            stmPayment.setString(2,paymentHistory.getNoiDung());
            stmPayment.setBigDecimal(3,paymentHistory.getSoTien());
            stmPayment.setDate(4,paymentHistory.getNgayGiaoDich());
            stmPayment.setString(5,paymentHistory.getImgSrcIcon());
            stmPayment.setInt(6,paymentHistory.getUser().getID());
            stmPayment.executeUpdate();

            connection.commit();
            statement.close();
            stmPayment.close();
        }catch (SQLException e) {
            if(connection!=null){
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
            }
            return false;
        }finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return true;
    }
    //----------------- KHÓA TÀI KHOẢN ĐĂNG NHẬP ---------------------------------
    public boolean lockAccount(String userName)
    {
        String sql = "UPDATE Account" +
                " SET Activity = 'LOCK' " +
                " WHERE userName = ?";
        try(Connection connection = jdbcUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,userName);
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //-------------------------- QUÊN MẬT KHẨU ĐĂNG NHẬP--------------------------------------

    public boolean changeFogotPass(String userName,String password) throws SQLException {
        int result = 0;
        String sql = "UPDATE Account" +
                " SET Passwords = ? " +
                " WHERE userName = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcUtil.getConnection();
            connection.setAutoCommit(false);

             statement = connection.prepareStatement(sql);
            statement.setString(1,password);
            statement.setString(2,userName);

            result= statement.executeUpdate() ;

            connection.commit();
        } catch (SQLException e) {
            if(connection!=null)
            {
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
            }
        }finally {
            if(statement!=null) statement.close();
            connection.setAutoCommit(true);
            connection.close();
        }
        return result>0;
    }
}
