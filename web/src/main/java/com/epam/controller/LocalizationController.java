package com.epam.controller;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 29.02.16.
 */
public class LocalizationController implements BaseController{
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
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void setLocalization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = request.getParameter("locale");
        request.getSession().setAttribute("locale", locale);
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer);
    }
}
