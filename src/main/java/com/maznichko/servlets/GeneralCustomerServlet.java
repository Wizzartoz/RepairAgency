package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.GetBank;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

public class GeneralCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        Command command = new GetBank();
        String result = command.execute(request, response);
        if (request.getParameter("money") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ReplenishmentCustomerServlet");
            dispatcher.forward(request, response);
        } else if (request.getParameter("user_message") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/RequestCustomerServlet");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("result",request.getParameter("result"));
            Integer bank = (Integer) httpSession.getAttribute("bank");
            request.setAttribute("bank", bank);
            RequestDispatcher dispatcher = request.getRequestDispatcher(result);
            dispatcher.forward(request, response);
        }
    }
}
