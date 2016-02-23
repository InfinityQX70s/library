package com.epam.service.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.dao.api.BookDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public class AuthorServiceImpl extends TransactionManager {

    private AuthorDao authorDao;
    private BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }

    public void addAuthor(Author author) throws ServiceException {
        try {
            Author element = authorDao.findById(author.getId());
            if (element == null){
                authorDao.create(author);
            }else {
                throw new ServiceException("Author with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            }
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void updateAuthor(Author author) throws ServiceException {
        try {
            Author element = authorDao.findById(author.getId());
            if (element != null){
                authorDao.update(author);
            }else {
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
            }
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void deleteAuthor(int id) throws ServiceException {
        try {
            Author author = authorDao.findById(id);
            List<Book> books = bookDao.findByAuthor(id);
            if (author != null && books.isEmpty()){
                authorDao.delete(id);
            }
            if (author == null) {
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
            }
            if (!books.isEmpty()){
                throw new ServiceException("Author assign with books", ServiceStatusCode.ASSIGNED);
            }
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public Author findAuthorById(int id) throws ServiceException {
        try {
            Author author = authorDao.findById(id);
            if (author != null)
                return author;
            else
                throw new ServiceException("Author not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Author> findAllAuthors() throws ServiceException {
        try {
            return authorDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
