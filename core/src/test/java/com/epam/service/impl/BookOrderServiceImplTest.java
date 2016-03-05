package com.epam.service.impl;

import com.epam.dao.api.BookDao;
import com.epam.dao.api.BookOrderDao;
import com.epam.dao.api.StatusDao;
import com.epam.dao.api.UserDao;
import com.epam.entity.Book;
import com.epam.entity.BookOrder;
import com.epam.entity.Status;
import com.epam.entity.User;
import com.epam.service.api.BookOrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by infinity on 05.03.16.
 */
public class BookOrderServiceImplTest {

    private BookDao bookDao;
    private BookOrderDao bookOrderDao;
    private UserDao userDao;
    private StatusDao statusDao;
    private BookOrderService bookOrderService;

    private Book getBook(){
        Book book = new Book();
        book.setId(30);
        book.setName("Тест");
        book.setCount(3);
        book.setYear(1870);
        return book;
    }

    private User getUser(){
        User user = new User();
        user.setId(20);
        user.setEmail("mazu@ya.ru");
        return user;
    }

    private Status getStatus(){
        Status status = new Status();
        status.setId(20);
        status.setName("open");
        return status;
    }

    private Status getStatusClose(){
        Status status = new Status();
        status.setId(30);
        status.setName("close");
        return status;
    }

    @Before
    public void setUp() throws Exception {
        bookDao = Mockito.mock(BookDao.class);
        bookOrderDao = Mockito.mock(BookOrderDao.class);
        userDao = Mockito.mock(UserDao.class);
        statusDao = Mockito.mock(StatusDao.class);
        bookOrderService = new BookOrderServiceImpl(bookDao,bookOrderDao,userDao,statusDao);
    }

    @Test
    public void testCreateBookOrder() throws Exception {
        Mockito.when(bookDao.findById(30)).thenReturn(getBook());
        Mockito.when(userDao.findByEmail("mazu@ya.ru")).thenReturn(getUser());
        Mockito.when(statusDao.findByName("open")).thenReturn(getStatus());
        bookOrderService.createBookOrder(30,"mazu@ya.ru");
    }

    @Test
    public void testSetStatusBookOrder() throws Exception {
        BookOrder bookOrder = new BookOrder();
        bookOrder.setId(20);
        bookOrder.setStatusId(20);
        bookOrder.setUserId(20);
        bookOrder.setBookId(30);
        Mockito.when(statusDao.findByName("close")).thenReturn(getStatusClose());
        Mockito.when(bookOrderDao.findById(20)).thenReturn(bookOrder);
        Mockito.when(bookDao.findById(30)).thenReturn(getBook());
        bookOrderService.setStatusBookOrder(20,"close");
        Mockito.verify(bookOrderDao,Mockito.times(1)).delete(20);
    }
}