package com.maznichko.filters;

import com.maznichko.services.Path;
import com.maznichko.servlets.MasterServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/LoginServlet")
public class AuthorizationFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        log.info("redirect filter is working...");
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("CUSTOMER")) {
               resp.sendRedirect(Path.CUSTOMER_SERVLET);
            }
            else if (session.getAttribute("role").equals("MANAGER")) {
                resp.sendRedirect(Path.MANAGER_SERVLET);
            }
            else if (session.getAttribute("role").equals("MASTER")) {
                resp.sendRedirect(Path.MASTER_SERVLET);
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}

