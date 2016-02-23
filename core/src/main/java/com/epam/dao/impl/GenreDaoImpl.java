package com.epam.dao.impl;

import com.epam.dao.api.GenreDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class GenreDaoImpl extends ConnectionManager implements GenreDao {

    private static final String CREATE = "INSERT INTO Genre (id,name) VALUES(?,?)";
    private static final String UPDATE = "UPDATE Genre SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Genre WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Genre WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM Genre WHERE name = ?";
    private static final String FIND_ALL = "SELECT * FROM Genre";

    public void create(Genre genre) throws DaoException {
        try {
            connect();
            Object[] params = {genre.getId(),genre.getName()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
             throw new DaoException("Unknown sql exception",e);
        }
    }

    public void update(Genre genre) throws DaoException {
        try {
            connect();
            Object[] params = {genre.getName(),genre.getId()};
            execute(UPDATE,params);
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void delete(int id) throws DaoException {
        try {
            connect();
            Object[] params = {id};
            execute(DELETE,params);
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public Genre findById(int id) throws DaoException {
        Genre genre = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                genre = new Genre();
                genre.setId(resultSet.getInt(i++));
                genre.setName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return genre;
    }

    public Genre findByName(String name) throws DaoException {
        Genre genre = null;
        try {
            connect();
            Object[] params = {name};
            resultSet = executeQuery(FIND_BY_NAME,params);
            while (resultSet.next()){
                int i = 1;
                genre = new Genre();
                genre.setId(resultSet.getInt(i++));
                genre.setName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return genre;
    }

    public List<Genre> findAll() throws DaoException {
        List<Genre> genres = new ArrayList<Genre>();
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            while (resultSet.next()){
                int i = 1;
                Genre genre = new Genre();
                genre.setId(resultSet.getInt(i++));
                genre.setName(resultSet.getString(i));
                genres.add(genre);
            }
            close();
        } catch (SQLException e) {
            throw new DaoException("Unknown sql exception",e);
        }
        return genres;
    }
}
