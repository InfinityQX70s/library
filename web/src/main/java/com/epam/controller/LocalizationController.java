package com.epam.controller;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 29.02.16.
 */
public class LocalizationController extends BaseController{

    private static final Logger LOG = Logger.getLogger(LocalizationController.class);

    public LocalizationController(Properties errorProperties) {
        super(errorProperties);
    }

    @Override
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if ("POST".equals(request.getMethod())) {
                if (uri.length == 3 && "localization".equals(uri[2]))
                    setLocalization(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Задаем локализацию и записываем ее в сессию
     * @param request
     * @param response
     * @throws IOException
     */
    private void setLocalization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = request.getParameter("locale");
        request.getSession().setAttribute("locale", locale);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
