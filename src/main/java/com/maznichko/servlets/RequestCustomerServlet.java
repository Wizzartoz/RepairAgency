package com.maznichko.servlets;

import com.maznichko.services.Command;;
import com.maznichko.services.Replenishment;
import com.maznichko.services.SendRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = "/RequestCustomerServlet")
public class RequestCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String res = request.getParameter("res");
        RequestDispatcher dispatcher = request.getRequestDispatcher(res);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = new SendRequest();
        String res = command.execute(request, response);
        String result = (String) request.getAttribute("result");
        response.sendRedirect(String.format("RequestCustomerServlet?res=%s&result=%s", res, result));
    }
}
