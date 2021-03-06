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
import java.util.Properties;

/**
 * Created by infinity on 23.02.16.
 */
public class LoginController extends BaseController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private UserService userService;
    private Validator validator;

    public LoginController(Properties errorProperties, UserService userService, Validator validator) {
        super(errorProperties);
        this.userService = userService;
        this.validator = validator;
    }

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
            showError(e,request,response);
        }
    }

    private void showUserLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    /**Осуществляем логин пользователя и устанавливаем ему ег ороль в сессию
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
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
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,req,resp);
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
