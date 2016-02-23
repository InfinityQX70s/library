package com.epam.controller.exception;

/**
 * Created by infinity on 23.02.16.
 */
public class ControllerException extends Exception{

    private ControllerStatusCode controllerStatusCode;

    public ControllerException(String message, ControllerStatusCode controllerStatusCode) {
        super(message);
        this.controllerStatusCode = controllerStatusCode;
    }

    public ControllerException(String message, Throwable cause, ControllerStatusCode controllerStatusCode) {
        super(message, cause);
        this.controllerStatusCode = controllerStatusCode;
    }

    public ControllerStatusCode getControllerStatusCode() {
        return controllerStatusCode;
    }
}
