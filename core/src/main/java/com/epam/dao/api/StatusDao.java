package com.epam.dao.api;

import com.epam.entity.Status;

import java.util.List;

/**
 * Created by infinity on 19.02.16.
 */
public interface StatusDao {

    Status findById(int id);
    Status findByName(String name);
    List<Status> findAll();
}
