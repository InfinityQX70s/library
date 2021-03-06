package com.epam.dao.impl;

import com.epam.dao.api.BookDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Book;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class BookDaoImpl extends ConnectionManager implements BookDao {

    private static final Logger LOG = Logger.getLogger(BookDaoImpl.class);

    private static final String CREATE = "INSERT INTO Book (id, name, count, year,  authorId, genreId) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Book SET name=?, count=?, year=?, authorId=?, genreId=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Book WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Book WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM Book WHERE name LIKE ?";
    private static final String FIND_BY_AUTHOR = "SELECT * FROM Book WHERE authorId = ?";
    private static final String FIND_BY_GENRE = "SELECT * FROM Book WHERE genreId = ?";
    private static final String FIND_ALL = "SELECT * FROM Book";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Book WHERE name LIKE ?;";
    private static final String FIND_ALL_BY_OFFSET = "SELECT * FROM Book LIMIT ?,7;";



    public void create(Book book) throws DaoException {
        try {
            connect();
            Object[] params = {book.getId(), book.getName(),book.getCount(),book.getYear(),book.getAuthorId(),book.getGenreId()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void update(Book book) throws DaoException {
        try {
            connect();
            Object[] params = {book.getName(),book.getCount(),book.getYear(),book.getAuthorId(),book.getGenreId(),book.getId()};
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

    public Book findById(int id) throws DaoException {
        Book book = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
            }
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return book;
    }

    public List<Book> findByName(String name) throws DaoException {
        List<Book> books;
        try {
            connect();
            Object[] params = {name};
            resultSet = executeQuery(FIND_BY_NAME,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    public List<Book> findByGenre(int genreId) throws DaoException {
        List<Book> books;
        try {
            connect();
            Object[] params = {genreId};
            resultSet = executeQuery(FIND_BY_GENRE,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    public List<Book> findByAuthor(int authorId) throws DaoException {
        List<Book> books;
        try {
            connect();
            Object[] params = {authorId};
            resultSet = executeQuery(FIND_BY_AUTHOR,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    public List<Book> findAll() throws DaoException {
        List<Book> books;
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    @Override
    public List<Book> searchByName(String name) throws DaoException {
        List<Book> books;
        try {
            connect();
            String wildcards = "%" + name + "%";
            Object[] params = {wildcards};
            resultSet = executeQuery(SEARCH_BY_NAME,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    @Override
    public List<Book> findAllByOffset(int page) throws DaoException {
        List<Book> books;
        try {
            connect();
            Object[] params = {page*7};
            resultSet = executeQuery(FIND_ALL_BY_OFFSET,params);
            books = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return books;
    }

    private List<Book> getResultSetList() throws SQLException {
        List<Book> books = new ArrayList<Book>();
        while (resultSet.next()){
            int i = 1;
            Book book = new Book();
            book.setId(resultSet.getInt(i++));
            book.setName(resultSet.getString(i++));
            book.setCount(resultSet.getInt(i++));
            book.setYear(resultSet.getInt(i++));
            book.setAuthorId(resultSet.getInt(i++));
            book.setGenreId(resultSet.getInt(i));
            books.add(book);
        }
        return books;
    }
}
