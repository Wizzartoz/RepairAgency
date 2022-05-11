package com.maznichko.servlets;

;

import com.maznichko.services.Path;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = "/RequestCustomerServlet")
public class RequestCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result",request.getParameter("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.CUSTOMER_SERVLET);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = (String) request.getAttribute("result");
        response.sendRedirect(String.format("RequestCustomerServlet?result=%s", result));
    }
}
