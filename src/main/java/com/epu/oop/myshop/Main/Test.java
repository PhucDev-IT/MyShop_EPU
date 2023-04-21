package com.epu.oop.myshop.Main;

import com.epu.oop.myshop.Dao.Product_Dao;
import com.epu.oop.myshop.Database.JDBCUtil;
import com.epu.oop.myshop.model.Category;
import com.epu.oop.myshop.model.CreateSQL;
import com.epu.oop.myshop.model.Product;
import com.epu.oop.myshop.model.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

public class Test{
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//        CreateSQL createSQL = CreateSQL.getInstance();
//        createSQL.AutoCreateDatabase();

//        Product_Dao productDao = Product_Dao.getInstance();
//        Random random = new Random();
//        for(int i=1;i<10000;i++)
//        {
//                Product product = new Product("Sản phẩm "+i,random.nextFloat(9200)+1, BigDecimal.valueOf(123*i),"Khong nói gì",
//                        "/com/epu/oop/myshop/image/Product/product1.png", random.nextInt(25)+1,new User(random.nextInt(4)+2));
//            productDao.Insert(product);
//                System.out.println(i);
//        }

    }
}