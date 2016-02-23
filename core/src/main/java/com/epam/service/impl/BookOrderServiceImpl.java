package com.epam.service.impl;

import com.epam.dao.api.BookDao;
import com.epam.dao.api.BookOrderDao;
import com.epam.dao.api.StatusDao;
import com.epam.dao.api.UserDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Book;
import com.epam.entity.BookOrder;
import com.epam.entity.Status;
import com.epam.entity.User;
import com.epam.service.api.AuthorService;
import com.epam.service.api.BookOrderService;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public class BookOrderServiceImpl extends TransactionManager implements BookOrderService{

    private BookDao bookDao;
    private BookOrderDao bookOrderDao;
    private UserDao userDao;
    private StatusDao statusDao;

    private static final String STATUS_OPEN = "open";
    private static final String STATUS_CLOSE = "close";

    public void createBookOrder(int bookId, String email) throws ServiceException {
        try {
            Book book = bookDao.findById(bookId);
            User user = userDao.findByEmail(email);
            Status status = statusDao.findByName(STATUS_OPEN);
            if (book != null && user != null && status != null && book.getCount() > 0) {
                BookOrder bookOrder = new BookOrder();
                bookOrder.setBookId(book.getId());
                bookOrder.setUserId(user.getId());
                bookOrder.setStatusId(status.getId());
                bookOrderDao.create(bookOrder);
                book.setCount(book.getCount() - 1);
                bookDao.update(book);
            }
            if (book == null)
                throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
            if (user == null)
                throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
            if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
            if (book.getCount() == 0)
                throw new ServiceException("Book count too small", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void setStatusBookOrder(int idBookOrder, String statusName) throws ServiceException {
        try {
            BookOrder bookOrder = bookOrderDao.findById(idBookOrder);
            Status status = statusDao.findByName(statusName);
            if (bookOrder != null && status != null) {
                bookOrder.setStatusId(status.getId());
                bookOrderDao.update(bookOrder);
                if (STATUS_CLOSE.equals(status)){
                    Book book = bookDao.findById(bookOrder.getBookId());
                    if (book != null){
                        book.setCount(book.getCount()+1);
                        bookDao.update(book);
                    }else
                        throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
                }
            }
            if (bookOrder == null)
                throw new ServiceException("BookOrder not found", ServiceStatusCode.NOT_FOUND);
            if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<BookOrder> findOrdersByUser(String email) throws ServiceException {
        try {
            User user = userDao.findByEmail(email);
            if (user != null)
                return bookOrderDao.findByUser(user.getId());
            else
                throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<BookOrder> findOrdersByStatus(String statusName) throws ServiceException {
        try {
            Status status = statusDao.findByName(statusName);
            if (status != null)
                return bookOrderDao.findByStatus(status.getId());
            else
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<BookOrder> filnAllBookOrders() throws ServiceException {
        try {
            return bookOrderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
        }
    }

}
