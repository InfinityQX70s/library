package com.epam.service.impl;

import com.epam.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by infinity on 23.02.16.
 */
public class TransactionManager {

    private Connection connection;

    protected void begitTransaction() throws SQLException {
        connection = ConnectionPool.getInstance().getConnection();
        connection.setAutoCommit(false);
    }

    protected void commitTransaction() throws SQLException {
        connection.commit();
    }

    protected void rollbackTransaction() throws SQLException {
        connection.rollback();
    }

    protected void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
