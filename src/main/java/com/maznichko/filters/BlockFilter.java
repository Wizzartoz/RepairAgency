package com.maznichko.filters;

import com.maznichko.services.Path;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BlockFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("BLOCK")) {
                req.getRequestDispatcher(Path.BLOCK_JSP).forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }
}
