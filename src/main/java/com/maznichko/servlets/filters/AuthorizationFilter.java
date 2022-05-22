package com.maznichko.servlets.filters;

import com.maznichko.services.Path;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/LoginServlet")
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("CUSTOMER")) {
                request.getRequestDispatcher(Path.CUSTOMER_SERVLET)
                        .forward(request, response);
            }
            else if (session.getAttribute("role").equals("MANAGER")) {
                request.getRequestDispatcher(Path.MANAGER_SERVLET)
                        .forward(request, response);
            }
            else if (session.getAttribute("role").equals("MASTER")) {
                request.getRequestDispatcher(Path.MASTER_SERVLET)
                        .forward(request, response);
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}

