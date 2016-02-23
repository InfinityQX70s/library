package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.User;

/**
 * Created by infinity on 19.02.16.
 */
public interface UserDao {

    void create(User user) throws DaoException;
    void update(User user) throws DaoException;
    void delete(int id) throws DaoException;
    User findById(int id) throws DaoException;
    User findByFirstNameAndLastName(String firstName, String lastName) throws DaoException;
    User findByEmail(String email) throws DaoException;
}
