package com.epam.dao.impl;

import com.epam.dao.api.UserDao;
import com.epam.entity.User;

import java.sql.SQLException;

/**
 * Created by infinity on 19.02.16.
 */
public class UserDaoImpl extends ConnectionManager implements UserDao {

    private static final String CREATE = "INSERT INTO User (email, password, firstName, lastName, isLibrarian) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE User SET email = ?, password = ?, firstName = ?, lastName = ?, isLibrarian = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM User WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM User WHERE id = ?";
    private static final String FIND_BY_FIRST_NAME_AND_LAST_NAME = "SELECT * FROM User WHERE firstName = ? AND lastName = ?";
    private static final String FIND_EMAIL = "SELECT * FROM User WHERE email = ?";

    public void create(User user) {
        try {
            connect();
            Object[] params = {user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isLibrarian()};
            execute(CREATE, params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try {
            connect();
            Object[] params = {user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isLibrarian(), user.getId()};
            execute(UPDATE, params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            connect();
            Object[] params = {id};
            execute(DELETE, params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findById(int id) {
        User user = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID, params);
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
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByFirstNameAndLastName(String firstName, String lastName) {
        User user = null;
        try {
            connect();
            Object[] params = {firstName, lastName};
            resultSet = executeQuery(FIND_BY_FIRST_NAME_AND_LAST_NAME, params);
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
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = null;
        try {
            connect();
            Object[] params = {email};
            resultSet = executeQuery(FIND_EMAIL, params);
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
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
