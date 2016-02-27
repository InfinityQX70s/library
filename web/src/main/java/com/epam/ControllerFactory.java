package com.epam;

import com.epam.controller.*;

/**
 * Created by infinity on 23.02.16.
 */
public class ControllerFactory {

    private AppContext appContext = AppContext.getInstance();

    private AuthorController authorController = appContext.getAuthorController();
    private BookOrderController bookOrderController = appContext.getBookOrderController();
    private BookController bookController = appContext.getBookController();
    private GenreController genreController = appContext.getGenreController();
    private LoginController loginController = appContext.getLoginController();
    private ErrorController errorController = appContext.getErrorController();
    private RegisterController registerController = appContext.getRegisterController();

    public BaseController getControllerByUri(String uri) {
        if (uri.startsWith("/library/author"))
            return authorController;
        else if (uri.startsWith("/library/genre"))
            return genreController;
        else if (uri.startsWith("/library/book"))
            return bookController;
        else if (uri.startsWith("/library/log"))
            return loginController;
        else if (uri.startsWith("/library/registrat"))
            return registerController;
        else if (uri.startsWith("/library/order"))
            return bookOrderController;
        return errorController;
    }
}
