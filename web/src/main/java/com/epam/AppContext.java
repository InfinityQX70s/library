package com.epam;

import com.epam.controller.*;
import com.epam.dao.api.*;
import com.epam.dao.impl.*;
import com.epam.service.api.*;
import com.epam.service.impl.*;

/**
 * Created by infinity on 23.02.16.
 */
public class AppContext {

    private static AppContext instance;

    private AuthorDao authorDao;
    private BookDao bookDao;
    private BookOrderDao bookOrderDao;
    private GenreDao genreDao;
    private StatusDao statusDao;
    private UserDao userDao;

    private AuthorService authorService;
    private BookOrderService bookOrderService;
    private BookService bookService;
    private GenreService genreService;
    private UserService userService;

    private AuthorController authorController;
    private BookController bookController;
    private BookOrderController bookOrderController;
    private ErrorController errorController;
    private GenreController genreController;
    private LoginController loginController;

    private ControllerFactory controllerFactory;

    private Validator validator;

    private AppContext() {
    }

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public synchronized AuthorDao getAuthorDao() {
        if (authorDao == null) {
            authorDao = new AuthorDaoImpl();
        }
        return authorDao;
    }

    public synchronized BookDao getBookDao() {
        if (bookDao == null) {
            bookDao = new BookDaoImpl();
        }
        return bookDao;
    }

    public synchronized BookOrderDao getBookOrderDao() {
        if (bookOrderDao == null){
            bookOrderDao = new BookOrderDaoImpl();
        }
        return bookOrderDao;
    }

    public synchronized GenreDao getGenreDao() {
        if (genreDao == null){
            genreDao = new GenreDaoImpl();
        }
        return genreDao;
    }

    public synchronized StatusDao getStatusDao() {
        if (statusDao == null){
            statusDao = new StatusDaoImpl();
        }
        return statusDao;
    }

    public synchronized UserDao getUserDao() {
        if (userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    public synchronized AuthorService getAuthorService() {
        if (authorService == null){
            authorService = new AuthorServiceImpl(getAuthorDao(),getBookDao());
        }
        return authorService;
    }

    public synchronized BookOrderService getBookOrderService() {
        if (bookOrderService == null){
            bookOrderService = new BookOrderServiceImpl(getBookDao(),getBookOrderDao(),
                    getUserDao(),getStatusDao());
        }
        return bookOrderService;
    }

    public synchronized BookService getBookService() {
        if (bookService == null){
            bookService = new BookServiceImpl(getBookDao(),getGenreDao(),getAuthorDao(),getBookOrderDao());
        }
        return bookService;
    }

    public synchronized GenreService getGenreService() {
        if (genreService == null){
            genreService = new GenreServiceImpl(getGenreDao(),getBookDao());
        }
        return genreService;
    }

    public synchronized UserService getUserService() {
        if (userService == null){
            userService = new UserServiceImpl(getUserDao());
        }
        return userService;
    }

    public synchronized AuthorController getAuthorController() {
        if (authorController == null){
            authorController = new AuthorController();
        }
        return authorController;
    }

    public synchronized BookController getBookController() {
        if (bookController == null){
            bookController = new BookController();
        }
        return bookController;
    }

    public synchronized BookOrderController getBookOrderController() {
        if (bookOrderController == null){
            bookOrderController = new BookOrderController();
        }
        return bookOrderController;
    }

    public synchronized ErrorController getErrorController() {
        if (errorController == null){
            errorController = new ErrorController();
        }
        return errorController;
    }

    public synchronized GenreController getGenreController() {
        if (genreController == null){
            genreController = new GenreController();
        }
        return genreController;
    }

    public synchronized LoginController getLoginController() {
        if (loginController == null){
            loginController = new LoginController();
        }
        return loginController;
    }

    public synchronized ControllerFactory getControllerFactory() {
        if (controllerFactory == null){
            controllerFactory = new ControllerFactory();
        }
        return controllerFactory;
    }

    public synchronized Validator getValidator() {
        if (validator == null){
            validator = new Validator();
        }
        return validator;
    }
}
