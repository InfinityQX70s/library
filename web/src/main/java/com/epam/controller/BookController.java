package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.entity.User;
import com.epam.service.api.AuthorService;
import com.epam.service.api.BookOrderService;
import com.epam.service.api.BookService;
import com.epam.service.api.GenreService;
import com.epam.service.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by infinity on 23.02.16.
 */
public class BookController extends BaseController {

    private static final Logger LOG = Logger.getLogger(BookController.class);

    private BookService bookService;
    private GenreService genreService;
    private AuthorService authorService;
    private BookOrderService bookOrderService;
    private Validator validator;

    public BookController(Properties errorProperties, BookService bookService,
                          GenreService genreService, AuthorService authorService,
                          BookOrderService bookOrderService, Validator validator) {
        super(errorProperties);
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookOrderService = bookOrderService;
        this.validator = validator;
    }

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("books"))
                    showBook(request, response);
                else if (uri.length == 4 && uri[3].equals("add"))
                    showFormForBookAdd(request, response);
                else if (uri.length == 5 && uri[4].equals("edit"))
                    showFormForChangeBook(request, response, uri[3]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("add"))
                    addBook(request, response);
                else if (uri.length == 4 && uri[3].equals("search"))
                    searchBook(request, response);
                else if (uri.length == 4 && uri[3].equals("delete"))
                    deleteBook(request, response);
                else if (uri.length == 4 && uri[3].equals("assign"))
                    assignBook(request, response);
                else if (uri.length == 4 && uri[3].equals("change"))
                    changeBook(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
             }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /** Показать список книг постранично
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Book> utilElem = bookService.findAllBooks();
            int pageCount = (int) Math.ceil(utilElem.size()/7.0);
            String page = request.getParameter("page");
            List<Book> books;
            if (page == null){
                page = "1";
                books = bookService.findAllByOffset(0);
            }else{
                books = bookService.findAllByOffset(Integer.parseInt(page)-1);
            }
            Map<Integer,Genre> mapGenres = new HashMap<>();
            Map<Integer,Author> mapAuthor = new HashMap<>();
            for (Book book : books){
                mapGenres.put(book.getGenreId(),genreService.findGenreById(book.getGenreId()));
                mapAuthor.put(book.getAuthorId(),authorService.findAuthorById(book.getAuthorId()));
            }
            request.setAttribute("currentPage", page);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("mapAuthor", mapAuthor);
            request.setAttribute("mapGenres", mapGenres);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/pages/book/book.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Показ формы для создания книги с заполнеными с писками авторов и жанров для выбора
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showFormForBookAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Author> author = authorService.findAllAuthors();
            List<Genre> genre = genreService.findAllGenres();
            request.setAttribute("author", author);
            request.setAttribute("genre", genre);
            request.getRequestDispatcher("/WEB-INF/pages/book/bookAdd.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void assignBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            validator.validateBookNumber(number);
            User user = (User) request.getSession().getAttribute("entity");
            bookOrderService.createBookOrder(Integer.parseInt(number),user.getEmail());
            response.sendRedirect("/orders");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Осуществляем поиск в зависимости от параметра который задан
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void searchBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String value = request.getParameter("value");
            String type = request.getParameter("type");
            List<Book> books = null;
            switch (type){
                case "name":
                    books = bookService.searchByName(value);
                    break;
                case "genre":
                    books = bookService.findBookByGenre(value);
                    break;
                case "author":
                    books = bookService.findBookByAuthor(value);
                    break;
            }
            Map<Integer,Genre> mapGenres = new HashMap<>();
            Map<Integer,Author> mapAuthor = new HashMap<>();
            for (Book book : books){
                mapGenres.put(book.getGenreId(),genreService.findGenreById(book.getGenreId()));
                mapAuthor.put(book.getAuthorId(),authorService.findAuthorById(book.getAuthorId()));
            }
            request.setAttribute("mapAuthor", mapAuthor);
            request.setAttribute("mapGenres", mapGenres);
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/pages/book/book.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            String name = request.getParameter("name");
            String year = request.getParameter("year");
            String count = request.getParameter("count");
            String genre = request.getParameter("genre");
            String author = request.getParameter("author");
            validator.validateBook(number,name,year,count);
            Book book = new Book();
            book.setId(Integer.parseInt(number));
            book.setName(name);
            book.setYear(Integer.parseInt(year));
            book.setCount(Integer.parseInt(count));
            bookService.addBook(book,author,genre);
            response.sendRedirect("/books");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
    private void showFormForChangeBook(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateBookNumber(number);
            Book book = bookService.findBookById(Integer.parseInt(number));
            Author currentAuthor = authorService.findAuthorById(book.getAuthorId());
            Genre currentGenre = genreService.findGenreById(book.getGenreId());
            List<Author> author = authorService.findAllAuthors();
            List<Genre> genre = genreService.findAllGenres();
            request.setAttribute("currentAuthor", currentAuthor);
            request.setAttribute("currentGenre", currentGenre);
            request.setAttribute("author", author);
            request.setAttribute("genre", genre);
            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/pages/book/bookEdit.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
    private void changeBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            String name = request.getParameter("name");
            String year = request.getParameter("year");
            String count = request.getParameter("count");
            String genre = request.getParameter("genre");
            String author = request.getParameter("author");
            validator.validateBook(number,name,year,count);
            Book book = new Book();
            book.setId(Integer.parseInt(number));
            book.setName(name);
            book.setYear(Integer.parseInt(year));
            book.setCount(Integer.parseInt(count));
            bookService.updateBook(book,author,genre);
            response.sendRedirect("/books");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       try {
            String number = request.getParameter("number");
            validator.validateBookNumber(number);
            bookService.deleteBook(Integer.parseInt(number));
            response.sendRedirect("/books");
       } catch (ServiceException e) {
           LOG.warn(e.getMessage());
           showError(e,request,response);
       }catch (ControllerException e) {
           LOG.warn(e.getMessage());
           showError(e,request,response);
       }
    }
}
