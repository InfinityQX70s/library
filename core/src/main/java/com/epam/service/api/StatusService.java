package com.epam.service.api;

import com.epam.entity.Status;
import com.epam.service.api.exception.ServiceException;

import java.util.List;

/**
 * Created by infinity on 26.02.16.
 */
public interface StatusService {

    Status findById(int id) throws ServiceException;
    List<Status> findAll() throws ServiceException;
}
