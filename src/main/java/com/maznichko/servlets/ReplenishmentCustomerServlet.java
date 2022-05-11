package com.maznichko.servlets;

import com.maznichko.services.Path;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerSecondServlet", value = "/ReplenishmentCustomerServlet")
public class ReplenishmentCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result",request.getParameter("result"));
        String path = request.getParameter("path");
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = (String) request.getAttribute("result");
        String path = (String) request.getAttribute("path");
        response.sendRedirect(String.format(Path.REPLENISHMENT_SERVLET+"?result=%s&path=%s", result,path));
    }
}
