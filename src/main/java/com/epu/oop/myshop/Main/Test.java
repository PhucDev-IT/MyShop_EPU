package com.epu.oop.myshop.Main;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.JdbcConnection.ConnectionPool;
import com.epu.oop.myshop.model.User;

import java.sql.SQLException;


public class Test{
    public Test() throws SQLException {
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        CreateSQL createSQL = CreateSQL.getInstance();
//        createSQL.AutoCreateDatabase();
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Product_Dao productDao = Product_Dao.getInstance(connectionPool);
//        Random random = new Random();
//        for(int i=75000;i<500000;i++)
//        {
//                Product product = new Product(0,"Sản phẩm "+i,random.nextInt(9200)+1, BigDecimal.valueOf(123*i),"Khong nói gì",
//                        "/com/epu/oop/myshop/image/Product/product1.png", random.nextInt(25)+1,new User(random.nextInt(4)+2));
//            productDao.Insert(product);
//                System.out.println(i);
//        }
//
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Product_Dao product_dao = Product_Dao.getInstance(connectionPool);

        Object[] obj1 = product_dao.sumTotalOrder(new User(5));

        System.out.println("Finnish");
    }


}