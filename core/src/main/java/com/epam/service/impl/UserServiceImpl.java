package com.epam.service.impl;

import com.epam.dao.api.UserDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.User;
import com.epam.service.api.UserService;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;
import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;

/**
 * Created by infinity on 23.02.16.
 */
public class UserServiceImpl extends TransactionManager implements UserService{

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) throws ServiceException {
        try {
            User element = userDao.findByEmail(user.getEmail());
            if (element == null){
                userDao.create(user);
            }else
                throw new ServiceException("User with such identifier exist", ServiceStatusCode.USER_ALREADY_EXIST);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public User findUserById(int id) throws ServiceException {
        try {
            User user = userDao.findById(id);
            if (user != null){
                return user;
            }else
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public User findUserByEmail(String email) throws ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user != null){
                return user;
            }else
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

}
