package com.epam.dao.impl;

import com.epam.dao.api.BookOrderDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.BookOrder;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 22.02.16.
 */
public class BookOrderDaoImpl extends ConnectionManager implements BookOrderDao{

    private static final Logger LOG = Logger.getLogger(BookOrderDaoImpl.class);

    private static final String CREATE = "INSERT INTO BookOrder (userId, bookId, statusId) VALUES(?,?,?)";
    private static final String UPDATE = "UPDATE BookOrder SET userId=?, bookId=?, statusId=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM BookOrder WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM BookOrder WHERE id = ?";
    private static final String FIND_BY_USER = "SELECT * FROM BookOrder WHERE userId = ?";
    private static final String FIND_BY_BOOK = "SELECT * FROM BookOrder WHERE bookId = ?";
    private static final String FIND_BY_STATUS = "SELECT * FROM BookOrder WHERE statusId = ?";
    private static final String FIND_ALL = "SELECT * FROM BookOrder";

    public void create(BookOrder bookOrder) throws DaoException {
        try {
            connect();
            Object[] params = {bookOrder.getUserId(),bookOrder.getBookId(),bookOrder.getStatusId()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
    }

    public void update(BookOrder bookOrder) throws DaoException {
        try {
            connect();
            Object[] params = {bookOrder.getUserId(),bookOrder.getBookId(),bookOrder.getStatusId(),bookOrder.getId()};
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

    public BookOrder findById(int id) throws DaoException {
        BookOrder bookOrder = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                bookOrder = new BookOrder();
                bookOrder.setId(resultSet.getInt(i++));
                bookOrder.setUserId(resultSet.getInt(i++));
                bookOrder.setBookId(resultSet.getInt(i++));
                bookOrder.setStatusId(resultSet.getInt(i));
            }
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return bookOrder;
    }

    public List<BookOrder> findByUser(int userId) throws DaoException {
        List<BookOrder> bookOrders;
        try {
            connect();
            Object[] params = {userId};
            resultSet = executeQuery(FIND_BY_USER,params);
            bookOrders = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return bookOrders;
    }

    public List<BookOrder> findByBook(int bookId) throws DaoException {
        List<BookOrder> bookOrders;
        try {
            connect();
            Object[] params = {bookId};
            resultSet = executeQuery(FIND_BY_BOOK,params);
            bookOrders = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return bookOrders;
    }

    public List<BookOrder> findByStatus(int statusId) throws DaoException {
        List<BookOrder> bookOrders;
        try {
            connect();
            Object[] params = {statusId};
            resultSet = executeQuery(FIND_BY_STATUS,params);
            bookOrders = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return bookOrders;
    }

    public List<BookOrder> findAll() throws DaoException {
        List<BookOrder> bookOrders;
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            bookOrders = getResultSetList();
            close();
        } catch (SQLException e) {
            LOG.warn(e.getMessage());
            throw new DaoException("Unknown sql exception",e);
        }
        return bookOrders;
    }

    private List<BookOrder> getResultSetList() throws SQLException {
        List<BookOrder> bookOrders = new ArrayList<BookOrder>();
        while (resultSet.next()){
            int i = 1;
            BookOrder bookOrder = new BookOrder();
            bookOrder.setId(resultSet.getInt(i++));
            bookOrder.setUserId(resultSet.getInt(i++));
            bookOrder.setBookId(resultSet.getInt(i++));
            bookOrder.setStatusId(resultSet.getInt(i));
            bookOrders.add(bookOrder);
        }
        return bookOrders;
    }
}
