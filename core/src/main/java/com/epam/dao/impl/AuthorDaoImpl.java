package com.epam.dao.impl;

import com.epam.dao.api.AuthorDao;
import com.epam.entity.Author;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public class AuthorDaoImpl extends ManagerDao implements AuthorDao {

    private static final String CREATE = "INSERT INTO Author (firstName, lastName) VALUES(?,?)";
    private static final String UPDATE = "UPDATE Author SET firstName = ?, lastName = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Author WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM Author WHERE id = ?";
    private static final String FIND_BY_FIRST_NAME_AND_LAST_NAME = "SELECT * FROM Author WHERE firstName = ? AND lastName = ?";
    private static final String FIND_ALL = "SELECT * FROM Author";

    public void create(Author author) {
        try {
            connect();
            Object[] params = {author.getFirstName(),author.getLastName()};
            execute(CREATE,params);
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Author author) {
        try {
            connect();
            Object[] params = {author.getFirstName(),author.getLastName(),author.getId()};
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

    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        Author author = null;
        try {
            connect();
            Object[] params = {firstName,lastName};
            resultSet = executeQuery(FIND_BY_FIRST_NAME_AND_LAST_NAME,params);
            while (resultSet.next()){
                int i = 1;
                author = new Author();
                author.setId(resultSet.getInt(i++));
                author.setFirstName(resultSet.getString(i++));
                author.setLastName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public Author findById(int id) {
        Author author = null;
        try {
            connect();
            Object[] params = {id};
            resultSet = executeQuery(FIND_BY_ID,params);
            while (resultSet.next()){
                int i = 1;
                author = new Author();
                author.setId(resultSet.getInt(i++));
                author.setFirstName(resultSet.getString(i++));
                author.setLastName(resultSet.getString(i));
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    public List<Author> findAll() {
        List<Author> authors = new ArrayList<Author>();
        try {
            connect();
            Object[] params = {};
            resultSet = executeQuery(FIND_ALL,params);
            while (resultSet.next()){
                int i = 1;
                Author author = new Author();
                author.setId(resultSet.getInt(i++));
                author.setFirstName(resultSet.getString(i++));
                author.setLastName(resultSet.getString(i));
                authors.add(author);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
