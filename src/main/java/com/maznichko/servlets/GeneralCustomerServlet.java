package com.maznichko.servlets;

import com.maznichko.services.Path;
import com.maznichko.services.commands.Command;
import com.maznichko.services.commands.CommandContainer;
import com.maznichko.services.GenerateTable;
import com.maznichko.services.GetBank;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class GeneralCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession httpSession = request.getSession();
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.CUSTOMER_JSP;
        if (command != null) {
            result = command.execute(request, response);
        }
        GenerateTable.execute(request, response);
        GetBank.execute(request, response);
        Integer bank = (Integer) httpSession.getAttribute("bank");
        request.setAttribute("bank", bank);
        RequestDispatcher dispatcher = request.getRequestDispatcher(result);
        dispatcher.forward(request, response);
    }
}
