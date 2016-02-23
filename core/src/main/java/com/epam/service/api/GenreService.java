package com.epam.service.api;

import com.epam.entity.Genre;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 23.02.16.
 */
public interface GenreService {

    void addGenre(Genre genre) throws ServiceException;
    void updateGenre(Genre genre) throws ServiceException;
    void deleteGenre(int id) throws ServiceException;
    Genre findGenreById(int id) throws ServiceException;
    List<Genre> findAllGenres() throws ServiceException;
}
