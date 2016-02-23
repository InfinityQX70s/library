package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Status;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface StatusDao {

    Status findById(int id) throws DaoException;
    Status findByName(String name) throws DaoException;
    List<Status> findAll() throws DaoException;
}
