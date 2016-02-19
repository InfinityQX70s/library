package com.epam.dao.api;

import com.epam.entity.Book;
import com.epam.entity.BookOrder;

/**
 * Created by infinity on 19.02.16.
 */
public interface BookOrderDao {

    void create(BookOrder bookOrder);
    void update(BookOrder bookOrder);
    void delete(int id);

}
