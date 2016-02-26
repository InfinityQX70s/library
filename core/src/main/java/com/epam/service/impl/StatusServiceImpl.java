package com.epam.service.impl;

import com.epam.dao.api.StatusDao;
import com.epam.dao.api.exception.DaoException;
import com.epam.entity.Status;
import com.epam.service.api.StatusService;
import com.epam.service.api.exception.ServiceException;
import com.epam.service.api.exception.ServiceStatusCode;

import java.util.List;

/**
 * Created by infinity on 26.02.16.
 */
public class StatusServiceImpl extends TransactionManager implements StatusService {

    private StatusDao statusDao;

    public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public Status findById(int id) throws ServiceException {
        try {
            Status status = statusDao.findById(id);
            if (status != null)
                return status;
            else
                throw new ServiceException("Status not found", ServiceStatusCode.NOT_FOUND);
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }

    @Override
    public List<Status> findAll() throws ServiceException {
        try {
            return statusDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Unknown exception", e, ServiceStatusCode.UNKNOWN);
        }
    }
}
