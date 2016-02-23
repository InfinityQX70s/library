package com.epam;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;

/**
 * Created by infinity on 23.02.16.
 */
public class Validator {

    public void validateGenreAndAuthorNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Author or Genre number format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateGenreAndAuthor(String number, String alias) throws ControllerException {
        if (alias.isEmpty())
            throw new ControllerException("Author or Genre field is empty", ControllerStatusCode.VALIDATE);
        validateGenreAndAuthorNumber(number);
    }
}
