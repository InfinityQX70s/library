package com.epam.service.impl;

import com.epam.dao.api.BookDao;
import com.epam.dao.api.GenreDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.service.api.GenreService;
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
public class GenreServiceImplTest {

    private GenreDao genreDao;
    private BookDao bookDao;
    private GenreService genreService;

    private Genre getGenre(){
        Genre genre = new Genre();
        genre.setId(20);
        genre.setName("Ужасы");
        return genre;
    }

    @Before
    public void setUp() throws Exception {
        genreDao = Mockito.mock(GenreDao.class);
        bookDao = Mockito.mock(BookDao.class);
        genreService = new GenreServiceImpl(genreDao,bookDao);
    }

    @Test
    public void testAddGenre() throws Exception {
        Genre genre = getGenre();
        Mockito.when(genreDao.findById(20)).thenReturn(null);
        genreService.addGenre(genre);
        Mockito.verify(genreDao,Mockito.times(1)).create(genre);
    }

    @Test
    public void testAddGenreExceptionGenreExist() throws DaoException {
        try {
            Genre genre = getGenre();
            Mockito.when(genreDao.findById(20)).thenReturn(genre);
            genreService.addGenre(genre);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.GENRE_ALREADY_EXIST,e.getStatusCode());
        }
    }

    @Test
    public void testUpdateGenre() throws Exception {
        Genre genre = getGenre();
        Mockito.when(genreDao.findById(20)).thenReturn(genre);
        genreService.updateGenre(genre);
        Mockito.verify(genreDao,Mockito.times(1)).update(genre);
    }

    @Test
    public void testUpdateGenreExceptionGenreNotFound() throws DaoException {
        try {
            Genre genre = getGenre();
            Mockito.when(genreDao.findById(20)).thenReturn(null);
            genreService.updateGenre(genre);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.GENRE_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testDeleteGenre() throws Exception {
        Genre genre = getGenre();
        Mockito.when(genreDao.findById(20)).thenReturn(genre);
        Mockito.when(bookDao.findByGenre(20)).thenReturn(new ArrayList<Book>());
        genreService.deleteGenre(20);
        Mockito.verify(genreDao,Mockito.times(1)).delete(20);
    }

    @Test
    public void testDeleteGenreExceptionGenreNotFound() throws DaoException {
        try {
            Mockito.when(genreDao.findById(20)).thenReturn(null);
            Mockito.when(bookDao.findByGenre(20)).thenReturn(new ArrayList<Book>());
            genreService.deleteGenre(20);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.GENRE_NOT_FOUND,e.getStatusCode());
        }
    }

    @Test
    public void testDeleteGenreExceptionAssign() throws DaoException {
        try {
            Genre genre = getGenre();
            List<Book> books = new ArrayList<>();
            books.add(new Book());
            Mockito.when(genreDao.findById(20)).thenReturn(genre);
            Mockito.when(bookDao.findByGenre(20)).thenReturn(books);
            genreService.deleteGenre(20);
        } catch (ServiceException e) {
            Assert.assertEquals(ServiceStatusCode.GENRE_ASSIGNED,e.getStatusCode());
        }
    }

}