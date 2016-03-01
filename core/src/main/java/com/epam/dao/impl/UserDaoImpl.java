package com.epam.dao.impl;

import com.epam.dao.api.UserDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.User;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by infinity on 19.02.16.
 */
public class UserDaoImpl extends ConnectionManager implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    private static final String CREATE = "INSERT INTO User (email, password, firstName, lastName, isLibrarian) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE User SET email = ?, password = ?, firstName = ?, lastName = ?, isLibrarian = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM User WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM User WHERE id = ?";
    private static final String FIND_BY_FIRST_NAME_AND_LAST_NAME = "SELECT * FROM User WHERE firstName = ? AND lastName = ?";
    private static final String FIND_EMAIL = "SELECT * FROM User WHERE email = ?";

    public void create(User user) throws DaoException {
        try {
            connect();
            Object[] params = {user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isLibrarian()};
            execute(CREATE, params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void update(User user) throws DaoException {
        try {
            connect();
            Object[] params = {user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isLibrarian(), user.getId()};
            execute(UPDATE, params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void delete(int id) throws DaoException {
        try {
            connect();
            Object[] params = {id};
            execute(DELETE, params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public User findById(int id) throws DaoException {
        User user;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID, params);
            user = getResultSet();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return user;
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) throws DaoException {
        User user;
        try {
            connect();
            Object[] params = {firstName, lastName};
            resultSet = executeQuery(FIND_BY_FIRST_NAME_AND_LAST_NAME, params);
            user = getResultSet();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return user;
    }

    public User findByEmail(String email) throws DaoException {
        User user;
        try {
            connect();
            Object[] params = {email};
            resultSet = executeQuery(FIND_EMAIL, params);
            user = getResultSet();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return user;
    }

    private User getResultSet() throws SQLException {
        User user = null;
        while (resultSet.next()) {
            int i = 1;
            user = new User();
            user.setId(resultSet.getInt(i++));
            user.setEmail(resultSet.getString(i++));
            user.setPassword(resultSet.getString(i++));
            user.setFirstName(resultSet.getString(i++));
            user.setLastName(resultSet.getString(i++));
            user.setLibrarian(resultSet.getBoolean(i));
        }
        return user;
    }
}
