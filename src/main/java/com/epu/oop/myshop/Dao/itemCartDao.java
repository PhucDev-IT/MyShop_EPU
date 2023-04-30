package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.itemCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class itemCartDao implements Dao_Interface<itemCart>{

    private static itemCartDao instance;
    private final ConnectionPool connectionPool;

    private Connection connection;
    public static itemCartDao getInstance(ConnectionPool connectionPool)
    {
        if (instance == null) {
            instance = new itemCartDao(connectionPool);
        }
        return instance;
    }

    public itemCartDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void openConnection() throws SQLException {
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
    public boolean Insert(itemCart itemCart) throws SQLException {
        String sql = "INSERT INTO itemCart(Product_ID, Quantity,Category_ID, Users_ID)" +
                " VALUES (?,?,?,?) ";

        try{
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1,itemCart.getProduct().getID());
            statement.setInt(2,itemCart.getQuantity());
            statement.setInt(3,itemCart.getCategory_ID());
            statement.setInt(4,itemCart.getUser_ID());

            statement.executeUpdate();

            connection.commit();
            statement.close();
        }catch (SQLException e)
        {
            if(connection!=null)
            {
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
                return false;
            }
        }finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
        return true;
    }

    @Override
    public List<itemCart> SelectAll() throws SQLException {
        return null;
    }


    @Override
    public itemCart SelectByID(itemCart itemCart) throws SQLException {
        return null;
    }

    @Override
    public int Update(itemCart itemCart) throws SQLException {
        return 0;
    }

    @Override
    public int Delete(itemCart itemCart) throws SQLException {
        String sql = "DELETE FROM itemCart WHERE id_Cart = ? ";

        try{
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,itemCart.getIdCart());

            statement.executeUpdate();

            connection.commit();
            statement.close();
        }catch (SQLException e)
        {
            if(connection!=null)
            {
                connection.rollback();
                System.out.println("Roll back: "+e.getMessage());
                return 0;
            }
        }finally {
            connection.setAutoCommit(true);
            closeConnection();
        }
        return 1;
    }

    //Lấy tất cả sản phẩm trong giỏ hàng của user...
    public List<itemCart> getDataByUser(int idUser) throws SQLException {
        List<itemCart> list = new ArrayList<>();
        String sql = "SELECT it.*,p.SrcImg,p.TenSP,p.Quantity as soLuongTon,p.Price FROM itemCart it JOIN Product p " +
                "ON it.Product_ID = p.MaSP " +
                "AND Users_ID = ? " +
                "AND p.Activity = 'ON' ";
        try{
            openConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                itemCart it = new itemCart();
                it.setIdCart(rs.getInt("id_Cart"));
                it.setQuantity(rs.getInt("Quantity"));
                it.setCategory_ID(rs.getInt("Category_ID"));
                it.setUser_ID(rs.getInt("Users_ID"));

                Product product = new Product();
                product.setTenSP(rs.getString("TenSP"));
                product.setSrcImg(rs.getString("SrcImg"));
                product.setQuantity(rs.getInt("soLuongTon"));
                product.setPrice(rs.getBigDecimal("Price"));
                it.setProduct(product);

                list.add(it);
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
