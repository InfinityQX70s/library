package com.epam.controller;

import com.epam.AppContext;
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
import java.util.Properties;

/**
 * Created by infinity on 27.02.16.
 */
public class RegisterController extends BaseController{

    private static final Logger LOG = Logger.getLogger(RegisterController.class);

    private UserService userService;
    private Validator validator;

    public RegisterController(Properties errorProperties, UserService userService, Validator validator) {
        super(errorProperties);
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if ("GET".equals(request.getMethod())) {
                if (uri.length == 3 && "registration".equals(uri[2]))
                    showRegisterForm(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 3 && "registration".equals(uri[2]))
                    registerUser(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
    }

    /**Валидируем данные с фармы и осуществляем регистрацию клиентов в бибилотеке
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            user.setLibrarian(false);
            userService.addUser(user);
            response.sendRedirect("/login");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
}
