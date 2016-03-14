package com.epam.controller;

import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.User;
import com.epam.service.api.UserService;
import com.epam.service.api.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by infinity on 14.03.16.
 */
public class UserController extends BaseController {

    private static final Logger LOG = Logger.getLogger(UserController.class);

    private UserService userService;
    private Validator validator;

    public UserController(Properties errorProperties, UserService userService, Validator validator) {
        super(errorProperties);
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("users"))
                    showUsers(request, response);
                else if (uri.length == 4 && uri[3].equals("add"))
                    showFormForUserAdd(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("add"))
                    addUser(request, response);
                else if (uri.length == 4 && uri[3].equals("delete"))
                    deleteUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void showUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userService.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/pages/user/user.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void showFormForUserAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/user/userAdd.jsp").forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            userService.deleteUser(Integer.parseInt(number));
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            validator.validateUser(email,password,firstName,lastName);
            User user = new User();
            user.setEmail(email);
            user.setPassword(DigestUtils.md5Hex(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setLibrarian(true);
            userService.addUser(user);
            response.sendRedirect("/users");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
}
