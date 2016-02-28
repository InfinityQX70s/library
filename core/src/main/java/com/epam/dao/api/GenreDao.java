package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Genre;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface GenreDao {

    void create(Genre genre) throws DaoException;
    void update(Genre genre) throws DaoException;
    void delete(int id) throws DaoException;
    Genre findById(int id) throws DaoException;
    Genre findByName(String name) throws DaoException;
    List<Genre> findAll() throws DaoException;
    List<Genre> searchByName(String name) throws DaoException;
    List<Genre> findAllByOffset(int page) throws DaoException;
}
