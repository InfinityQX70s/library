package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.User;
import com.epam.service.api.GenreService;
import com.epam.service.api.UserService;
import com.epam.service.api.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 23.02.16.
 */
public class LoginController implements BaseController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private AppContext appContext = AppContext.getInstance();
    private UserService userService = appContext.getUserService();
    private Validator validator = appContext.getValidator();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 3 && "login".equals(uri[2]))
                    showUserLoginForm(request, response);
                else if (uri.length == 3 && "logout".equals(uri[2]))
                    logoutUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 3 && "login".equals(uri[2]))
                    loginUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showUserLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    // /login POST
    private void loginUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            validator.validateEmail(email);
            User user = userService.findUserByEmail(email);
            if (user != null && DigestUtils.md5Hex(password).equals(user.getPassword())) {
                if (user.isLibrarian())
                    req.getSession().setAttribute("role", "librarian");
                else
                    req.getSession().setAttribute("role", "consumer");
                req.getSession().setAttribute("entity", user);
                resp.sendRedirect("/books");
            } else {
                throw new ControllerException("Wrong email or pass", ControllerStatusCode.WRONG_EMAIL_PASS);
            }
        } catch (ServiceException | ControllerException e) {
            LOG.warn(e.getMessage());
            req.setAttribute("error", e);
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    // /logout GET
    private void logoutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("role", null);
        req.getSession().setAttribute("entity", null);
//        req.getSession().invalidate();
        resp.sendRedirect("/login");
    }
}
