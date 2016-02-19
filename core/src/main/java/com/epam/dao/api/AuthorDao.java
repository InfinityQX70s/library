package com.epam.dao.api;

import com.epam.entity.Author;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface AuthorDao {

    void create(Author author);
    void update(Author author);
    void delete(int id);
    Author findByFirstNameAndLastName(String firstName, String lastName);
    Author findById(int id);
    List<Author> findAll();
}
