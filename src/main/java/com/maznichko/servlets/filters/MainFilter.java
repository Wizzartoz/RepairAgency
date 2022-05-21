package com.maznichko.servlets.filters;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"GeneralCustomerServlet", "ManagerServlet", "MasterServlet"})
public class MainFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (req.getParameter("logout") != null) {
            session.removeAttribute("role");
            session.removeAttribute("login");
        }
        if (session.getAttribute("role") == null) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
