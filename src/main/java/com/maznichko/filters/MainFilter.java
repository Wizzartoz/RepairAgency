package com.maznichko.filters;

import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {
        "GeneralCustomerServlet",
        "ManagerServlet",
        "MasterServlet",
        "ManagerReportServlet"
})
public class MainFilter implements Filter {
    private static final Logger log = Logger.getLogger(MainFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("permissions filter is working...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String destination = req.getServletPath();
        if (req.getParameter("logout") != null) {
            session.removeAttribute("role");
            session.removeAttribute("login");
        }
        if (session.getAttribute("role") == null) {
            resp.sendRedirect("/index.jsp");
        } else if (destination.equals(Path.CUSTOMER_SERVLET)) {
            if (!session.getAttribute("role").equals("CUSTOMER")) {
                resp.sendRedirect(Path.ERROR);
            } else {
                chain.doFilter(request, response);
            }
        } else if (destination.equals(Path.MANAGER_SERVLET) || destination.equals(Path.REPORT_SERVLET)) {
            if (!session.getAttribute("role").equals("MANAGER")) {
                resp.sendRedirect(Path.ERROR);
            } else {
                chain.doFilter(request, response);
            }
        } else if (destination.equals(Path.MASTER_SERVLET)) {
            if (!session.getAttribute("role").equals("MASTER")) {
                resp.sendRedirect(Path.ERROR);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
