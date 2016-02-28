package com.epam.dao.api;

import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Book;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface BookDao {

    void create(Book book) throws DaoException;
    void update(Book book) throws DaoException;
    void delete(int id) throws DaoException;
    Book findById(int id) throws DaoException;
    List<Book> findByName(String name) throws DaoException;
    List<Book> findByGenre(int genreId) throws DaoException;
    List<Book> findByAuthor(int authorId) throws DaoException;
    List<Book> findAll() throws DaoException;
    List<Book> searchByName(String name) throws DaoException;
    List<Book> findAllByOffset(int page) throws DaoException;

}
