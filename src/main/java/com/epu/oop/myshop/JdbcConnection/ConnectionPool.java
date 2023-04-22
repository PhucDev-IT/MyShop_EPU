package com.epu.oop.myshop.JdbcConnection;

import com.epu.oop.myshop.model.CreateSQL;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class ConnectionPool {
    private final String url = CreateSQL.urlConnect;
    private final String username = CreateSQL.userName;
    private final String password = CreateSQL.password;
    private static final int MAX_POOL_SIZE = 10;

    private static ConnectionPool instance;
    private DataSource dataSource;

    private static HikariConfig config;

    public ConnectionPool(){
        // Khởi tạo HikariCP DataSource với các thông số cấu hình
         config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(MAX_POOL_SIZE);

        dataSource = new HikariDataSource(config);
    }
    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            System.out.println("Khơi tạo pool");
        }
        return instance;
    }
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
