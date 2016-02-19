package com.epam.dao.api;

import com.epam.entity.Genre;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface GenreDao {

    void create(Genre genre);
    void update(Genre genre);
    void delete(int id);
    Genre findById(int id);
    Genre findByName(String name);
    List<Genre> findAll();
}
