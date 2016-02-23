package com.epam;

import com.epam.controller.BaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 23.02.16.
 */
public class FrontController extends HttpServlet {

    private AppContext appContext = AppContext.getInstance();

    private void processRequest(HttpServletRequest
                                          request, HttpServletResponse response)
            throws ServletException, IOException {
        BaseController controller = appContext.getControllerFactory().getControllerByUri(request.getRequestURI());
        controller.execute(getServletContext(), request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
}
