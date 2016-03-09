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

/**
 * Created by infinity on 23.02.16.
 */
public class AuthorController implements BaseController {

    private static final Logger LOG = Logger.getLogger(AuthorController.class);

    private AppContext appContext = AppContext.getInstance();
    private AuthorService authorService = appContext.getAuthorService();
    private Validator validator = appContext.getValidator();

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
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

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
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    // /authors/add GET
    private void showFormForAuthorAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/author/authorAdd.jsp").forward(request, response);
    }

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
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

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
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }

    }

    // /authors/delete POST
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            validator.validateGenreAuthorAndrOrderNumber(number);
            authorService.deleteAuthor(Integer.parseInt(number));
            response.sendRedirect("/authors");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    // /authors/{}/edit GET
    private void showFormForChangeAuthor(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateGenreAuthorAndrOrderNumber(number);
            Author author = authorService.findAuthorById(Integer.parseInt(number));
            request.setAttribute("author", author);
            request.getRequestDispatcher("/WEB-INF/pages/author/authorEdit.jsp").forward(request, response);
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

}
