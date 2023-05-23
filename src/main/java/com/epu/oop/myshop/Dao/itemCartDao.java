package com.epu.oop.myshop.Dao;

import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.itemCartModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class itemCartDao implements Dao_Interface<itemCartModel>{

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

    private itemCartDao(ConnectionPool connectionPool) {
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


    //id_cart và users_ID cùng 1 value giống nhau sẵn nên lam theo cách này đ không phải truy vấn 2 lâần để tìm ID cart
    @Override
    public boolean Insert(itemCartModel itemCartModel) throws SQLException {
        String sql = "DECLARE @id INT = ? " +
                     " DECLARE @id_product INT = ?" +
                    " DECLARE @number INT = ? " +
                "IF EXISTS (SELECT Product_ID FROM itemCart WHERE Product_ID = @id_product and id_Cart = @id) " +
                "BEGIN " +
                " UPDATE itemCart " +
                " SET Quantity += @number " +
                " WHERE Product_ID = @id_product and id_Cart = @id " +
                "END " +
                "ELSE " +
                "BEGIN " +
                "  INSERT INTO itemCart(id_Cart,Product_ID,Quantity) " +
                "  VALUES(@id,@id_product,@number) " +
                "END";

        try{
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemCartModel.getIdCart());
            statement.setInt(2, itemCartModel.getProduct().getID());
            statement.setInt(3, itemCartModel.getQuantity());


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
    public List<itemCartModel> SelectAll() throws SQLException {
        return null;
    }


    @Override
    public itemCartModel SelectByID(itemCartModel itemCartModel) throws SQLException {
        return null;
    }

    @Override
    public int Update(itemCartModel itemCartModel) throws SQLException {
        return 0;
    }

    @Override
    public int Delete(itemCartModel itemCartModel) throws SQLException {
        String sql = "DELETE FROM itemCart WHERE id_Cart = ? AND Product_ID = ?";

        try{
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, itemCartModel.getIdCart());
            statement.setInt(2,itemCartModel.getProduct().getID());
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
    public List<itemCartModel> getDataByUser(int idUser) throws SQLException {
        List<itemCartModel> list = new ArrayList<>();
        String sql = "SELECT it.*,p.MaSP,p.SrcImg,p.TenSP,p.Quantity as soLuongTon,p.Price FROM itemCart it JOIN Product p " +
                "ON it.Product_ID = p.MaSP " +
                "AND id_Cart = ? " +
                "AND p.Activity = 'ON' ";
        try{
            openConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,idUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                itemCartModel it = new itemCartModel();
                it.setIdCart(rs.getInt("id_Cart"));
                it.setQuantity(rs.getInt("Quantity"));

                Product product = new Product();
                product.setID(rs.getInt("MaSP"));
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
