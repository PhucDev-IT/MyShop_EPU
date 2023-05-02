package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Messenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessengeDao implements Dao_Interface<Messenger> {

    private static MessengeDao instance;

    private Connection connection;

    private final ConnectionPool connectionPool;

    public MessengeDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static MessengeDao getInstance(ConnectionPool connectionPool)
    {
        if (instance == null) {
            instance = new MessengeDao(connectionPool);
        }
        return instance;
    }
    private void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = connectionPool.getConnection();
        }
    }

    private void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }




        @Override
    public boolean Insert(Messenger messenger) throws SQLException {
        String sql = "INSERT INTO Messenger(Sender,Content,Statuss,SentDate,SrcIcon,Account_ID)" +
                " VALUES (?,?,?,?,?,?)";

        try{
            openConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,messenger.getNameSender());
            statement.setString(2,messenger.getContent());
            statement.setBoolean(3,false);
            statement.setDate(4,messenger.getSentDate());
            statement.setString(5, messenger.getImgSrc());
            statement.setInt(6,messenger.getAccount_ID());

            statement.executeUpdate();

            connection.commit();
            statement.close();
        }catch (SQLException e)
        {
            if(connection!=null)
            {
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

    @Override
    public List<Messenger> SelectAll() throws SQLException {
        return null;
    }

    @Override
    public Messenger SelectByID(Messenger messenger) throws SQLException {
        return null;
    }

    @Override
    public int Update(Messenger messenger) throws SQLException {
        return 0;
    }

    @Override
    public int Delete(Messenger messenger) throws SQLException {
        return 0;
    }

    //Lấy tất cả tin nhắn của người dùng
    public List<Messenger> getDataMessenge(int AccountID) throws SQLException {
        List<Messenger> list = new ArrayList<>();
        String sql = "SELECT * FROM Messenger " +
                "WHERE Account_ID = ? " +
                "Order by sentDate";
        System.out.println(AccountID);
        try {
        openConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,AccountID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Messenger messenger = new Messenger();
                messenger.setID(rs.getInt("ID"));
                messenger.setNameSender(rs.getString("Sender"));
                messenger.setContent(rs.getString("Content"));
                messenger.setSentDate(rs.getDate("SentDate"));
                messenger.setStatus(rs.getBoolean("Statuss"));
                messenger.setImgSrc(rs.getString("SrcIcon"));
                messenger.setAccount_ID(rs.getInt("Account_ID"));
                list.add(messenger);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }

        return list;
    }
}
