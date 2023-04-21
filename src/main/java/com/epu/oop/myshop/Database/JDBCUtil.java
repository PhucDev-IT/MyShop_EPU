package com.epu.oop.myshop.Database;

import com.epu.oop.myshop.model.CreateSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection(){
        Connection conn = null;
            try{
                // Tải driver JDBC
                Class.forName(CreateSQL.driver);

                // Thông tin đăng nhập
                String url = CreateSQL.urlConnect;
                String username = CreateSQL.userName;
                String password = CreateSQL.password;

                // Tạo kết nối
                conn = DriverManager.getConnection(url, username, password);


            }catch (SQLException e)
            {
                System.out.println("Không thể kết nối đến Database: \n"+e.getMessage());
            }catch ( ClassNotFoundException e)
            {
                System.out.println("Could not load JDBC driver: " + e.getMessage());
            }


        return conn;
    }

    public static void CloseConnection(Connection c)
    {
        try{
            if(c!=null)
            {
                c.close();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
