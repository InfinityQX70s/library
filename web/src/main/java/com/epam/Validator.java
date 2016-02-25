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

    public void validateBook(String number, String name, String year, String count) throws ControllerException{
        validateBookNumber(number);
        if (name.isEmpty()){
            throw new ControllerException("Book name field is empty", ControllerStatusCode.VALIDATE);
        }
        try {
            int validYear = Integer.parseInt(year);
            if (validYear > 2016 || validYear < 0)
                throw new ControllerException("Book year format exception", ControllerStatusCode.VALIDATE);
        }catch (NumberFormatException e){
            throw new ControllerException("Book count format exception", ControllerStatusCode.VALIDATE);
        }
        try {
            Integer.parseInt(count);
        }catch (NumberFormatException e){
            throw new ControllerException("Book count format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateBookNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Book number format exception", ControllerStatusCode.VALIDATE);
        }
    }
}
