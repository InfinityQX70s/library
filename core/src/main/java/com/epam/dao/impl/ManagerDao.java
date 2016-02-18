package com.epam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by infinity on 18.02.16.
 */
public class ManagerDao {

    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;



    protected void close() throws SQLException {
        if (resultSet != null)
            resultSet.close();
        if (preparedStatement != null)
            preparedStatement.close();
        if (connection != null)
            connection.close();
    }
}
