package com.epam.dao.impl;

import com.epam.dao.api.BookDao;
import com.epam.entity.Book;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class BookDaoImpl extends ManagerDao implements BookDao {

    private static final String CREATE = "INSERT INTO Author (firstName, lastName) VALUES(?,?)";
    private static final String UPDATE = "UPDATE Author SET firstName = ?, lastName = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Author WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Author WHERE id = ?";
    private static final String FIND_BY_FIRST_NAME_AND_LAST_NAME = "SELECT * FROM Author WHERE firstName = ? AND lastName = ?";
    private static final String FIND_ALL = "SELECT * FROM Author";

    public void create(Book book) {

    }

    public void update(Book book) {

    }

    public void delete(int id) {

    }

    public Book findById(int id) {
        return null;
    }

    public Book findByName(String name) {
        return null;
    }

    public List<Book> findByGenre(int genreId) {
        return null;
    }

    public List<Book> findByAuthor(int authorId) {
        return null;
    }

    public List<Book> findAll() {
        return null;
    }
}
