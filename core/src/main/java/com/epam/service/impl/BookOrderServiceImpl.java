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
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public class BookOrderServiceImpl extends TransactionManager implements BookOrderService {

    private static final Logger LOG = Logger.getLogger(BookOrderServiceImpl.class);

    private BookDao bookDao;
    private BookOrderDao bookOrderDao;
    private UserDao userDao;
    private StatusDao statusDao;

    private static final String STATUS_OPEN = "open";
    private static final String STATUS_CLOSE = "close";

    public BookOrderServiceImpl(BookDao bookDao, BookOrderDao bookOrderDao, UserDao userDao, StatusDao statusDao) {
        this.bookDao = bookDao;
        this.bookOrderDao = bookOrderDao;
        this.userDao = userDao;
        this.statusDao = statusDao;
    }

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
            } else if (book == null)
                throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
            else if (user == null)
                throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
            else if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
            else if (book.getCount() == 0)
                throw new ServiceException("Book count too small", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void setStatusBookOrder(int idBookOrder, String statusName) throws ServiceException {
        try {
            BookOrder bookOrder = bookOrderDao.findById(idBookOrder);
            Status status = statusDao.findByName(statusName);
            if (bookOrder != null && status != null && status.getId() != bookOrder.getStatusId()) {
                bookOrder.setStatusId(status.getId());
                bookOrderDao.update(bookOrder);
                if (STATUS_CLOSE.equals(statusName)) {
                    bookOrderDao.delete(bookOrder.getId());
                    Book book = bookDao.findById(bookOrder.getBookId());
                    if (book != null) {
                        book.setCount(book.getCount() + 1);
                        bookDao.update(book);
                    } else
                        throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
                }
            } else if (bookOrder == null)
                throw new ServiceException("BookOrder not found", ServiceStatusCode.NOT_FOUND);
            else if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
            else if (status.getId() == bookOrder.getStatusId())
                throw new ServiceException("BookOrder already has this status", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public void deleteBookOrder(int idBookOrder) throws ServiceException {
        try {
            BookOrder bookOrder = bookOrderDao.findById(idBookOrder);
            if (bookOrder != null) {
                bookOrderDao.delete(bookOrder.getId());
                Book book = bookDao.findById(bookOrder.getBookId());
                if (book != null) {
                    book.setCount(book.getCount() + 1);
                    bookDao.update(book);
                } else
                    throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
            } else if (bookOrder == null)
                throw new ServiceException("BookOrder not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
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
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public BookOrder findOrderById(int id) throws ServiceException {
        try {
            BookOrder bookOrder = bookOrderDao.findById(id);
            if (bookOrder != null)
                return bookOrder;
            else
                throw new ServiceException("Order not found", ServiceStatusCode.NOT_FOUND);
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
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<BookOrder> findAllBookOrders() throws ServiceException {
        try {
            return bookOrderDao.findAll();
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("User not found", ServiceStatusCode.NOT_FOUND);
        }
    }

}
