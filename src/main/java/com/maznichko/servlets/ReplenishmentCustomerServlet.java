package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.GetBank;
import com.maznichko.services.Replenishment;
import com.maznichko.services.SendRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerSecondServlet", value = "/ReplenishmentCustomerServlet")
public class ReplenishmentCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result",request.getParameter("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/GeneralCustomerServlet");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = (String) request.getAttribute("result");
        response.sendRedirect(String.format("ReplenishmentCustomerServlet?result=%s", result));
    }
}
