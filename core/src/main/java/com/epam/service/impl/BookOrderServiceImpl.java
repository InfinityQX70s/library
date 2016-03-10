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

import java.sql.SQLException;
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

    /**Создаем заказ на книгу, на определенного пользователя, и уменьшаем количество книг на одну.
     * @param bookId идентификатор книги
     * @param email адрес пользователя, бронирующего книгу
     * @throws ServiceException
     */
    public void createBookOrder(int bookId, String email) throws ServiceException {
        try {
            //begitTransaction();
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
                //commitTransaction();
            } else if (book == null)
                throw new ServiceException("Book not found", ServiceStatusCode.BOOK_NOT_FOUND);
            else if (user == null)
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
            else if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.STATUS_NOT_FOUND);
            else if (book.getCount() == 0)
                throw new ServiceException("Book count too small", ServiceStatusCode.BOOK_COUNT);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    /**Устанавливаем статус для книги, если стату закрыто, то увеличиваем счетчик книг и удаляем статус из базы
     * @param idBookOrder новер заказа
     * @param statusName статус
     * @throws ServiceException
     */
    public void setStatusBookOrder(int idBookOrder, String statusName) throws ServiceException {
        try {
            //begitTransaction();
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
                        //commitTransaction();
                    } else
                        throw new ServiceException("Book not found", ServiceStatusCode.BOOK_NOT_FOUND);
                }
            } else if (bookOrder == null)
                throw new ServiceException("Book Order not found", ServiceStatusCode.BOOK_ORDER_NOT_FOUND);
            else if (status == null)
                throw new ServiceException("Status not found", ServiceStatusCode.STATUS_NOT_FOUND);
            else if (status.getId() == bookOrder.getStatusId())
                throw new ServiceException("Book Order already has this status", ServiceStatusCode.BOOK_ORDER_ALREADY_EXIST);
        } catch (DaoException e) {
            LOG.warn(e.getMessage());
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public void deleteBookOrder(int idBookOrder) throws ServiceException {
        try {
           // begitTransaction();
            BookOrder bookOrder = bookOrderDao.findById(idBookOrder);
            if (bookOrder != null) {
                bookOrderDao.delete(bookOrder.getId());
                Book book = bookDao.findById(bookOrder.getBookId());
                if (book != null) {
                    book.setCount(book.getCount() + 1);
                    bookDao.update(book);
                    //commitTransaction();
                } else
                    throw new ServiceException("Book not found", ServiceStatusCode.BOOK_NOT_FOUND);
            } else if (bookOrder == null)
                throw new ServiceException("BookOrder not found", ServiceStatusCode.BOOK_ORDER_NOT_FOUND);
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
                throw new ServiceException("User not found", ServiceStatusCode.USER_NOT_FOUND);
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
                throw new ServiceException("BookOrder not found", ServiceStatusCode.BOOK_ORDER_NOT_FOUND);
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
                throw new ServiceException("Status not found", ServiceStatusCode.STATUS_NOT_FOUND);
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
            throw new ServiceException("Unknown exception", ServiceStatusCode.UNKNOWN);
        }
    }

}
