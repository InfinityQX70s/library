package com.epam.service.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.dao.api.BookDao;
import com.epam.dao.api.BookOrderDao;
import com.epam.dao.api.GenreDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.dao.impl.BookOrderDaoImpl;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.entity.BookOrder;
import com.epam.entity.Genre;
import com.epam.service.api.BookService;
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
public class BookServiceImplTest {

    private BookDao bookDao;
    private GenreDao genreDao;
    private AuthorDao authorDao;
    private BookOrderDao bookOrderDao;
    private BookService bookService;

    private Author getAuthor(){
        Author author = new Author();
        author.setId(20);
        author.setAlias("Булгаков");
        return author;
    }

    private Genre getGenre(){
        Genre genre = new Genre();
        genre.setId(20);
        genre.setName("Ужасы");
        return genre;
    }

    private Book getBook(){
        Book book = new Book();
        book.setId(30);
        book.setName("Тест");
        book.setCount(3);
        book.setYear(1870);
        return book;
    }

    @Before
    public void setUp() throws Exception {
        bookDao = Mockito.mock(BookDao.class);
        genreDao = Mockito.mock(GenreDao.class);
        authorDao = Mockito.mock(AuthorDao.class);
        bookOrderDao = Mockito.mock(BookOrderDao.class);
        bookService = new BookServiceImpl(bookDao,genreDao,authorDao,bookOrderDao);
    }

    @Test
    public void testAddBook() throws Exception {
        Mockito.when(authorDao.findByAlias("Булгаков")).thenReturn(getAuthor());
        Mockito.when(genreDao.findByName("Ужасы")).thenReturn(getGenre());
        Mockito.when(bookDao.findById(30)).thenReturn(null);
        bookService.addBook(getBook(),"Булгаков","Ужасы");
    }

    @Test
    public void testAddBookExceptionBookExist() throws DaoException {
        try {
            Mockito.when(authorDao.findByAlias("Булгаков")).thenReturn(getAuthor());
            Mockito.when(genreDao.findByName("Ужасы")).thenReturn(getGenre());
            Mockito.when(bookDao.findById(30)).thenReturn(getBook());
            bookService.addBook(getBook(),"Булгаков","Ужасы");
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.BOOK_ALREADY_EXIST,e.getStatusCode());
        }
    }

    @Test
    public void testAddBookExceptionGenreNotFound() throws DaoException {
        try {
            Mockito.when(authorDao.findByAlias("Булгаков")).thenReturn(getAuthor());
            Mockito.when(genreDao.findByName("Ужасы")).thenReturn(null);
            Mockito.when(bookDao.findById(30)).thenReturn(null);
            bookService.addBook(getBook(),"Булгаков","Ужасы");
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.GENRE_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testAddBookExceptionAuthorNotFound() throws DaoException {
        try {
            Mockito.when(authorDao.findByAlias("Булгаков")).thenReturn(null);
            Mockito.when(genreDao.findByName("Ужасы")).thenReturn(getGenre());
            Mockito.when(bookDao.findById(30)).thenReturn(null);
            bookService.addBook(getBook(),"Булгаков","Ужасы");
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.AUTHOR_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testUpdateBook() throws Exception {
        Mockito.when(authorDao.findByAlias("Булгаков")).thenReturn(getAuthor());
        Mockito.when(genreDao.findByName("Ужасы")).thenReturn(getGenre());
        Mockito.when(bookDao.findById(30)).thenReturn(getBook());
        bookService.updateBook(getBook(),"Булгаков","Ужасы");
    }

    @Test
    public void testDeleteBook() throws Exception {
        Mockito.when(bookDao.findById(30)).thenReturn(getBook());
        Mockito.when(bookOrderDao.findByBook(30)).thenReturn(new ArrayList<BookOrder>());
        bookService.deleteBook(30);
    }

    @Test
    public void testDeleteBookExceptionNotFound() throws DaoException {
        try {
            Mockito.when(bookDao.findById(30)).thenReturn(null);
            Mockito.when(bookOrderDao.findByBook(30)).thenReturn(new ArrayList<BookOrder>());
            bookService.deleteBook(30);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.BOOK_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testDeleteBookExceptionAssign() throws DaoException {
        try {
            List<BookOrder> bookOrders = new ArrayList<>();
            bookOrders.add(new BookOrder());
            Mockito.when(bookDao.findById(30)).thenReturn(getBook());
            Mockito.when(bookOrderDao.findByBook(30)).thenReturn(bookOrders);
            bookService.deleteBook(30);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.BOOK_ASSIGNED,e.getStatusCode());
        }
    }

}