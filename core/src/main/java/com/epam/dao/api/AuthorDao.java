package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Author;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface AuthorDao {

    void create(Author author) throws DaoException;
    void update(Author author) throws DaoException;
    void delete(int id) throws DaoException;
    Author findByAlias(String alisa) throws DaoException;
    Author findById(int id) throws DaoException;
    List<Author> findAll() throws DaoException;
}
