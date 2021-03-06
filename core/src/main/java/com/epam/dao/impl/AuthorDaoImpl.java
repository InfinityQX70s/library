package com.epam.dao.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Author;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class AuthorDaoImpl extends ConnectionManager implements AuthorDao {

    private static final Logger LOG = Logger.getLogger(AuthorDaoImpl.class);

    private static final String CREATE = "INSERT INTO Author (id, alias) VALUES(?,?)";
    private static final String UPDATE = "UPDATE Author SET alias = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Author WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Author WHERE id = ?";
    private static final String FIND_BY_ALIAS = "SELECT * FROM Author WHERE alias = ?";
    private static final String FIND_ALL = "SELECT * FROM Author";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Author WHERE alias LIKE ?;";
    private static final String FIND_ALL_BY_OFFSET = "SELECT * FROM Author LIMIT ?,7;";

    public void create(Author author) throws DaoException {
        try {
            connect();
            Object[] params = {author.getId(),author.getAlias()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void update(Author author) throws DaoException {
        try {
            connect();
            Object[] params = {author.getAlias(),author.getId()};
            execute(UPDATE,params);
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
            execute(DELETE,params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public Author findByAlias(String alias) throws DaoException {
        Author author = null;
        try {
            connect();
            Object[] params = {alias};
            resultSet = executeQuery(FIND_BY_ALIAS,params);
            author = getResultSet();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return author;
    }

    public Author findById(int id) throws DaoException {
        Author author = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            author = getResultSet();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return author;
    }

    public List<Author> findAll() throws DaoException {
        List<Author> authors;
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            authors = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return authors;
    }

    @Override
    public List<Author> searchByName(String name) throws DaoException {
        List<Author> authors;
        try {
            connect();
            String wildcards = "%" + name + "%";
            Object[] params = {wildcards};
            resultSet = executeQuery(SEARCH_BY_NAME,params);
            authors = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return authors;
    }

    @Override
    public List<Author> findAllByOffset(int page) throws DaoException {
        List<Author> authors;
        try {
            connect();
            Object[] params = {page*7};
            resultSet = executeQuery(FIND_ALL_BY_OFFSET,params);
            authors = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return authors;
    }

    private Author getResultSet() throws SQLException {
        Author author = null;
        while (resultSet.next()){
            int i = 1;
            author = new Author();
            author.setId(resultSet.getInt(i++));
            author.setAlias(resultSet.getString(i));
        }
        return author;
    }

    private List<Author> getResultSetList() throws SQLException {
        List<Author> authors = new ArrayList<Author>();
        while (resultSet.next()){
            int i = 1;
            Author author = new Author();
            author.setId(resultSet.getInt(i++));
            author.setAlias(resultSet.getString(i));
            authors.add(author);
        }
        return authors;
    }
}
