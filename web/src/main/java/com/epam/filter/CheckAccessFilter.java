package com.epam.filter;

import com.epam.controller.exception.ControllerException;
import com.epam.controller.exception.ControllerStatusCode;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by infinity on 27.02.16.
 */
public class CheckAccessFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CheckAccessFilter.class);

    private static final String librarianRole = "librarian";
    private static final String consumerRole = "consumer";
    private static final String rootPath = "/library/";
    private static final String regExpLog = "/library/log.*";
    private static final String regExpReg = "/library/reg.*";
    private static final String regExpLoc = "/library/loc.*";
    private static final String regConsom = "/library/(books|orders).*";
    private static final String regLibra = "/library/(books|orders|authors|genres|users).*";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        String role = (String) req.getSession().getAttribute("role");
        if (role!= null && (role.equals(librarianRole)|| role.equals(consumerRole)) && path.equals(rootPath))
            resp.sendRedirect("/books");
        else if (role == null && !path.matches(regExpLog) && !path.matches(regExpReg))
            resp.sendRedirect("/login");
        else if ((path.matches(regLibra) && role.equals(librarianRole))
                || (path.matches(regConsom) && role.equals(consumerRole))
                || path.matches(regExpLog) || path.matches(regExpReg)|| path.matches(regExpLoc))
            chain.doFilter(request, response);
        else
            try {
                throw new ControllerException("Page not found", ControllerStatusCode.PAGE_NOT_FOUND);
            } catch (ControllerException e) {
                LOG.warn(e.getMessage());
                req.setAttribute("errorMessage", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            }
    }

    @Override
    public void destroy() {

    }
}
