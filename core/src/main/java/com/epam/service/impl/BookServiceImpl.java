package com.epam.service.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.dao.api.BookDao;
import com.epam.dao.api.BookOrderDao;
import com.epam.dao.api.GenreDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.entity.BookOrder;
import com.epam.entity.Genre;
import com.epam.service.api.BookService;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public class BookServiceImpl extends TransactionManager implements BookService{

    private BookDao bookDao;
    private GenreDao genreDao;
    private AuthorDao authorDao;
    private BookOrderDao bookOrderDao;

    public void addBook(Book book, String alias, String genreName) throws ServiceException {
        try {
            Genre genre = genreDao.findByName(genreName);
            Author author = authorDao.findByAlias(alias);
            Book element = bookDao.findById(book.getId());
            if (element == null && author != null && genre != null){
                book.setAuthorId(author.getId());
                book.setGenreId(genre.getId());
                bookDao.create(book);
            }
            if (element != null)
                throw new ServiceException("Book with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            if (author == null)
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
            if (genre == null)
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void updateBook(Book book, String alias, String genreName) throws ServiceException {
        try {
            Genre genre = genreDao.findByName(genreName);
            Author author = authorDao.findByAlias(alias);
            Book element = bookDao.findById(book.getId());
            if (element != null && author != null && genre != null){
                book.setAuthorId(author.getId());
                book.setGenreId(genre.getId());
                bookDao.update(book);
            }
            if (element == null)
                throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
            if (author == null)
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
            if (genre == null)
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void deleteBook(int id) throws ServiceException {
        try {
            Book book = bookDao.findById(id);
            List<BookOrder> bookOrders = bookOrderDao.findByBook(id);
            if (book != null && bookOrders.isEmpty()){
                bookDao.delete(id);
            }
            if (book == null)
                throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
            if (!bookOrders.isEmpty())
                throw new ServiceException("Book assign with orders", ServiceStatusCode.ASSIGNED);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public Book findBookById(int id) throws ServiceException {
        try {
            Book book = bookDao.findById(id);
            if (book != null)
                return book;
            else
                throw new ServiceException("Book not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Book> findBookByName(String name) throws ServiceException {
        try {
            return bookDao.findByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Book> findBookByGenre(String genreName) throws ServiceException {
        try {
            Genre genre = genreDao.findByName(genreName);
            if (genre != null)
                return bookDao.findByGenre(genre.getId());
            else
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Book> findBookByAuthor(String alias) throws ServiceException {
        try {
            Author author = authorDao.findByAlias(alias);
            if (author != null)
                return bookDao.findByGenre(author.getId());
            else
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Book> findAllBooks() throws ServiceException {
        try {
            return bookDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

}
