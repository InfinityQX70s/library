package com.epam.controller;

import com.epam.AppContext;
import com.epam.Validator;
import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import com.epam.entity.*;
import com.epam.service.api.*;
import com.epam.service.api.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by infinity on 23.02.16.
 */
public class BookOrderController extends BaseController {

    private static final Logger LOG = Logger.getLogger(BookOrderController.class);

    private BookService bookService;
    private UserService userService;
    private StatusService statusService;
    private BookOrderService bookOrderService;
    private Validator validator;

    public BookOrderController(Properties errorProperties, BookService bookService,
                               UserService userService, StatusService statusService,
                               BookOrderService bookOrderService, Validator validator) {
        super(errorProperties);
        this.bookService = bookService;
        this.userService = userService;
        this.statusService = statusService;
        this.bookOrderService = bookOrderService;
        this.validator = validator;
    }

    public void execute(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String[] uri = request.getRequestURI().split("/");
            if (request.getMethod().equals("GET")) {
                if (uri.length == 3 && uri[2].equals("orders"))
                    showOrder(request, response);
                else if (uri.length == 5 && uri[4].equals("edit"))
                    showFormForChangeOrder(request, response, uri[3]);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
            if (request.getMethod().equals("POST")) {
                if (uri.length == 4 && uri[3].equals("delete"))
                    deleteOrder(request, response);
                else if (uri.length == 4 && uri[3].equals("change"))
                    changeOrder(request, response);
                else
                    throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            }
        } catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    /**Удаляем заказ, только для клиентов
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String role = (String) request.getSession().getAttribute("role");
            if (role.equals("consumer")) {
                String number = request.getParameter("number");
                validator.validateGenreAuthorAndrOrderNumber(number);
                bookOrderService.deleteBookOrder(Integer.parseInt(number));
                response.sendRedirect("/orders");
            }else
                throw new ControllerException("Access denied", ControllerStatusCode.ACCESS_DENIED);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }

    }

    /**Показываем заказы, если для клиента то только его, если для библиотекаря, то все
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void showOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String role = (String) request.getSession().getAttribute("role");
            List<BookOrder> bookOrders;
            if (role.equals("consumer")) {
                User user = (User) request.getSession().getAttribute("entity");
                bookOrders = bookOrderService.findOrdersByUser(user.getEmail());
            }else{
                bookOrders = bookOrderService.findAllBookOrders();
            }
            Map<Integer, User> mapUser = new HashMap<>();
            Map<Integer, Status> mapStatus = new HashMap<>();
            Map<Integer, Book> mapBook = new HashMap<>();
            for (BookOrder bookOrder : bookOrders) {
                mapUser.put(bookOrder.getUserId(), userService.findUserById(bookOrder.getUserId()));
                mapStatus.put(bookOrder.getStatusId(), statusService.findById(bookOrder.getStatusId()));
                mapBook.put(bookOrder.getBookId(), bookService.findBookById(bookOrder.getBookId()));
            }
            request.setAttribute("mapUser", mapUser);
            request.setAttribute("mapStatus", mapStatus);
            request.setAttribute("mapBook", mapBook);
            request.setAttribute("bookOrders", bookOrders);
            request.getRequestDispatcher("/WEB-INF/pages/order/order.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void showFormForChangeOrder(HttpServletRequest request, HttpServletResponse response, String number) throws ServletException, IOException {
        try {
            validator.validateOrderNumber(number);
            BookOrder bookOrder = bookOrderService.findOrderById(Integer.parseInt(number));
            User user = userService.findUserById(bookOrder.getUserId());
            Book book = bookService.findBookById(bookOrder.getBookId());
            Status currentStatus = statusService.findById(bookOrder.getStatusId());
            List<Status> status = statusService.findAll();
            request.setAttribute("bookOrder", bookOrder);
            request.setAttribute("user", user);
            request.setAttribute("book", book);
            request.setAttribute("currentStatus", currentStatus);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/WEB-INF/pages/order/orderEdit.jsp").forward(request, response);
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }

    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String number = request.getParameter("number");
            String status = request.getParameter("status");
            validator.validateOrderNumber(number);
            bookOrderService.setStatusBookOrder(Integer.parseInt(number), status);
            response.sendRedirect("/orders");
        } catch (ServiceException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }catch (ControllerException e) {
            LOG.warn(e.getMessage());
            showError(e,request,response);
        }
    }
}
