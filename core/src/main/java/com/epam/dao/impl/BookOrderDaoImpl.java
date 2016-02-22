package com.epam.dao.impl;

import com.epam.dao.api.BookOrderDao;
import com.epam.entity.Book;
import com.epam.entity.BookOrder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 22.02.16.
 */
public class BookOrderDaoImpl extends ManagerDao implements BookOrderDao{

    private static final String CREATE = "INSERT INTO BookOrder (userId, bookId, statusId) VALUES(?,?,?)";
    private static final String UPDATE = "UPDATE BookOrder SET userId=?, bookId=?, statusId=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM BookOrder WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM BookOrder WHERE id = ?";
    private static final String FIND_BY_USER = "SELECT * FROM BookOrder WHERE userId = ?";
    private static final String FIND_BY_BOOK = "SELECT * FROM BookOrder WHERE bookId = ?";
    private static final String FIND_BY_STATUS = "SELECT * FROM BookOrder WHERE statusId = ?";
    private static final String FIND_ALL = "SELECT * FROM BookOrder";

    public void create(BookOrder bookOrder) {
        try {
            connect();
            Object[] params = {bookOrder.getUserId(),bookOrder.getBookId(),bookOrder.getStatusId()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(BookOrder bookOrder) {
        try {
            connect();
            Object[] params = {bookOrder.getUserId(),bookOrder.getBookId(),bookOrder.getStatusId(),bookOrder.getId()};
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

    public BookOrder findById(int id) {
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
            e.printStackTrace();
        }
        return bookOrder;
    }

    public List<BookOrder> findByUser(int userId) {
        List<BookOrder> bookOrders = new ArrayList<BookOrder>();
        try {
            connect();
            Object[] params = {userId};
            resultSet = executeQuery(FIND_BY_USER,params);
            while (resultSet.next()){
                int i = 1;
                BookOrder bookOrder = new BookOrder();
                bookOrder.setId(resultSet.getInt(i++));
                bookOrder.setUserId(resultSet.getInt(i++));
                bookOrder.setBookId(resultSet.getInt(i++));
                bookOrder.setStatusId(resultSet.getInt(i));
                bookOrders.add(bookOrder);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookOrders;
    }

    public List<BookOrder> findByBook(int bookId) {
        List<BookOrder> bookOrders = new ArrayList<BookOrder>();
        try {
            connect();
            Object[] params = {bookId};
            resultSet = executeQuery(FIND_BY_BOOK,params);
            while (resultSet.next()){
                int i = 1;
                BookOrder bookOrder = new BookOrder();
                bookOrder.setId(resultSet.getInt(i++));
                bookOrder.setUserId(resultSet.getInt(i++));
                bookOrder.setBookId(resultSet.getInt(i++));
                bookOrder.setStatusId(resultSet.getInt(i));
                bookOrders.add(bookOrder);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookOrders;
    }

    public List<BookOrder> findByStatus(int statusId) {
        List<BookOrder> bookOrders = new ArrayList<BookOrder>();
        try {
            connect();
            Object[] params = {statusId};
            resultSet = executeQuery(FIND_BY_STATUS,params);
            while (resultSet.next()){
                int i = 1;
                BookOrder bookOrder = new BookOrder();
                bookOrder.setId(resultSet.getInt(i++));
                bookOrder.setUserId(resultSet.getInt(i++));
                bookOrder.setBookId(resultSet.getInt(i++));
                bookOrder.setStatusId(resultSet.getInt(i));
                bookOrders.add(bookOrder);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookOrders;
    }

    public List<BookOrder> findAll() {
        List<BookOrder> bookOrders = new ArrayList<BookOrder>();
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_BY_STATUS,params);
            while (resultSet.next()){
                int i = 1;
                BookOrder bookOrder = new BookOrder();
                bookOrder.setId(resultSet.getInt(i++));
                bookOrder.setUserId(resultSet.getInt(i++));
                bookOrder.setBookId(resultSet.getInt(i++));
                bookOrder.setStatusId(resultSet.getInt(i));
                bookOrders.add(bookOrder);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookOrders;
    }
}
