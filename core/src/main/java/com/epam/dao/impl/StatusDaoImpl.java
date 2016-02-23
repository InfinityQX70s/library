package com.epam.dao.impl;

import com.epam.dao.api.StatusDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Status;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class StatusDaoImpl extends ConnectionManager implements StatusDao {

    private static final String FIND_BY_ID = "SELECT * FROM Status WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM Status WHERE name = ?";
    private static final String FIND_ALL = "SELECT * FROM Status";

    public Status findById(int id) throws DaoException {
        Status status = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                status = new Status();
                status.setId(resultSet.getInt(i++));
                status.setName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return status;
    }

    public Status findByName(String name) throws DaoException {
        Status status = null;
        try {
            connect();
            Object[] params = {name};
            resultSet = executeQuery(FIND_BY_NAME,params);
            while (resultSet.next()){
                int i = 1;
                status = new Status();
                status.setId(resultSet.getInt(i++));
                status.setName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return status;
    }

    public List<Status> findAll() throws DaoException {
        List<Status> statuses = new ArrayList<Status>();
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            while (resultSet.next()){
                int i = 1;
                Status status = new Status();
                status.setId(resultSet.getInt(i++));
                status.setName(resultSet.getString(i));
                statuses.add(status);
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return statuses;
    }
}
