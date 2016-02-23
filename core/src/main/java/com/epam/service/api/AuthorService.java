package com.epam.service.api;

import com.epam.entity.Author;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public interface AuthorService {

    void addAuthor(Author author) throws ServiceException;
    void updateAuthor(Author author) throws ServiceException;
    void deleteAuthor(int id) throws ServiceException;
    Author findAuthorById(int id) throws ServiceException;
    List<Author> findAllAuthors() throws ServiceException;
}
