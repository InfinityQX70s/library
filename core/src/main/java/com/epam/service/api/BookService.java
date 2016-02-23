package com.epam.service.api;

import com.epam.entity.Book;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public interface BookService {

    void addBook(Book book, String alias, String genreName) throws ServiceException;
    void updateBook(Book book, String alias, String genreName) throws ServiceException;
    void deleteBook(int id) throws ServiceException;
    Book findBookById(int id) throws ServiceException;
    List<Book> findBookByName(String name) throws ServiceException;
    List<Book> findBookByGenre(String genreName) throws ServiceException;
    List<Book> findBookByAuthor(String alias) throws ServiceException;
    List<Book> findAllBooks() throws ServiceException;
}
