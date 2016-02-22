package com.epam.dao.impl;

import com.epam.dao.api.BookDao;
import com.epam.entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class BookDaoImpl extends ManagerDao implements BookDao {

    private static final String CREATE = "INSERT INTO Book (name, count, year,  authorId, genreId) VALUES(?,?,?,?,?)";
    private static final String UPDATE = "UPDATE Book SET name=?, count=?, year=?, authorId=?, genreId=? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Book WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Book WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT * FROM Book WHERE name = ?";
    private static final String FIND_BY_AUTHOR = "SELECT * FROM Book WHERE authorId = ?";
    private static final String FIND_BY_GENRE = "SELECT * FROM Book WHERE genreId = ?";
    private static final String FIND_ALL = "SELECT * FROM Book";

    public void create(Book book) {
        try {
            connect();
            Object[] params = {book.getName(),book.getCount(),book.getYear(),book.getAuthorId(),book.getGenreId()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Book book) {
        try {
            connect();
            Object[] params = {book.getName(),book.getCount(),book.getYear(),book.getAuthorId(),book.getGenreId(),book.getId()};
            execute(UPDATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            connect();
            Object[] params = {id};
            execute(DELETE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book findById(int id) {
        Book book = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    public List<Book> findByName(String name) {
        List<Book> books = new ArrayList<Book>();
        try {
            connect();
            Object[] params = {name};
            resultSet = executeQuery(FIND_BY_NAME,params);
            while (resultSet.next()){
                int i = 1;
                Book book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
                books.add(book);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> findByGenre(int genreId) {
        List<Book> books = new ArrayList<Book>();
        try {
            connect();
            Object[] params = {genreId};
            resultSet = executeQuery(FIND_BY_GENRE,params);
            while (resultSet.next()){
                int i = 1;
                Book book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
                books.add(book);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> findByAuthor(int authorId) {
        List<Book> books = new ArrayList<Book>();
        try {
            connect();
            Object[] params = {authorId};
            resultSet = executeQuery(FIND_BY_AUTHOR,params);
            while (resultSet.next()){
                int i = 1;
                Book book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
                books.add(book);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> findAll() {
        List<Book> books = new ArrayList<Book>();
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            while (resultSet.next()){
                int i = 1;
                Book book = new Book();
                book.setId(resultSet.getInt(i++));
                book.setName(resultSet.getString(i++));
                book.setCount(resultSet.getInt(i++));
                book.setYear(resultSet.getInt(i++));
                book.setAuthorId(resultSet.getInt(i++));
                book.setGenreId(resultSet.getInt(i));
                books.add(book);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
