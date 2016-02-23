package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.BookOrder;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface BookOrderDao {

    void create(BookOrder bookOrder) throws DaoException;
    void update(BookOrder bookOrder) throws DaoException;
    void delete(int id) throws DaoException;
    BookOrder findById(int id) throws DaoException;
    List<BookOrder> findByUser(int userId) throws DaoException;
    List<BookOrder> findByBook(int bookId) throws DaoException;
    List<BookOrder> findByStatus(int statusId) throws DaoException;
    List<BookOrder> findAll() throws DaoException;

}
