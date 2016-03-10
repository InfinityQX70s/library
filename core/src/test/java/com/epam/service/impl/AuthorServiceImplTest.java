package com.epam.service.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.dao.api.BookDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.service.api.AuthorService;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by infinity on 05.03.16.
 */
public class AuthorServiceImplTest {

    private AuthorDao authorDao;
    private BookDao bookDao;
    private AuthorService authorService;

    private Author getAuthor(){
        Author author = new Author();
        author.setId(20);
        author.setAlias("Булгаков");
        return author;
    }

    @Before
    public void setUp() throws Exception {
        authorDao = Mockito.mock(AuthorDao.class);
        bookDao = Mockito.mock(BookDao.class);
        authorService = new AuthorServiceImpl(authorDao,bookDao);
    }

    @Test
    public void testAddAuthor() throws Exception {
        Author author = getAuthor();
        Mockito.when(authorDao.findById(20)).thenReturn(null);
        authorService.addAuthor(author);
        Mockito.verify(authorDao,Mockito.times(1)).create(author);
    }

    @Test
    public void testAddAuthorExceptionAuthorExist() throws DaoException{
        try {
            Author author = getAuthor();
            Mockito.when(authorDao.findById(20)).thenReturn(author);
            authorService.addAuthor(author);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.AUTHOR_ALREADY_EXIST,e.getStatusCode());
        }
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = getAuthor();
        Mockito.when(authorDao.findById(20)).thenReturn(author);
        authorService.updateAuthor(author);
        Mockito.verify(authorDao,Mockito.times(1)).update(author);
    }

    @Test
    public void testUpdateAuthorExceptionAuthorNotFound() throws DaoException{
        try {
            Author author = getAuthor();
            Mockito.when(authorDao.findById(20)).thenReturn(null);
            authorService.updateAuthor(author);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.AUTHOR_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author = getAuthor();
        Mockito.when(authorDao.findById(20)).thenReturn(author);
        Mockito.when(bookDao.findByAuthor(20)).thenReturn(new ArrayList<Book>());
        authorService.deleteAuthor(20);
        Mockito.verify(authorDao,Mockito.times(1)).delete(20);
    }

    @Test
    public void testDeleteAuthorExceptionAuthorNotFound() throws DaoException{
        try {
            Mockito.when(authorDao.findById(20)).thenReturn(null);
            Mockito.when(bookDao.findByAuthor(20)).thenReturn(new ArrayList<Book>());
            authorService.deleteAuthor(20);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.AUTHOR_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testDeleteAuthorExceptionAuthorAssigned() throws DaoException{
        try {
            List<Book> books = new ArrayList<>();
            books.add(new Book());
            Author author = getAuthor();
            Mockito.when(authorDao.findById(20)).thenReturn(author);
            Mockito.when(bookDao.findByAuthor(20)).thenReturn(books);
            authorService.deleteAuthor(20);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.AUTHOR_ASSIGNED,e.getStatusCode());
        }
    }

}