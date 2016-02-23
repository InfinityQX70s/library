package com.epam.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by infinity on 23.02.16.
 */
public class StaticPageFilter implements Filter {

    private static final String regExp = "/(css|js|font|image|WEB-INF).*";
    private static final String rootPath = "/library";

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getRequestURI().substring(req.getContextPath().length());
        if (path.matches(regExp)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher(rootPath + path).forward(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
