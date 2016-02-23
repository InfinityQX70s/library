package com.epam;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by infinity on 23.02.16.
 */
public class ConnectionPool {

    private static ConnectionPool connectionPool;
    private BasicDataSource dataSource;

    private ConnectionPool(){
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("1");
        dataSource.setUrl("jdbc:mysql://localhost/library");

        // the settings below are optional -- dbcp can work with defaults
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(20);
        dataSource.setMaxOpenPreparedStatements(180);

    }

    public static ConnectionPool getInstance(){
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
            return connectionPool;
        } else {
            return connectionPool;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
