package com.epam.controller;

import com.epam.controller.exception.ControllerException;
import com.epam.service.api.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by infinity on 23.02.16.
 */
public abstract class BaseController {

    private Properties errorProperties;

    /**При создании экземвляра класса в него передается проперти
     * по статус коду ошибки мы можем из файла проперти получить ее описание
     * @param errorProperties
     */
    public BaseController(Properties errorProperties) {
        this.errorProperties = errorProperties;
    }

    /** Метод анализирует запрос и передает его на исполнение одному из методов контроллера
     * @param servletContext
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public abstract void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void showError(ControllerException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMessage", errorProperties.getProperty(e.getControllerStatusCode().name()));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }

    protected void showError(ServiceException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMessage", errorProperties.getProperty(e.getStatusCode().name()));
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }
}
