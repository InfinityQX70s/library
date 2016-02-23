package com.epam.service.impl;

import com.epam.dao.api.BookDao;
import com.epam.dao.api.GenreDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public class GenreServiceImpl extends TransactionManager{

    private GenreDao genreDao;
    private BookDao bookDao;

    public GenreServiceImpl(GenreDao genreDao, BookDao bookDao) {
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }

    public void addGenre(Genre genre) throws ServiceException {
        try {
            Genre element = genreDao.findById(genre.getId());
            if (element == null){
                genreDao.create(genre);
            }else {
                throw new ServiceException("Genre with such identifier exist", ServiceStatusCode.ALREADY_EXIST);
            }
        }catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void updateGenre(Genre genre) throws ServiceException {
        try {
            Genre element = genreDao.findById(genre.getId());
            if (element != null){
                genreDao.update(genre);
            }else {
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
            }
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public void deleteGenre(int id) throws ServiceException {
        try {
            Genre genre = genreDao.findById(id);
            List<Book> books = bookDao.findByAuthor(id);
            if (genre != null && books.isEmpty()){
                genreDao.delete(id);
            }
            if (genre == null) {
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
            }
            if (!books.isEmpty()){
                throw new ServiceException("Genre assign with books", ServiceStatusCode.ASSIGNED);
            }
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public Genre findGenreById(int id) throws ServiceException {
        try {
            Genre genre = genreDao.findById(id);
            if (genre != null)
                return genre;
            else
                throw new ServiceException("Genre not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    public List<Genre> findAllGenres() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
