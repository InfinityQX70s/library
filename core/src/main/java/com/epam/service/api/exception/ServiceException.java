package com.epam.service.api.exception;

/**
 * Created by infinity on 23.02.16.
 */
public class ServiceException extends Exception {

    private ServiceStatusCode statusCode;

    public ServiceException(String message, ServiceStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, Throwable cause, ServiceStatusCode statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ServiceStatusCode getStatusCode() {
        return statusCode;
    }
}
