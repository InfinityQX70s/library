package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.Author;
import com.epam.service.api.AuthorService;
import com.epam.service.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 23.02.16.
 */
public class AuthorController extends BaseController {

    private static final Logger LOG = Logger.getLogger(AuthorController.class);

    private AuthorService authorService;
    private Validator validator;

    public AuthorController(Properties errorProperties, AuthorService authorService, Validator validator) {
        super(errorProperties);
        this.authorService = authorService;
        this.validator = validator;
    }

    /**Метод получает запрос разбирает его и определеяет какому из методов отдать его на обработку
     * @param servletContext
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("authors"))
                    showAuthor(request, response);
                else if (uri.length == 4 && uri[3].equals("add"))
                    showFormForAuthorAdd(request, response);
                else if (uri.length == 5 && uri[4].equals("edit"))
                    showFormForChangeAuthor(request, response, uri[3]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("add"))
                    addAuthor(request, response);
                else if (uri.length == 4 && uri[3].equals("delete"))
                    deleteAuthor(request, response);
                else if (uri.length == 4 && uri[3].equals("change"))
                    changeAuthor(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Метод выводит постранично всех авторов
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    // /authors GET
    private void showAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Author> utilElem = authorService.findAllAuthors();
            int pageCount = (int) Math.ceil(utilElem.size()/7.0);
            String page = request.getParameter("page");
            List<Author> authors;
            if (page == null){
                page = "1";
                authors = authorService.findAllByOffset(0);
            }else{
                authors = authorService.findAllByOffset(Integer.parseInt(page)-1);
            }
            request.setAttribute("currentPage", page);
            request.setAttribute("authors", authors);
            request.setAttribute("pageCount", pageCount);
            request.getRequestDispatcher("/WEB-INF/pages/author/author.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    // /authors/add GET
    private void showFormForAuthorAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/author/authorAdd.jsp").forward(request, response);
    }

    /** Производит валидацию вводимых данных и создание нового автора в бд
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    // /authors/add POST
    private void addAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String number = request.getParameter("number");
            String alias = request.getParameter("alias");
            validator.validateGenreAndAuthor(number,alias);
            Author author = new Author();
            author.setId(Integer.parseInt(number));
            author.setAlias(alias);
            authorService.addAuthor(author);
            response.sendRedirect("/authors");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Изменение автора
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    // /authors/change POST
    private void changeAuthor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String number = request.getParameter("number");
            String alias = request.getParameter("alias");
            validator.validateGenreAndAuthor(number,alias);
            Author author = new Author();
            author.setId(Integer.parseInt(number));
            author.setAlias(alias);
            authorService.updateAuthor(author);
            response.sendRedirect("/authors");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }

    }

    // /authors/delete POST
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            validator.validateGenreAuthorAndrOrderNumber(number);
            authorService.deleteAuthor(Integer.parseInt(number));
            response.sendRedirect("/authors");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Отображение формы и заполнение ее для изменения автора
     * @param request
     * @param response
     * @param number
     * @throws ServletException
     * @throws IOException
     */
    // /authors/{}/edit GET
    private void showFormForChangeAuthor(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateGenreAuthorAndrOrderNumber(number);
            Author author = authorService.findAuthorById(Integer.parseInt(number));
            request.setAttribute("author", author);
            request.getRequestDispatcher("/WEB-INF/pages/author/authorEdit.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

}
