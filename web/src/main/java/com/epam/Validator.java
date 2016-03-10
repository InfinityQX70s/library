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
            int a = Integer.parseInt(number);
            if (a <= 0)
                throw new ControllerException("Number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }catch (NumberFormatException e){
            throw new ControllerException("Number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }
    }

    public void validateEmail(String email) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.EMAIL_FORMAT);
        }
    }

    public void validateUser(String email, String password, String firstName, String lastName) throws ControllerException {
        if (!email.matches(EMAIL_PATTERN)){
            throw new ControllerException("User email format exception", ControllerStatusCode.EMAIL_FORMAT);
        }
        if (password.isEmpty()){
            throw new ControllerException("Password field is empty", ControllerStatusCode.EMPTY_FIELD);
        }
        if(firstName.isEmpty()){
            throw new ControllerException("First name field is empty", ControllerStatusCode.EMPTY_FIELD);
        }
        if (lastName.isEmpty()){
            throw new ControllerException("Last name field is empty", ControllerStatusCode.EMPTY_FIELD);
        }
    }

    public void validateGenreAndAuthor(String number, String alias) throws ControllerException {
        if (alias.isEmpty())
            throw new ControllerException("Author or Genre field is empty", ControllerStatusCode.EMPTY_FIELD);
        validateGenreAuthorAndrOrderNumber(number);
    }

    public void validateBook(String number, String name, String year, String count) throws ControllerException{
        validateBookNumber(number);
        if (name.isEmpty()){
            throw new ControllerException("Book name field is empty", ControllerStatusCode.EMPTY_FIELD);
        }
        try {
            int validYear = Integer.parseInt(year);
            if (validYear > 2016 || validYear < 0)
                throw new ControllerException("Book year format exception", ControllerStatusCode.BOOK_YEAR);
        }catch (NumberFormatException e){
            throw new ControllerException("Book year format exception", ControllerStatusCode.BOOK_YEAR);
        }
        try {
            int a = Integer.parseInt(count);
            if (a <= 0)
                throw new ControllerException("Book count format exception", ControllerStatusCode.BOOK_COUNT_FORMAT);
        }catch (NumberFormatException e){
            throw new ControllerException("Book count format exception", ControllerStatusCode.BOOK_COUNT_FORMAT);
        }
    }

    public void validateBookNumber(String number) throws ControllerException {
        try {
            int a = Integer.parseInt(number);
            if (a <= 0)
                throw new ControllerException("Number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }catch (NumberFormatException e){
            throw new ControllerException("Book number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }
    }

    public void validateOrderNumber(String number) throws ControllerException {
        try {
            int a = Integer.parseInt(number);
            if (a <= 0)
                throw new ControllerException("Number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }catch (NumberFormatException e){
            throw new ControllerException("Order number format exception", ControllerStatusCode.NUMBER_FORMAT);
        }
    }
}
