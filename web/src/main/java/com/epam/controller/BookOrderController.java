package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.*;
import com.epam.service.api.*;
import com.epam.service.api.exception.ServiceException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by infinity on 23.02.16.
 */
public class BookOrderController implements BaseController {

    private AppContext appContext = AppContext.getInstance();
    private BookService bookService = appContext.getBookService();
    private UserService userService = appContext.getUserService();
    private StatusService statusService  = appContext.getStatusService();
    private BookOrderService bookOrderService = appContext.getBookOrderService();
    private Validator validator = appContext.getValidator();

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("orders"))
                    showOrder(request, response);
                else if (uri.length == 4 && uri[3].equals("add"))
                    showFormForOrderAdd(request, response);
                else if (uri.length == 5 && uri[4].equals("edit"))
                    showFormForChangeOrder(request, response, uri[3]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("add"))
                    addOrder(request, response);
                else if (uri.length == 4 && uri[3].equals("change"))
                    changeOrder(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<BookOrder> bookOrders = bookOrderService.findAllBookOrders();
            Map<Integer,User> mapUser = new HashMap<>();
            Map<Integer,Status> mapStatus = new HashMap<>();
            Map<Integer,Book> mapBook = new HashMap<>();
            for (BookOrder bookOrder : bookOrders){
                mapUser.put(bookOrder.getUserId(),userService.findUserById(bookOrder.getUserId()));
                mapStatus.put(bookOrder.getStatusId(),statusService.findById(bookOrder.getStatusId()));
                mapBook.put(bookOrder.getBookId(),bookService.findBookById(bookOrder.getBookId()));
            }
            request.setAttribute("mapUser", mapUser);
            request.setAttribute("mapStatus", mapStatus);
            request.setAttribute("mapBook", mapBook);
            request.setAttribute("bookOrders", bookOrders);
            request.getRequestDispatcher("/WEB-INF/pages/order/order.jsp").forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void showFormForOrderAdd(HttpServletRequest request, HttpServletResponse response){

    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response){

    }

    private void showFormForChangeOrder(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateOrderNumber(number);
            BookOrder bookOrder = bookOrderService.findOrderById(Integer.parseInt(number));
            User user = userService.findUserById(bookOrder.getUserId());
            Book book = bookService.findBookById(bookOrder.getBookId());
            List<Status> status = statusService.findAll();
            request.setAttribute("bookOrder", bookOrder);
            request.setAttribute("user", user);
            request.setAttribute("book", book);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/WEB-INF/pages/order/orderEdit.jsp").forward(request, response);
        } catch (ServiceException | ControllerException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }

    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            String status = request.getParameter("status");
            validator.validateOrderNumber(number);
            bookOrderService.setStatusBookOrder(Integer.parseInt(number),status);
            response.sendRedirect("/orders");
        } catch (ServiceException | ControllerException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        }
    }
}
