package com.epam;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;

/**
 * Created by infinity on 23.02.16.
 */
public class Validator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void validateGenreAuthorAndrOrderNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Author, Genre or Order number format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateEmail(String email) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateUser(String email, String password, String firstName, String lastName) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.VALIDATE);
        }
        if (password.isEmpty()){
            throw new ControllerException("Password field is empty", ControllerStatusCode.VALIDATE);
        }
        if(firstName.isEmpty()){
            throw new ControllerException("First name field is empty", ControllerStatusCode.VALIDATE);
        }
        if (lastName.isEmpty()){
            throw new ControllerException("Last name field is empty", ControllerStatusCode.VALIDATE);
        }
    }

    public void validateGenreAndAuthor(String number, String alias) throws ControllerException {
        if (alias.isEmpty())
            throw new ControllerException("Author or Genre field is empty", ControllerStatusCode.VALIDATE);
        validateGenreAuthorAndrOrderNumber(number);
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

    public void validateOrderNumber(String number) throws ControllerException {
        try {
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new ControllerException("Order number format exception", ControllerStatusCode.VALIDATE);
        }
    }
}
