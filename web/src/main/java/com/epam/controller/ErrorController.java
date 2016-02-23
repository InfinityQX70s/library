package com.epam.controller;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 23.02.16.
 */
public class ErrorController implements BaseController {
    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
        } catch (ControllerException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
