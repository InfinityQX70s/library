package com.epam.dao.impl;

import com.epam.dao.api.GenreDao;
import com.epam.entity.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class GenreDaoImpl extends ManagerDao implements GenreDao {

    private static final String CREATE = "INSERT INTO Genre (name) VALUES(?)";
    private static final String UPDATE = "UPDATE Genre SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Genre WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Genre WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM Genre WHERE name = ?";
    private static final String FIND_ALL = "SELECT * FROM Genre";

    public void create(Genre genre) {
        try {
            connect();
            Object[] params = {genre.getName()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Genre genre) {
        try {
            connect();
            Object[] params = {genre.getName(),genre.getId()};
            execute(UPDATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            connect();
            Object[] params = {id};
            execute(DELETE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Genre findById(int id) {
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
            e.printStackTrace();
        }
        return genre;
    }

    public Genre findByName(String name) {
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
            e.printStackTrace();
        }
        return genre;
    }

    public List<Genre> findAll() {
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
            e.printStackTrace();
        }
        return genres;
    }
}
