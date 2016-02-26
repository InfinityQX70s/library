package com.epam.service.api;

import com.epam.entity.BookOrder;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public interface BookOrderService {

    void createBookOrder(int bookId, String email) throws ServiceException;
    void setStatusBookOrder(int idBookOrder, String statusName) throws ServiceException;
    List<BookOrder> findOrdersByUser(String email) throws ServiceException;
    List<BookOrder> findOrdersByStatus(String statusName) throws ServiceException;
    List<BookOrder> findAllBookOrders() throws ServiceException;
    BookOrder findOrderById(int id) throws ServiceException;
}
