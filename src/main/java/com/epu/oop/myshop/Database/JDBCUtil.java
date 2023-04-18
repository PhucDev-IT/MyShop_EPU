package com.epu.oop.myshop.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection(){
        Connection conn = null;
            try{
                // Tải driver JDBC
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                // Thông tin đăng nhập
                String url = "jdbc:sqlserver://localhost:1433;databaseName=MyShop1";
                String username = "sa";
                String password = "123456";

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
