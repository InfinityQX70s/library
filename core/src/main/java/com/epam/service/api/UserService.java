package com.epam.service.api;

import com.epam.entity.User;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public interface UserService {

    void addUser(User user) throws ServiceException;
    void deleteUser(int id) throws ServiceException;
    User findUserById(int id) throws ServiceException;
    User findUserByEmail(String email) throws ServiceException;
    List<User> findAll() throws ServiceException;
}
