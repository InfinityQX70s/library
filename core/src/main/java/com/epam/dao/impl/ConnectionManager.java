package com.epam.dao.impl;

import com.epam.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by infinity on 18.02.16.
 */
public class ConnectionManager {

    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;


    protected void connect() throws SQLException{
        connection = ConnectionPool.getInstance().getConnection();
    }

    public ResultSet executeQuery(String query, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++)
            preparedStatement.setObject(i + 1, params[i]);
        return preparedStatement.executeQuery();
    }

    public void execute(String query, Object[] params) throws SQLException {
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++)
                preparedStatement.setObject(i + 1, params[i]);
            preparedStatement.execute();
    }

    protected void close() throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (preparedStatement != null)
            preparedStatement.close();
        if (connection != null)
            connection.close();
    }
}
