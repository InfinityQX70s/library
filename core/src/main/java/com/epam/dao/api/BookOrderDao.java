package com.epam.dao.api;

import com.epam.entity.Book;
import com.epam.entity.BookOrder;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface BookOrderDao {

    void create(BookOrder bookOrder);
    void update(BookOrder bookOrder);
    void delete(int id);
    BookOrder findById(int id);
    List<BookOrder> findByUser(int userId);
    List<BookOrder> findByBook(int bookId);
    List<BookOrder> findByStatus(int statusId);
    List<BookOrder> findAll();

}
