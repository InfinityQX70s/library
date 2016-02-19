package com.epam.dao.api;

import com.epam.entity.Book;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface BookDao {

    void create(Book book);
    void update(Book book);
    void delete(int id);
    Book findById(int id);
    Book findByName(String name);
    List<Book> findByGenre(int genreId);
    List<Book> findByAuthor(int authorId);
    List<Book> findAll();
}
