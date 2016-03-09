package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.Genre;
import com.epam.service.api.AuthorService;
import com.epam.service.api.GenreService;
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
public class GenreController implements BaseController {

    private static final Logger LOG = Logger.getLogger(GenreController.class);

    private AppContext appContext = AppContext.getInstance();
    private GenreService genreService = appContext.getGenreService();
    private Validator validator = appContext.getValidator();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("genres"))
                    showGenre(request, response);
                else if (uri.length == 4 && uri[3].equals("add"))
                    showFormForGenreAdd(request, response);
                else if (uri.length == 5 && uri[4].equals("edit"))
                    showFormForChangeGenre(request, response, uri[3]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("add"))
                    addGenre(request, response);
                else if (uri.length == 4 && uri[3].equals("delete"))
                    deleteGenre(request, response);
                else if (uri.length == 4 && uri[3].equals("change"))
                    changeGenre(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Genre> utilElem = genreService.findAllGenres();
            int pageCount = (int) Math.ceil(utilElem.size()/7.0);
            String page = request.getParameter("page");
            List<Genre> genres;
            if (page == null){
                page = "1";
                genres = genreService.findAllByOffset(0);
            }else{
                genres = genreService.findAllByOffset(Integer.parseInt(page)-1);
            }
            request.setAttribute("currentPage", page);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("genres", genres);
            request.getRequestDispatcher("/WEB-INF/pages/genre/genre.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showFormForGenreAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/genre/genreAdd.jsp").forward(request, response);
    }

    private void addGenre(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String number = request.getParameter("number");
            String name = request.getParameter("name");
            validator.validateGenreAndAuthor(number,name);
            Genre genre = new Genre();
            genre.setId(Integer.parseInt(number));
            genre.setName(name);
            genreService.addGenre(genre);
            response.sendRedirect("/genres");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void deleteGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            validator.validateGenreAuthorAndrOrderNumber(number);
            genreService.deleteGenre(Integer.parseInt(number));
            response.sendRedirect("/genres");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showFormForChangeGenre(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateGenreAuthorAndrOrderNumber(number);
            Genre genre = genreService.findGenreById(Integer.parseInt(number));
            request.setAttribute("genre", genre);
            request.getRequestDispatcher("/WEB-INF/pages/genre/genreEdit.jsp").forward(request, response);
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void changeGenre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            String name = request.getParameter("name");
            validator.validateGenreAndAuthor(number,name);
            Genre genre = new Genre();
            genre.setId(Integer.parseInt(number));
            genre.setName(name);
            genreService.updateGenre(genre);
            response.sendRedirect("/genres");
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
